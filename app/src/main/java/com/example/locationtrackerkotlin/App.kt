package com.example.locationtrackerkotlin

import android.app.Application
import com.example.locationtrackerkotlin.dagger.AppComponent
import com.example.locationtrackerkotlin.dagger.DaggerAppComponent

class App : Application() {
    companion object {
        lateinit var context: App
            private set
        lateinit var appComponent: AppComponent
            private set
    }

    override fun onCreate() {
        super.onCreate()
        context = this
        appComponent = DaggerAppComponent.create()
    }
}