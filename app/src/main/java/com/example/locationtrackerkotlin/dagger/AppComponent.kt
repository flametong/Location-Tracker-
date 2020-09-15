package com.example.locationtrackerkotlin.dagger

import com.example.locationtrackerkotlin.activity.LoginActivity
import com.example.locationtrackerkotlin.activity.SignUpActivity
import com.example.locationtrackerkotlin.activity.SplashScreen
import com.example.locationtrackerkotlin.dagger.modules.PresenterModule
import dagger.Component
import javax.inject.Singleton

@Component(modules = [PresenterModule::class])
@Singleton
interface AppComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(signUpActivity: SignUpActivity)
    fun inject(splashScreen: SplashScreen)
}