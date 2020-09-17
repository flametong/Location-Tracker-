package com.example.locationtrackerkotlin.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.locationtrackerkotlin.App
import com.example.locationtrackerkotlin.R
import com.example.locationtrackerkotlin.databinding.ActivitySignUpBinding
import com.example.locationtrackerkotlin.mvp.presenter.SignUpPresenter
import com.example.locationtrackerkotlin.mvp.view.SignUpView
import com.google.firebase.auth.FirebaseAuth
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import javax.inject.Inject

class SignUpActivity : MvpAppCompatActivity(), SignUpView {

    private lateinit var binding: ActivitySignUpBinding

    @Inject
    lateinit var mAuth: FirebaseAuth

    @InjectPresenter
    lateinit var mPresenter: SignUpPresenter

    @ProvidePresenter
    fun provideLoginPresenter(): SignUpPresenter {
        return SignUpPresenter(mAuth)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            val email = binding.edEmail.text.toString().trim()
            val password = binding.edPassword.text.toString().trim()
            val confirmedPassword = binding.edConfirmPassword.text.toString().trim()
            mPresenter.signUpUser(email, password, confirmedPassword)
        }
    }

    // Show success toast and go to LoginActivity
    override fun showSignUpSuccess() {
        Toast.makeText(this, R.string.sign_up_successful,
            Toast.LENGTH_SHORT).show()
        startActivity(Intent(baseContext, LoginActivity::class.java))
    }

    // Show error toast
    override fun showSignUpError() {
        Toast.makeText(this, R.string.sign_up_error,
            Toast.LENGTH_SHORT).show()
    }

    // Show error if text inputs are empty
    override fun showValidateError() {
        Toast.makeText(this, R.string.validate_error,
            Toast.LENGTH_SHORT).show()
    }

    // Show error if password mismatch
    override fun showConfirmedPasswordError() {
        Toast.makeText(this, R.string.password_mismatch,
            Toast.LENGTH_SHORT).show()
    }
}