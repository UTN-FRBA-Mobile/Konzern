package utn.frba.mobile.konzern.posts.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_new_item_post.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.adapter.ImageSliderAdapter
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.viewModel.PostsViewModel
import utn.frba.mobile.konzern.utils.FilePickerManager

class NewItemPostFragment : Fragment(), FilePickerManager.ResultListener {
    private lateinit var viewModel: PostsViewModel
    private lateinit var filePickerManager: FilePickerManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(PostsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")
        filePickerManager = FilePickerManager(this, FilePickerManager.FILE_PICKED_REQUEST_CODE)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_new_item_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_save.setOnClickListener {
            this.onBtnSave()
        }

        btn_cancel.setOnClickListener {
            findNavController().navigateUp()
        }

        btn_add_image.setOnClickListener{
            filePickerManager.showFilePicker()
        }

        viewModel.selectedItem.observe(viewLifecycleOwner, Observer<Post> {
            setView(it)
        })

        viewModel.images.observe(viewLifecycleOwner, Observer<List<Uri>> {
            setImageSlider(it)
        })
    }

    private fun setImageSlider(images: List<Uri>) {
        if(images.count() > 0) {
            imageSlider.visibility = View.VISIBLE
            imageSlider.setSliderAdapter(ImageSliderAdapter(images), false)
        }
        else
            imageSlider.visibility = View.GONE
    }

    private fun setView(item: Post){
    }

    private fun onBtnSave(){
        validateFields()
        if(layout_summary.error == null && layout_description.error == null) {
            viewModel.addItem(et_summary.text.toString(), et_description.text.toString())
            findNavController().navigateUp()
        }
    }

    private fun validateFields() {
        layout_summary.error = null
        layout_description.error = null

        if(et_summary.text.isNullOrEmpty())
            layout_summary.error = layout_summary.helperText

        if(et_description.text.isNullOrEmpty())
            layout_description.error = layout_description.helperText
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
}
