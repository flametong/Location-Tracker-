package com.example.locationtrackerkotlin.room

import android.util.Log
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.service.CurrentUserLocation
import com.example.locationtrackerkotlin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.sql.Timestamp
import javax.inject.Inject

class DatabaseActions {

    companion object {
        private var TAG = DatabaseActions::class.simpleName
    }

    @Inject
    lateinit var mAuth: FirebaseAuth

    @Inject
    lateinit var mFirebaseFirestore: FirebaseFirestore

    @Inject
    lateinit var mDatabase: AppDatabase

    init {
        App.appComponent.inject(this)
    }

    // Send data from database to Cloud Firestore
    // and then clear database
    suspend fun sendDataToServer() {
        val dbLocationList = mDatabase.userLocationDao().getAll()

        if (dbLocationList.isNotEmpty()) {
            dbLocationList.forEach {
                // Create current user location instance
                val currentLocation = CurrentUserLocation(
                    it.userEmail,
                    it.latitude,
                    it.longitude,
                    it.timeInMillis,
                    it.timeInMillis?.let { millis -> Timestamp(millis) }
                )

                mFirebaseFirestore.collection(Constants.LOCATIONS)
                    .add(currentLocation)
                    .addOnSuccessListener { dc ->
                        Log.d(TAG, "DocumentSnapshot added with ID: ${dc.id}")
                    }
                    .addOnFailureListener { e ->
                        Log.d(TAG, "Error adding document $e")
                    }
            }

            mDatabase.clearAllTables()
            Log.d(TAG, "Database cleared")
        }
    }

    // Insert current UserLocation instance to db
    suspend fun insertToDatabase(latitude: Double, longitude: Double) {
        val userLocation = UserLocation(
            0,
            mAuth.currentUser?.email,
            latitude,
            longitude,
            System.currentTimeMillis()
        )

        mDatabase.userLocationDao().insert(userLocation)

        Log.d(TAG, "Insert completed")
    }
}