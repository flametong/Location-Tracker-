package com.example.locationtrackerkotlin.mvp.presenter

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.locationtrackerkotlin.mvp.view.LoginView
import com.example.locationtrackerkotlin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class LoginPresenter(private val mAuth: FirebaseAuth) : MvpPresenter<LoginView>() {

    companion object {
        val TAG = LoginPresenter::class.simpleName
        const val PERMISSION_REQUEST_LOCATION = 1
    }

    fun signIn(email: String, password: String) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            viewState.showValidateError()
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewState.showSignInSuccess()
                    } else {
                        viewState.showSignInError()
                    }
                }
        }
    }

    // Request location permissions at application start
    @RequiresApi(Build.VERSION_CODES.Q)
    fun getLocationPermission(context: Context) {
        if (Constants.CURRENT_VERSION < Constants.VERSION_Q) {
            if (context.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Constants.ACCESS_FINE_LOCATION
                    )
                } == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted")
            } else {
                viewState.requestLocationPermission()
            }
        } else {
            if (context.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        Constants.ACCESS_BACKGROUND_LOCATION
                    )
                } == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted")
            } else {
                viewState.requestLocationPermission()
            }
        }
    }
}