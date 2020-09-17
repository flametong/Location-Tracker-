package com.example.locationtrackerkotlin.util

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.R

object Constants {
    val LOCATIONS = App.context.getString(R.string.locations)
    val REQUEST_TAG = App.context.getString(R.string.request_tag)
    val DIALOG_TAG = App.context.getString(R.string.dialog_tag)
    val DATABASE_NAME = App.context.getString(R.string.database_name)
    val UNIQUE_LOCATION_WORK = App.context.getString(R.string.unique_location_work)
    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    @RequiresApi(Build.VERSION_CODES.Q)
    const val ACCESS_BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION
    val CURRENT_VERSION = Build.VERSION.SDK_INT
    const val VERSION_Q = Build.VERSION_CODES.Q
    const val USER_ID = 0L
}