package utn.frba.mobile.konzern.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import utn.frba.mobile.konzern.posts.PostsActivity

class PermissionsManager(
    private val resultListener: PermissionResultListener,
    private val requestCode: Int
) {
    interface PermissionResultListener{
        val fragment: Fragment
        fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray)
        fun onPermissionGranted()
    }

    companion object {
        const val PERMISSION_REQUEST_CODE = 100
    }

    private fun getActivity(): Activity{
        return resultListener.fragment.requireActivity()
    }

    fun checkPermission(permission: String): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val result = ContextCompat.checkSelfPermission(getActivity(), permission)
            if (result != PackageManager.PERMISSION_GRANTED) {
                requestPermission(permission)
                return false
            }
        }

        return true
    }

    private fun requestPermission(permission: String) {
        requestPermissions(arrayOf(permission))
    }

    private fun requestPermissions(permissions: Array<String>) {
        resultListener.fragment.requestPermissions(permissions, requestCode)
    }

    fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode == this.requestCode) {
            if (grantResults.isNotEmpty()) {
                val permissionAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (!permissionAccepted) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(getActivity(), permissions[0])) {
                            showMessageOKCancel("Para poder continuar, necesitás otorgar permisos.",
                                DialogInterface.OnClickListener { _, _ ->
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                        requestPermissions(permissions)
                                    }
                                },
                                DialogInterface.OnClickListener { _, _ ->
                                    //findNavController().navigateUp()
                                })
                            return
                        }
                    }
                } else{
                    resultListener.onPermissionGranted()
                }
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener, cancelListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(getActivity())
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancelar", cancelListener)
            .create()
            .show()
    }
}