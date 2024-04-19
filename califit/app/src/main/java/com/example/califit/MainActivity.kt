package com.example.califit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showSplashScreen()
    }

    private fun showSplashScreen() {
        supportFragmentManager.commit {
            replace(android.R.id.content, SplashScreenFragment())
        }
    }
}
