package com.example.locationtrackerkotlin.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface TrackerView : MvpView {
    fun signOutUser()
    fun showRequestLocationDialog()
    fun showStartLocationService()
    fun showServiceAlreadyRunning()
    fun showStopLocationService()
}