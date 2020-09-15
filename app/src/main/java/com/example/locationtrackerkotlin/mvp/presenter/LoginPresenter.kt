package com.example.locationtrackerkotlin.mvp.presenter

import com.example.locationtrackerkotlin.mvp.base.BasePresenter
import com.example.locationtrackerkotlin.mvp.view.LoginView

interface LoginPresenter: BasePresenter<LoginView> {
    fun signIn(email: String, password: String)
}