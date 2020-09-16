package com.example.locationtrackerkotlin.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.example.locationtrackerkotlin.App
import java.lang.Exception

class CheckNetwork {

    companion object {
        private val TAG = CheckNetwork::class.simpleName
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun registerNetworkCallback() {
        try {
            val connectivityManager =
                App.context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

            connectivityManager.registerDefaultNetworkCallback(
                object : NetworkCallback() {
                    override fun onAvailable(network: Network) {
                        Variables.isNetworkConnected = true
                        Log.d(TAG, "isNetworkConnected = true")
                    }

                    override fun onLost(network: Network) {
                        Variables.isNetworkConnected = false
                        Log.d(TAG, "isNetworkConnected = false")
                    }
                }
            )
        } catch (e: Exception) {
            Variables.isNetworkConnected = false
        }
    }
}