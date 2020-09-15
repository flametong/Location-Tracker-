package com.example.locationtrackerkotlin.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.R
import com.example.locationtrackerkotlin.databinding.ActivityLoginBinding
import com.example.locationtrackerkotlin.mvp.presenter.LoginPresenterImpl
import com.example.locationtrackerkotlin.mvp.view.LoginView
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginView {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var mPresenter: LoginPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.appComponent.inject(this)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycle.addObserver(mPresenter)
        mPresenter.attachView(this)

        binding.btnLogin.setOnClickListener {
            val email = binding.edEmail.text.toString().trim()
            val password = binding.edPassword.text.toString().trim()
            mPresenter.signIn(email, password)
        }

        binding.btnSignUp.setOnClickListener {
            startActivity(Intent(baseContext, SignUpActivity::class.java))
        }
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

    override fun getContext(): Context {
        return this
    }
}