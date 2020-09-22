package com.example.locationtrackerkotlin.mvp.presenter

import com.example.locationtrackerkotlin.mvp.view.SignUpView
import com.google.firebase.auth.FirebaseAuth
import moxy.InjectViewState
import moxy.MvpPresenter

@InjectViewState
class SignUpPresenter(private val mAuth: FirebaseAuth) : MvpPresenter<SignUpView>() {

    fun signUpUser(
        email: String,
        password: String,
        confirmedPassword: String
    ) {
        if (email.isEmpty()
            || password.isEmpty()
            || confirmedPassword.isEmpty()
        ) {
            viewState.showValidateError()
        } else {
            if (password != confirmedPassword) {
                viewState.showConfirmedPasswordError()
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            viewState.showSignUpSuccess()
                        } else {
                            viewState.showSignUpError()
                        }
                    }
            }
        }
    }
}