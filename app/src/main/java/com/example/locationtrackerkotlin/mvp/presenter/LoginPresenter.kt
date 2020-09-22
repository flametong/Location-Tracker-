package com.example.locationtrackerkotlin.mvp.presenter

import com.example.locationtrackerkotlin.mvp.view.LoginView
import com.google.firebase.auth.FirebaseAuth
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class LoginPresenter(private val mAuth: FirebaseAuth) : MvpPresenter<LoginView>() {

    fun signIn(email: String, password: String) {
        if (email.isEmpty() || password.isEmpty()) {
            viewState.showValidateError()
        } else {
            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        viewState.showSignInSuccess()
                    } else {
                        viewState.showSignInError()
                    }
                }
        }
    }

    fun provideLocationPermissions() {
        viewState.requestLocationPermissions()
    }
}