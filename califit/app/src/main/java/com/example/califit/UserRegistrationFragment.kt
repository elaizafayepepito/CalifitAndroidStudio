package com.example.califit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment

class UserRegistrationFragment : Fragment() {

    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_user_registration, container, false)
        emailEditText = view.findViewById(R.id.editTextEmail)
        passwordEditText = view.findViewById(R.id.editTextPassword)
        confirmPasswordEditText = view.findViewById(R.id.editTextConfirmPassword)
        signUpButton = view.findViewById(R.id.buttonSignUp)

        signUpButton.setOnClickListener {
            // Implement validation and registration logic here
        }

        return view
    }
}
