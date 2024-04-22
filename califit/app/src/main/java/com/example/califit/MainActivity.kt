package com.example.califit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        showSplashScreen() // Call this function to show the splash screen
    }

    private fun showSplashScreen() {
        // Use supportFragmentManager to begin a transaction, replace a container in your layout (here using android.R.id.content as the container) with your SplashScreenFragment
        supportFragmentManager.commit {
            setReorderingAllowed(true) // Optimize the fragment transaction
            replace(android.R.id.content, SplashScreenFragment()) // Replace the entire activity content with the splash screen
        }
    }
}
