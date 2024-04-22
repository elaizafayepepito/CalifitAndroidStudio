package com.example.califit

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.califit.databinding.FragmentGenderRegistrationBinding

class GenderRegistrationFragment : Fragment() {
    private var _binding: FragmentGenderRegistrationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGenderRegistrationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set up your UI event listeners here
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
