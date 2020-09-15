package com.example.locationtrackerkotlin.mvp.presenter

import com.example.locationtrackerkotlin.mvp.base.BasePresenter
import com.example.locationtrackerkotlin.mvp.view.SignUpView

interface SignUpPresenter: BasePresenter<SignUpView> {
    fun signUpUser(email: String,
                   password: String,
                   confirmedPassword: String)
}