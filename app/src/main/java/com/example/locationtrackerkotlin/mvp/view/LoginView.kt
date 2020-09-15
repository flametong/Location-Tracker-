package com.example.locationtrackerkotlin.mvp.view

import com.example.locationtrackerkotlin.mvp.base.BaseView

interface LoginView: BaseView {
    fun showSignInSuccess()
    fun showSignInError()
    fun showValidateError()
}