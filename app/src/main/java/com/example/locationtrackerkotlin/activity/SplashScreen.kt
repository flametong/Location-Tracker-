package com.example.locationtrackerkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.locationtrackerkotlin.App
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class SplashScreen : AppCompatActivity() {

    @Inject
    lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)

        // Check if user is authorized
        if (mAuth.currentUser == null) {
            startActivity(Intent(baseContext, LoginActivity::class.java))
        } else {
            startActivity(Intent(baseContext, TrackerActivity::class.java))
        }
        finish()
    }
}