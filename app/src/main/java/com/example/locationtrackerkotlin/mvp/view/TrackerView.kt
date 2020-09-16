package com.example.locationtrackerkotlin.mvp.view

import com.example.locationtrackerkotlin.mvp.base.BaseView

interface TrackerView : BaseView {
    fun signOutUser()
    fun showRequestLocationDialog()
    fun showStartLocationService()
    fun showServiceAlreadyRunning()
    fun showStopLocationService()
}