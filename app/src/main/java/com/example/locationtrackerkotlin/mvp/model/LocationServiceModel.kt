package com.example.locationtrackerkotlin.mvp.model

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.util.Constants
import com.example.locationtrackerkotlin.util.Util
import com.example.locationtrackerkotlin.service.LocationService
import java.util.concurrent.TimeUnit

class LocationServiceModel {

    // Start unique periodic worker
    fun startLocationService() {
        if (!Util.isWorkScheduled(Constants.REQUEST_TAG)) {
            val locationWorkRequest = PeriodicWorkRequest.Builder(
                LocationService::class.java,
                15, TimeUnit.MINUTES
            )
                .addTag(Constants.REQUEST_TAG)
                .build()

            WorkManager.getInstance(App.context).enqueueUniquePeriodicWork(
                Constants.UNIQUE_LOCATION_WORK,
                ExistingPeriodicWorkPolicy.KEEP,
                locationWorkRequest
            )
        }
    }

    // Stop unique periodic worker
    fun stopLocationService() {
        WorkManager.getInstance(App.context)
            .cancelAllWorkByTag(Constants.REQUEST_TAG)
    }
}