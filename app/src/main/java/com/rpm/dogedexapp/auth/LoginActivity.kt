package com.rpm.dogedexapp.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.rpm.dogedexapp.MainActivity
import com.rpm.dogedexapp.R
import com.rpm.dogedexapp.api.ApiResponseStatus
import com.rpm.dogedexapp.databinding.ActivityLoginBinding
import com.rpm.dogedexapp.model.User

class LoginActivity : AppCompatActivity(),
    LoginFragment.LoginFragmentActions,
    SignUpFragment.SignUpFragmentActions
{

    private val authViewModel: AuthViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        authViewModel.status.observe(this) { status ->
            when ( status ) {
                is ApiResponseStatus.Error -> {
                    binding.loadingWheel.visibility = View.GONE
                    showErrorDialog(status.message)
                }
                is ApiResponseStatus.Loading -> {
                    binding.loadingWheel.visibility = View.VISIBLE
                }
                is ApiResponseStatus.Success -> {
                    binding.loadingWheel.visibility = View.GONE
                }
            }
        }

        authViewModel.user.observe(this) { user ->
            if ( user != null ) {
                User.setLoggedInUser(this, user)
                startMainActivity()
            }
        }
    }

    override fun onRegisterButtonClick() {
        findNavController(R.id.nav_host_fragment)
            .navigate(LoginFragmentDirections.actionLoginFragmentToSignUpFragment())
    }

    override fun onLoginFieldsValidated(email: String, password: String) {
        authViewModel.login(email, password)
    }

    override fun onSignUpFieldsValidated(
        email: String,
        password: String,
        passwordConfirmation: String
    ) {
        authViewModel.signUp(email, password, passwordConfirmation)
    }

    private fun showErrorDialog(message: Int) {
        AlertDialog.Builder(this)
            .setTitle(R.string.there_was_an_error)
            .setMessage(message)
            .setPositiveButton(android.R.string.ok) { _, _ -> }
            .create()
            .show()
    }

    private fun startMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}