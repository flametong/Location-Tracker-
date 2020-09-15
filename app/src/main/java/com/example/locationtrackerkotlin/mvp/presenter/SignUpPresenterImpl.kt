package com.example.locationtrackerkotlin.mvp.presenter

import android.app.Activity
import android.text.TextUtils
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.example.locationtrackerkotlin.mvp.view.SignUpView
import com.google.firebase.auth.FirebaseAuth

class SignUpPresenterImpl(private val mAuth: FirebaseAuth) :
    SignUpPresenter, LifecycleObserver {

    private var mView: SignUpView? = null

    override fun signUpUser(email: String, password: String, confirmedPassword: String) {
        if (TextUtils.isEmpty(email)
            || TextUtils.isEmpty(password)
            || TextUtils.isEmpty(confirmedPassword)
        ) {
            mView?.showValidateError()
        } else {
            if (password != confirmedPassword) {
                mView?.showConfirmedPasswordError()
            } else {
                mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(mView as Activity) {
                        if (it.isSuccessful) {
                            mView?.showSignUpSuccess()
                        } else {
                            mView?.showSignUpError()
                        }
                    }
            }
        }
    }

    override fun attachView(view: SignUpView) {
        mView = view
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    override fun detachView() {
        mView = null
    }
}