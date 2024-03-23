package com.rpm.dogedexapp.auth

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rpm.dogedexapp.R
import com.rpm.dogedexapp.databinding.FragmentLoginBinding
import com.rpm.dogedexapp.isValidEmail
import java.lang.Exception

class LoginFragment : Fragment() {

    interface LoginFragmentActions {

        fun onRegisterButtonClick()
        fun onLoginFieldsValidated(email: String, password: String)

    }

    private lateinit var loginFragmentActions: LoginFragmentActions

    private lateinit var binding: FragmentLoginBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        loginFragmentActions = try {
            context as LoginFragmentActions
        } catch ( ex: ClassCastException ) {
            throw ClassCastException("$context must implement LoginFragmentActions")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater)

        binding.loginRegisterButton.setOnClickListener {
            loginFragmentActions.onRegisterButtonClick()
        }

        binding.loginButton.setOnClickListener {
            validateFields()
        }

        return binding.root
    }

    private fun validateFields() {
        binding.emailInput.error = null
        binding.passwordInput.error = null

        val email = binding.emailEdit.text.toString()
        if ( !isValidEmail(email) ) {
            binding.emailInput.error = getString(R.string.email_is_not_valid)
            return
        }

        val password = binding.passwordEdit.text.toString()
        if ( password.isEmpty() ) {
            binding.passwordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        // Perform sign up
        loginFragmentActions.onLoginFieldsValidated(email, password)
    }

}