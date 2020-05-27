package utn.frba.mobile.konzern.utils

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.fragment.app.Fragment
import utn.frba.mobile.konzern.utils.PermissionsManager.Companion.PERMISSION_REQUEST_CODE

class FilePickerManager(
    private val resultListener: ResultListener,
    private val requestCode: Int
): PermissionsManager.PermissionResultListener {
    interface ResultListener{
        val fragment: Fragment
        fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?)
        fun onRequestFilePickedResult(files: List<Uri>)
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
    }

    companion object {
        const val FILE_PICKED_REQUEST_CODE = 1
    }

    private val permissionsManager = PermissionsManager(this, PERMISSION_REQUEST_CODE)

    fun showFilePicker(){
        if(permissionsManager.checkPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
            onPermissionGranted()
        }
    }

    private fun showChooser() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        resultListener.fragment.startActivityForResult(intent, requestCode)
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, resultData: Intent?){
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == requestCode) {
                if (resultData != null) {
                    val files = ArrayList<Uri>()
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

                    resultListener.onRequestFilePickedResult(files)
                }
            }
        }
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