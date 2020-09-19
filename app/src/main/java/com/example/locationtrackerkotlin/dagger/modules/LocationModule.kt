package com.example.locationtrackerkotlin.dagger.modules

import com.example.locationtrackerkotlin.App
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class LocationModule {

    @Provides
    @Singleton
    fun provideLocationClient(): FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(App.context)


    @Provides
    @Singleton
    fun provideLocationRequest(): LocationRequest = LocationRequest()

}