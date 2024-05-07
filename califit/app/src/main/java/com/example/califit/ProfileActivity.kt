package com.example.califit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.widget.Button

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val logoutButton: Button = findViewById(R.id.logoutButton)
        logoutButton.setOnClickListener {
            logout()
        }
    }

    private fun logout() {
        // Here you'd handle the logout process
        finish() // For now, just close the activity
    }
}
