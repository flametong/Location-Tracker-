package com.example.locationtrackerkotlin.mvp.presenter

import com.example.locationtrackerkotlin.mvp.model.LocationServiceModel
import com.example.locationtrackerkotlin.mvp.view.TrackerView
import com.example.locationtrackerkotlin.util.Constants
import com.example.locationtrackerkotlin.util.Util
import com.example.locationtrackerkotlin.util.Variables
import com.google.firebase.auth.FirebaseAuth
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class TrackerPresenterImpl(private val mAuth: FirebaseAuth) : MvpPresenter<TrackerView>() {

    private var mModel = LocationServiceModel()

    fun requestLocationDialog() {
        viewState.showRequestLocationDialog()
    }

    fun signOutUser() {
        Variables.isServiceActive = false
        mModel.stopLocationService()
        mAuth.signOut()
        viewState.signOutUser()
    }

    fun launchService() {
        Variables.isServiceActive = true

        if (!Util.isWorkScheduled(Constants.REQUEST_TAG)) {
            viewState.showStartLocationService()
            mModel.startLocationService()
        } else {
            viewState.showServiceAlreadyRunning()
        }
    }

    fun stopService() {
        Variables.isServiceActive = false
        mModel.stopLocationService()
        viewState.showStopLocationService()
    }
}