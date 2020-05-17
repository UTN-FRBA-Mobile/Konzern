package utn.frba.mobile.konzern.posts.ui

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_new_item_post.*
import utn.frba.mobile.konzern.R
import utn.frba.mobile.konzern.posts.PostsActivity.Companion.PERMISSION_REQUEST_CODE
import utn.frba.mobile.konzern.posts.adapter.ImageSliderAdapter
import utn.frba.mobile.konzern.posts.model.Post
import utn.frba.mobile.konzern.posts.viewModel.PostsViewModel


class NewItemPostFragment : Fragment() {
    private val STORAGE_REQUEST_CODE: Int = 201
    private lateinit var viewModel: PostsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_new_item_post, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = activity?.run {
            ViewModelProvider(this).get(PostsViewModel::class.java)
        } ?: throw Exception("Invalid Activity")

        btn_save.setOnClickListener {
            this.onBtnSave()
        }

        btn_cancel.setOnClickListener {
            findNavController().navigateUp()
        }

        btn_add_image.setOnClickListener{
            onBtnAddImage()
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

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun onBtnAddImage(){
        checkPermission()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val permission = Manifest.permission.READ_EXTERNAL_STORAGE
            val result = ContextCompat.checkSelfPermission(requireActivity(), permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                requestPermission(permission)
                return
            }
        }

        showChooser()
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private fun showChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        startActivityForResult(intent, STORAGE_REQUEST_CODE)
    }

    private fun requestPermission(permission: String) {
        ActivityCompat.requestPermissions(requireActivity(), arrayOf(permission), PERMISSION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?) {
        super.onActivityResult(requestCode, resultCode, resultData)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == STORAGE_REQUEST_CODE) {
                if (resultData != null) {
                    if (resultData.clipData != null) {
                        val count = resultData.clipData!!.itemCount
                        val images = ArrayList<Uri>()
                        var currentItem = 0
                        while (currentItem < count) {
                            val imageUri: Uri = resultData.clipData!!.getItemAt(currentItem).uri
                            currentItem += 1
                            images.add(imageUri)
                        }
                        addImage(images)
                    } else if (resultData.data != null) {
                        addImage(resultData.data)
                    }
                }
            }
        }
    }

    private fun addImage(imageUri: Uri?){
        if(imageUri != null)
            viewModel.addImage(arrayListOf(imageUri))
    }

    private fun addImage(imageUri: List<Uri>){
        viewModel.addImage(imageUri)
    }
}
