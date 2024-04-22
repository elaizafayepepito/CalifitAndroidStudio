package com.example.califit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.califit.R

class AgeRegistrationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_age_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Replace 'ageEditText' and 'nextButton' with the actual ID names from your XML layout
        val ageEditText = view.findViewById<EditText>(R.id.editTextAge)
        val nextButton = view.findViewById<Button>(R.id.buttonNextAge)

        nextButton.setOnClickListener {
            // Get the age from the EditText
            val ageInput = ageEditText.text.toString().toIntOrNull()

            if (ageInput != null && ageInput > 0) {
                // Here you can save the age to the ViewModel if you have one, or pass it to the next fragment

                // Navigate to the next fragment
                findNavController().navigate(R.id.action_ageRegistrationFragment_to_genderRegistrationFragment)
            } else {
                // Show an error message if the age input is invalid
                Toast.makeText(requireContext(), "Please enter a valid age.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
