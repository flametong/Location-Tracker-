package com.example.locationtrackerkotlin.mvp.view

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleTagStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleTagStrategy::class)
interface SignUpView: MvpView {
    fun showSignUpSuccess()
    fun showSignUpError()
    fun showValidateError()
    fun showConfirmedPasswordError()
}