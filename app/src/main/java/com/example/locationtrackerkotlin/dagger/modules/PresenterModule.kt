package com.example.locationtrackerkotlin.dagger.modules

import com.example.locationtrackerkotlin.mvp.presenter.LoginPresenterImpl
import com.example.locationtrackerkotlin.mvp.presenter.SignUpPresenterImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresenterModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @Provides
    @Singleton
    fun provideSignInPresenterImpl(mAuth: FirebaseAuth): LoginPresenterImpl {
        return LoginPresenterImpl(mAuth)
    }

    @Provides
    @Singleton
    fun provideSignUpPresenterImpl(mAuth: FirebaseAuth): SignUpPresenterImpl {
        return SignUpPresenterImpl(mAuth)
    }
}