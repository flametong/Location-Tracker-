package com.example.locationtrackerkotlin.activity

import android.content.Context
import android.content.Intent
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.R
import com.example.locationtrackerkotlin.databinding.ActivityTrackerBinding
import com.example.locationtrackerkotlin.dialog.LocationRequestDialog
import com.example.locationtrackerkotlin.mvp.presenter.TrackerPresenterImpl
import com.example.locationtrackerkotlin.mvp.view.TrackerView
import com.example.locationtrackerkotlin.room.AppDatabase
import com.example.locationtrackerkotlin.util.Constants
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class TrackerActivity : AppCompatActivity(), TrackerView {

    private lateinit var binding: ActivityTrackerBinding

    @Inject
    lateinit var mPresenter: TrackerPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        binding = ActivityTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(mPresenter)
        mPresenter.attachView(this)

        // Start location tracker
        binding.btnLaunchTracker.setOnClickListener {
            mPresenter.launchService()
        }

        // Stop location tracker
        binding.btnStopTracker.setOnClickListener {
            mPresenter.stopService()
        }

        // Sign out and stop location tracker
        binding.btnSignOut.setOnClickListener {
            mPresenter.signOutUser()
        }
    }

    // Go to LoginActivity after sign out
    // and show sign out toast
    override fun signOutUser() {
        startActivity(Intent(baseContext, LoginActivity::class.java))
        Toast.makeText(
            this, R.string.sign_out_successful,
            Toast.LENGTH_SHORT
        ).show()
    }

    // Show dialog that requires GPS to be enabled
    // and open the settings
    override fun showRequestLocationDialog() {
        val locationManager: LocationManager =
            getSystemService(Context.LOCATION_SERVICE) as LocationManager

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
            || !locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        ) {
            LocationRequestDialog().show(
                supportFragmentManager,
                Constants.DIALOG_TAG
            )
        }
    }

    // Show launch service toast
    override fun showStartLocationService() {
        Toast.makeText(
            this, R.string.tracker_launched,
            Toast.LENGTH_SHORT
        ).show()
    }

    // Show toast if location tracker is already running
    override fun showServiceAlreadyRunning() {
        Toast.makeText(
            this, R.string.service_already_running,
            Toast.LENGTH_SHORT
        ).show()
    }

    // Show tracker stop toast
    override fun showStopLocationService() {
        Toast.makeText(
            this, R.string.tracker_stopped,
            Toast.LENGTH_SHORT
        ).show()
    }

    override fun getContext(): Context {
        return this
    }
}