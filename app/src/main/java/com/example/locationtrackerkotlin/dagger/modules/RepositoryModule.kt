package com.example.locationtrackerkotlin.dagger.modules

import androidx.room.Room
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.room.AppDatabase
import com.example.locationtrackerkotlin.util.Constants
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Provides
    @Singleton
    fun provideAppDatabase(): AppDatabase {
        return Room.databaseBuilder(
            App.context,
            AppDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }
}