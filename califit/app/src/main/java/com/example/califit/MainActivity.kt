package com.example.califit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showSplashScreen();
    }

    private fun showSplashScreen() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(android.R.id.content, SplashScreenFragment())
            // Add this line to add SplashScreenFragment to the back stack
            addToBackStack(null)
        }
    }

    // Add this function to navigate to the welcome page after the splash screen
    fun navigateToWelcomePage() {
        showWelcomePage()
    }

    private fun showWelcomePage() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(android.R.id.content, WelcomeFragment())
            // Don't add WelcomeFragment to the back stack
        }
    }
}