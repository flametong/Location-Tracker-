package com.example.locationtrackerkotlin.mvp.presenter

import com.example.locationtrackerkotlin.mvp.base.BasePresenter
import com.example.locationtrackerkotlin.mvp.view.TrackerView

interface TrackerPresenter : BasePresenter<TrackerView> {
    fun signOutUser()
    fun launchService()
    fun stopService()
}