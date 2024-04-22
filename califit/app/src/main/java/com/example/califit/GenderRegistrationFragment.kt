package com.example.califit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment

class GenderRegistrationFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gender_registration, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Make sure your view IDs in the XML are exactly "buttonFinish" and "imageViewGender"
        val buttonFinish = view.findViewById<Button>(R.id.buttonFinish)
        val imageViewGender = view.findViewById<ImageView>(R.id.imageViewGender)

        // Set up your UI event listeners here
        buttonFinish.setOnClickListener {
            // Handle finish button click
            // Example: Navigate to the next screen or submit the data
        }

        // Example of setting an image resource to ImageView
        // imageViewGender.setImageResource(R.drawable.your_image_resource)
    }
}
