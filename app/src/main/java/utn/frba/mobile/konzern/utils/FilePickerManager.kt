package utn.frba.mobile.konzern.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import utn.frba.mobile.konzern.BuildConfig
import utn.frba.mobile.konzern.utils.PermissionsManager.Companion.PERMISSION_REQUEST_CODE
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class FilePickerManager(private val resultListener: ResultListener): PermissionsManager.PermissionResultListener {
    interface ResultListener{
        val fragment: Fragment
        fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?)
        fun onRequestFilePickedResult(files: List<Uri>)
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    }

    companion object {
        const val FILE_PICKED_REQUEST_CODE = 1
        const val FILE_CAMERA_CAPTURED_REQUEST_CODE = 2
    }

    private val permissionsManager = PermissionsManager(this, PERMISSION_REQUEST_CODE)

    fun showFilePicker(){
        if(permissionsManager.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
            onPermissionGranted()
        }
    }

    private fun showChooser() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder: AlertDialog.Builder = AlertDialog.Builder(fragment.requireContext())
        builder.setTitle("Choose your profile picture")
        builder.setItems(options) { dialog, item ->
            when {
                options[item] == "Take Photo" -> {
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    intent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        FileProvider.getUriForFile(
                            resultListener.fragment.requireContext(),
                            BuildConfig.APPLICATION_ID.toString() + ".provider",
                            createImageFile()!!
                        )
                    )
                    resultListener.fragment.startActivityForResult(intent, FILE_CAMERA_CAPTURED_REQUEST_CODE)
                }
                options[item] == "Choose from Gallery" -> {
                    val intent = Intent(Intent.ACTION_GET_CONTENT)
                    intent.type = "image/*"
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    //val pickPhoto = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                    resultListener.fragment.startActivityForResult(intent, FILE_PICKED_REQUEST_CODE)
                }
                options[item] == "Cancel" -> {
                    dialog.dismiss()
                }
            }
        }
        builder.show()
    }

    private lateinit var currentPhotoPath: String

    @Throws(IOException::class)
    private fun createImageFile(): File? { // Create an image file name
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = File(
            Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_DCIM
            ), "Camera"
        )
        val image = File.createTempFile(imageFileName, ".jpg", storageDir)
        currentPhotoPath = "file://" + image.absolutePath
        return image
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?){
        if (resultCode == Activity.RESULT_OK) {
            val files = arrayListOf<Uri>()

            if(requestCode == FILE_PICKED_REQUEST_CODE && resultData != null) {
                files.addAll(retrieveFileFromGallery(resultData))
            }
            else if(requestCode == FILE_CAMERA_CAPTURED_REQUEST_CODE){
                files.add(Uri.parse(currentPhotoPath))
                currentPhotoPath = ""
            }

            if (files.count() > 0) {
                resultListener.onRequestFilePickedResult(files)
            }
        }
    }

    private fun retrieveFileFromGallery(resultData: Intent): List<Uri>{
        val files = arrayListOf<Uri>()
        if (resultData.clipData != null) {
            val count = resultData.clipData!!.itemCount
            var currentItem = 0
            while (currentItem < count) {
                val imageUri: Uri = resultData.clipData!!.getItemAt(currentItem).uri
                currentItem += 1
                files.add(imageUri)
            }
        } else if (resultData.data != null) {
            files.add(resultData.data!!)
        }

        return files
    }

    override val fragment: Fragment
        get() = this.resultListener.fragment

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onPermissionGranted() {
        showChooser()
    }
}