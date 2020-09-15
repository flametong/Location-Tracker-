package com.example.locationtrackerkotlin.mvp.base

interface BasePresenter<T: BaseView> {
    fun attachView(view: T)
    fun detachView()
}