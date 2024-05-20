package com.example.califit

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.califit.RegistrationViewModel

class RegistrationViewModel : ViewModel() {
    private val _age = MutableLiveData<Int>()
    val age: LiveData<Int> get() = _age

    // Add other registration-related data as needed

    fun setAge(age: Int) {
        _age.value = age
    }

    // Add other methods to handle registration data as needed
}
