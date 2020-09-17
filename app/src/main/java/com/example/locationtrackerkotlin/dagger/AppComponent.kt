package com.example.locationtrackerkotlin.dagger

import com.example.locationtrackerkotlin.activity.LoginActivity
import com.example.locationtrackerkotlin.activity.SignUpActivity
import com.example.locationtrackerkotlin.activity.SplashScreen
import com.example.locationtrackerkotlin.activity.TrackerActivity
import com.example.locationtrackerkotlin.dagger.modules.LocationModule
import com.example.locationtrackerkotlin.dagger.modules.FirebaseModule
import com.example.locationtrackerkotlin.dagger.modules.RepositoryModule
import com.example.locationtrackerkotlin.room.DatabaseActions
import com.example.locationtrackerkotlin.service.LocationService
import dagger.Component
import javax.inject.Singleton

@Component(modules = [
    FirebaseModule::class,
    RepositoryModule::class,
    LocationModule::class])
@Singleton
interface AppComponent {
    fun inject(loginActivity: LoginActivity)
    fun inject(signUpActivity: SignUpActivity)
    fun inject(trackerActivity: TrackerActivity)
    fun inject(splashScreen: SplashScreen)
    fun inject(locationService: LocationService)
    fun inject(databaseActions: DatabaseActions)
}