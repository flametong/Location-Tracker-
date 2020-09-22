package com.example.locationtrackerkotlin.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface LoginView : MvpView {
    fun showSignInSuccess()
    fun showSignInError()
    fun showValidateError()
    fun requestLocationPermissions()
}