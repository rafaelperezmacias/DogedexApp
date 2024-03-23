package com.rpm.dogedexapp.auth

import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rpm.dogedexapp.R
import com.rpm.dogedexapp.databinding.FragmentSignUpBinding
import com.rpm.dogedexapp.isValidEmail

class SignUpFragment : Fragment() {

    interface SignUpFragmentActions {

        fun onSignUpFieldsValidated(email: String, password: String, passwordConfirmation: String)

    }

    private lateinit var signUpFragmentActions: SignUpFragmentActions

    override fun onAttach(context: Context) {
        super.onAttach(context)
        signUpFragmentActions = try {
            context as SignUpFragmentActions
        } catch ( ex: ClassCastException ) {
            throw ClassCastException("$context must implement SignUpFragmentActions")
        }
    }

    private lateinit var binding: FragmentSignUpBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignUpBinding.inflate(layoutInflater)
        setUpSignUpButton()
        return binding.root
    }

    private fun setUpSignUpButton() {
        binding.signUpButton.setOnClickListener {
            validateFields()
        }
    }

    private fun validateFields() {
        binding.emailInput.error = null
        binding.passwordInput.error = null
        binding.confirmPasswordInput.error = null

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

        val passwordConfirmation = binding.confirmPasswordEdit.text.toString()
        if ( passwordConfirmation.isEmpty() ) {
            binding.confirmPasswordInput.error = getString(R.string.password_must_not_be_empty)
            return
        }

        if ( password != passwordConfirmation ) {
            binding.passwordInput.error = getString(R.string.passwords_do_not_match)
            return
        }

        // Perform sign up
        signUpFragmentActions.onSignUpFieldsValidated(email, password, passwordConfirmation)
    }


}