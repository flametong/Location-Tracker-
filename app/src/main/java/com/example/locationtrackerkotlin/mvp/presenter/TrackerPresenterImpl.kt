package com.example.locationtrackerkotlin.mvp.presenter

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.locationtrackerkotlin.mvp.model.LocationServiceModelImpl
import com.example.locationtrackerkotlin.mvp.view.TrackerView
import com.example.locationtrackerkotlin.util.Constants
import com.example.locationtrackerkotlin.util.Util
import com.example.locationtrackerkotlin.util.Variables
import com.google.firebase.auth.FirebaseAuth

class TrackerPresenterImpl(private val mAuth: FirebaseAuth) :
    TrackerPresenter, LifecycleObserver {

    private var mView: TrackerView? = null
    private var mModel = LocationServiceModelImpl()

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun requestLocationDialog() {
        mView?.showRequestLocationDialog()
    }

    override fun signOutUser() {
        Variables.isServiceActive = false
        mModel.stopLocationService()
        mAuth.signOut()
        mView?.signOutUser()
    }

    override fun launchService() {
        Variables.isServiceActive = true
        mModel.startLocationService()

        if (!Util.isWorkScheduled(Constants.REQUEST_TAG)) {
            mView?.showStartLocationService()
        } else {
            mView?.showServiceAlreadyRunning()
        }
    }

    override fun stopService() {
        Variables.isServiceActive = false
        mModel.stopLocationService()
        mView?.showStopLocationService()
    }

    override fun attachView(view: TrackerView) {
        mView = view
    }

    override fun detachView() {
        mView = null
    }
}