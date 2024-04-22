package com.example.califit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Button
import androidx.fragment.app.Fragment

class NameRegistrationFragment : Fragment() {

    private lateinit var editTextFirstName: EditText
    private lateinit var editTextLastName: EditText
    private lateinit var buttonNext: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_name_registration, container, false)
        editTextFirstName = view.findViewById(R.id.editTextFirstName)
        editTextLastName = view.findViewById(R.id.editTextLastName)
        buttonNext = view.findViewById(R.id.buttonNext)

        buttonNext.setOnClickListener {
            val firstName = editTextFirstName.text.toString().trim()
            val lastName = editTextLastName.text.toString().trim()
            if (firstName.isEmpty() || lastName.isEmpty()) {
                // Show error message if needed
            } else {
                // Proceed to the next registration step
                // Pass data via navigation arguments or ViewModel
            }
        }

        return view
    }
}
