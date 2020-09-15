package com.example.locationtrackerkotlin.mvp.presenter

import android.app.Activity
import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.locationtrackerkotlin.mvp.view.LoginView
import com.google.firebase.auth.FirebaseAuth

class LoginPresenterImpl(private val mAuth: FirebaseAuth) :
    LoginPresenter,
    LifecycleObserver {

    companion object {
        val TAG = LoginPresenterImpl::class.simpleName
        const val PERMISSION_REQUEST_LOCATION = 1
        val CURRENT_VERSION = Build.VERSION.SDK_INT
        const val VERSION_Q = Build.VERSION_CODES.Q
        const val ACCESS_FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION

        @RequiresApi(Build.VERSION_CODES.Q)
        const val ACCESS_BACKGROUND_LOCATION =
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
    }

    private var mView: LoginView? = null

    override fun signIn(email: String, password: String) {
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            mView?.showValidateError()
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(mView as Activity) {
                    if (it.isSuccessful) {
                        mView?.showSignInSuccess()
                    } else {
                        mView?.showSignInError()
                    }
                }
        }
    }

    // Request location permissions at application start
    @RequiresApi(Build.VERSION_CODES.Q)
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun getLocationPermission() {
        if (CURRENT_VERSION < VERSION_Q) {
            if (mView?.getContext()?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        ACCESS_FINE_LOCATION
                    )
                } == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted")
            } else {
                requestLocationPermission()
            }
        } else {
            if (mView?.getContext()?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        ACCESS_BACKGROUND_LOCATION
                    )
                } == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "Permission granted")
            } else {
                requestLocationPermission()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun requestLocationPermission() {
        if (CURRENT_VERSION < VERSION_Q) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    mView as Activity, ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    mView as Activity,
                    arrayOf(
                        ACCESS_FINE_LOCATION
                    ),
                    PERMISSION_REQUEST_LOCATION
                )
            }
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    mView as Activity, ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    mView as Activity,
                    arrayOf(
                        ACCESS_FINE_LOCATION,
                        ACCESS_BACKGROUND_LOCATION
                    ),
                    PERMISSION_REQUEST_LOCATION
                )
            }
        }
    }

    override fun attachView(view: LoginView) {
        mView = view
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun detachView() {
        mView = null
    }
}