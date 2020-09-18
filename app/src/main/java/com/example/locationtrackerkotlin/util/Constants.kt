package com.example.locationtrackerkotlin.util

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresApi

object Constants {
    const val LOCATIONS = "locations"
    const val REQUEST_TAG = "LOCATION_REQUEST"
    const val DIALOG_TAG = "REQUEST_LOCATION_DIALOG"
    const val DATABASE_NAME = "UserLocation.db"
    const val UNIQUE_LOCATION_WORK = "UniqueLocationWork"
    const val TABLE_NAME = "UserLocation"
    const val USER_ID_DB = "userId"
    const val USER_EMAIL = "userEmail"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val TIME_IN_MILLIS = "timeInMillis"
    const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    @RequiresApi(Build.VERSION_CODES.Q)
    const val ACCESS_BACKGROUND_LOCATION = Manifest.permission.ACCESS_BACKGROUND_LOCATION
    val CURRENT_VERSION = Build.VERSION.SDK_INT
    const val VERSION_Q = Build.VERSION_CODES.Q
    const val USER_ID = 0L
}