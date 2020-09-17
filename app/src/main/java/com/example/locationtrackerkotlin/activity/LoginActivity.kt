package com.example.locationtrackerkotlin.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.R
import com.example.locationtrackerkotlin.databinding.ActivityLoginBinding
import com.example.locationtrackerkotlin.mvp.presenter.LoginPresenter
import com.example.locationtrackerkotlin.mvp.view.LoginView
import com.example.locationtrackerkotlin.util.Constants
import com.google.firebase.auth.FirebaseAuth
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class LoginActivity : MvpAppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding

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
        setContentView(binding.root)

        // Try to log in button
        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString().trim()
            val password = binding.edPassword.text.toString().trim()
            mPresenter.signIn(email, password)
        }

        // Go to SignUpActivity
        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(baseContext, SignUpActivity::class.java))
        }
    }

    // Get background location permission
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onStart() {
        super.onStart()
        mPresenter.getLocationPermission(context = this)
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

    // Show alert with request location permissions
    override fun requestLocationPermission() {
        if (Constants.CURRENT_VERSION < Constants.VERSION_Q) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Constants.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Constants.ACCESS_FINE_LOCATION
                    ),
                    LoginPresenter.PERMISSION_REQUEST_LOCATION
                )
            }
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(
                    this, Constants.ACCESS_FINE_LOCATION
                )
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(
                        Constants.ACCESS_FINE_LOCATION,
                        Constants.ACCESS_BACKGROUND_LOCATION
                    ),
                    LoginPresenter.PERMISSION_REQUEST_LOCATION
                )
            }
        }
    }
}