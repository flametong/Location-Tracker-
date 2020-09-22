package com.example.locationtrackerkotlin.activity

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.R
import com.example.locationtrackerkotlin.databinding.ActivityLoginBinding
import com.example.locationtrackerkotlin.mvp.presenter.LoginPresenter
import com.example.locationtrackerkotlin.mvp.view.LoginView
import com.example.locationtrackerkotlin.util.PermissionsHandler
import com.example.locationtrackerkotlin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LoginActivity : MvpAppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding
    private val permissionsHandler = PermissionsHandler()

    @Inject
    lateinit var mAuth: FirebaseAuth

    @InjectPresenter
    lateinit var mPresenter: LoginPresenter

    @ProvidePresenter
    fun provideLoginPresenter(): LoginPresenter {
        return LoginPresenter(mAuth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)

        binding.apply {
            setContentView(root)

            // Try to log in button
            btnLogin.setOnClickListener {
                val email = edEmail.text.toString().trim()
                val password = edPassword.text.toString().trim()
                mPresenter.signIn(email, password)
            }

            // Go to SignUpActivity
            btnSignUp.setOnClickListener {
                startActivity(Intent(baseContext, SignUpActivity::class.java))
            }
        }
    }

    override fun onStart() {
        super.onStart()
        mPresenter.provideLocationPermissions()
    }

    // Show success toast and start tracker activity
    override fun showSignInSuccess() {
        Toast.makeText(
            this, R.string.login_successful,
            Toast.LENGTH_SHORT
        ).show()

        startActivity(Intent(baseContext, TrackerActivity::class.java))
    }

    // Show error toast
    override fun showSignInError() {
        Toast.makeText(
            this, R.string.login_failed,
            Toast.LENGTH_SHORT
        ).show()
    }

    // Show error if text inputs are empty
    override fun showValidateError() {
        Toast.makeText(
            this, R.string.validate_error,
            Toast.LENGTH_SHORT
        ).show()
    }

    // Get background location permission
    override fun requestLocationPermissions() {
        val isHaveLocationPermission =
            permissionsHandler.checkHasPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)

        if (!isHaveLocationPermission) {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                permissionsHandler.requestPermission(
                    this, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ),
                    Constants.PERMISSION_REQUEST_LOCATION
                )
            } else {
                permissionsHandler.requestPermission(
                    this, arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_BACKGROUND_LOCATION,
                    ),
                    Constants.PERMISSION_REQUEST_LOCATION
                )
            }
        }
    }
}