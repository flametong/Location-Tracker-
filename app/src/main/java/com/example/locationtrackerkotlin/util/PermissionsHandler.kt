package com.example.locationtrackerkotlin.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionsHandler {

    fun checkHasPermission(context: Context?, permission: String?): Boolean =
        ContextCompat.checkSelfPermission(context!!, permission!!) ==
                PackageManager.PERMISSION_GRANTED

    fun requestPermission(
        activity: AppCompatActivity?,
        permissions: Array<String?>?,
        requestCode: Int
    ) = ActivityCompat.requestPermissions(activity!!, permissions!!, requestCode)
}