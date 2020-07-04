package utn.frba.mobile.konzern.posts.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_post_item.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.adapter.ImageSliderAdapter
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.utils.FilePickerManager

class PostItemFragment : PostBaseFragment(), FilePickerManager.ResultListener {
    private lateinit var filePickerManager: FilePickerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        filePickerManager = FilePickerManager(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        cleanView()

        vButtonSavePostItem.setOnClickListener {
            this.onBtnSave()
        }

        vButtonCancelPostItem.setOnClickListener {
            findNavController().navigateUp()
        }

        vButtonAddImagePostItem.setOnClickListener{
            filePickerManager.showFilePicker()
        }

        viewModel.editEvent.observe(viewLifecycleOwner, Observer {
            setView(viewModel.selectedItem!!)
        })

        viewModel.images.observe(viewLifecycleOwner, Observer<List<Uri>> {
            setImageSlider(it)
        })
    }

    private fun cleanView(){
        setView(Post())
        setImageSlider(arrayListOf())
    }

    private fun setImageSlider(images: List<Uri>) {
        if(images.count() > 0) {
            vSliderImagePostItem.visibility = View.VISIBLE
            vSliderImagePostItem.setSliderAdapter(ImageSliderAdapter(images), false)
        }
        else
            vSliderImagePostItem.visibility = View.GONE
    }

    private fun setView(item: Post){
        vInputSummaryPostItem.setText(item.summary)
        vInputDescriptionPostItem.setText(item.description)
    }

    private fun onBtnSave(){
        validateFields()
        if(vLayoutSummaryPostItem.error == null && vLayoutDescriptionPostItem.error == null) {
            viewModel.saveItem(vInputSummaryPostItem.text.toString(), vInputDescriptionPostItem.text.toString())
            findNavController().navigateUp()
        }
    }

    private fun validateFields() {
        vLayoutSummaryPostItem.error = null
        vLayoutDescriptionPostItem.error = null

        if(vInputSummaryPostItem.text.isNullOrEmpty())
            vLayoutSummaryPostItem.error = vLayoutSummaryPostItem.helperText

        if(vInputDescriptionPostItem.text.isNullOrEmpty())
            vLayoutDescriptionPostItem.error = vLayoutDescriptionPostItem.helperText
    }

    //region FilePickerManagerListener
    override val fragment: Fragment
        get() = this

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        filePickerManager.onActivityResult(requestCode, resultCode, resultData)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        filePickerManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onRequestFilePickedResult(files: List<Uri>) {
        viewModel.addImage(files)
    }
    //endregion

    companion object {
        @JvmStatic
        fun newInstance(isClaim: Boolean) = PostItemFragment().apply {
            arguments = Bundle().apply {
                putBoolean("isClaim", isClaim)
            }
        }
    }
}
