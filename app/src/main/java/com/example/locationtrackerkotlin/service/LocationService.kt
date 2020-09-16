package com.example.locationtrackerkotlin.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.concurrent.futures.CallbackToFutureAdapter
import androidx.core.content.ContextCompat
import androidx.work.ForegroundInfo
import androidx.work.ListenableWorker
import androidx.work.WorkerParameters
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.R
import com.example.locationtrackerkotlin.room.DatabaseActions
import com.example.locationtrackerkotlin.util.CheckNetwork
import com.example.locationtrackerkotlin.util.Constants
import com.example.locationtrackerkotlin.util.Variables
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.common.util.concurrent.ListenableFuture
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.sql.Timestamp
import javax.inject.Inject

class LocationService(
    appContext: Context,
    workerParams: WorkerParameters
) : ListenableWorker(appContext, workerParams) {

    companion object {
        private val TAG = LocationService::class.simpleName
        private val NOTIFICATION_CHANNEL_ID =
            App.context.getString(R.string.notification_channel_id)
        private val NOTIFICATION_CHANNEL_NAME =
            App.context.getString(R.string.notification_channel_name)
        private val LOCATION_CHANNEL_DESCRIPTION =
            App.context.getString(R.string.location_channel_description)
        private const val INTERVAL: Long = 1000 * 1 * 15 // 15 min
        private const val FASTEST_INTERVAL: Long = 1000 * 1 * 10 // 10 min
    }

    private lateinit var mLocationCallback: LocationCallback

    @Inject
    lateinit var mLocationRequest: LocationRequest

    @Inject
    lateinit var mLocationProviderClient: FusedLocationProviderClient

    @Inject
    lateinit var mAuth: FirebaseAuth

    @Inject
    lateinit var mFirebaseFirestore: FirebaseFirestore

    init {
        App.appComponent.inject(this)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun startWork(): ListenableFuture<Result> {

        return CallbackToFutureAdapter.getFuture {

            mLocationCallback = object : LocationCallback() {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun onLocationResult(locationResult: LocationResult?) {
                    locationResult ?: return

                    CheckNetwork().registerNetworkCallback()

                    val location = locationResult.lastLocation

                    if (Variables.isServiceActive) {
                        Log.d(TAG, "Service is active")

                        // Create current user location instance
                        val currentLocation = CurrentUserLocation(
                            mAuth.currentUser?.email,
                            location.latitude,
                            location.longitude,
                            System.currentTimeMillis(),
                            Timestamp(System.currentTimeMillis())
                        )

                        if (Variables.isNetworkConnected) {
                            Log.d(TAG, "Internet connected")

                            // Send stored data from db to server
                            GlobalScope.launch {
                                DatabaseActions().sendDataToServer()
                            }

                            mFirebaseFirestore.collection(Constants.LOCATIONS)
                                .add(currentLocation)
                                .addOnSuccessListener {
                                    Log.d(TAG, "DocumentSnapshot added with ID: ${it.id}")
                                }
                                .addOnFailureListener {
                                    Log.d(TAG, "Error adding document $it")
                                }
                        } else {
                            Log.d(TAG, "Internet disconnected")

                            // Saving data to db when no internet
                            GlobalScope.launch {
                                DatabaseActions().insertToDatabase(
                                    location.latitude,
                                    location.longitude
                                )
                            }
                        }
                    } else {
                        Log.d(TAG, "Service is non-active")
                        mLocationProviderClient.removeLocationUpdates(mLocationCallback)
                    }
                }
            }

            if (Variables.isServiceActive) {
                setForegroundAsync(createForegroundInfo())
                requestLocationUpdates()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createForegroundInfo(): ForegroundInfo {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Set the settings of notification channel
        NotificationChannel(
            NOTIFICATION_CHANNEL_ID,
            NOTIFICATION_CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            description = LOCATION_CHANNEL_DESCRIPTION
            enableLights(true)
            lightColor = Color.RED
            enableVibration(false)
        }.let {
            notificationManager.createNotificationChannel(it)
        }

        val pendingIntent = PendingIntent
            .getActivity(applicationContext, 0, Intent(), 0)

        val builder = Notification.Builder(
            applicationContext,
            NOTIFICATION_CHANNEL_ID
        )

        // Creating notification style
        val notification = builder
            .setContentTitle(App.context.getString(R.string.location_service))
            .setContentText(App.context.getString(R.string.location_tracker_is_working))
            .setContentIntent(pendingIntent)
            .setSmallIcon(android.R.mipmap.sym_def_app_icon)
            .setTicker(App.context.getString(R.string.ticker_text))
            .build()
            .also {
                // No cancelable
                it.flags = Notification.FLAG_NO_CLEAR
            }

        return ForegroundInfo(1, notification)
    }

    private fun requestLocationUpdates() {
        // Set the setting for location request
        mLocationRequest.apply {
            interval = INTERVAL
            fastestInterval = FASTEST_INTERVAL
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }

        // Check location permissions
        if (ContextCompat.checkSelfPermission(
                applicationContext,
                Constants.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
            || ContextCompat.checkSelfPermission(
                applicationContext,
                Constants.ACCESS_BACKGROUND_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            // Request location updates and when an update is
            // received, store the location in Firebase
            mLocationProviderClient.requestLocationUpdates(
                mLocationRequest,
                mLocationCallback,
                Looper.getMainLooper()
            )
        }
    }
}