package com.example.locationtrackerkotlin.util

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.R

object Constants {
    var LOCATIONS = App.context.getString(R.string.locations)
    var REQUEST_TAG = App.context.getString(R.string.request_tag)
    var DIALOG_TAG = App.context.getString(R.string.dialog_tag)
    var DATABASE_NAME = App.context.getString(R.string.database_name)
    var UNIQUE_LOCATION_WORK = App.context.getString(R.string.unique_location_work)
    var ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    @RequiresApi(Build.VERSION_CODES.Q)
    var ACCESS_BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION
}