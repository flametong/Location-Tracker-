package com.example.locationtrackerkotlin.mvp.view

import com.example.locationtrackerkotlin.mvp.base.BaseView

interface SignUpView: BaseView {
    fun showSignUpSuccess()
    fun showSignUpError()
    fun showValidateError()
    fun showConfirmedPasswordError()
}