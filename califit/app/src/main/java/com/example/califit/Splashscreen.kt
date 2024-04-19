package com.example.califit

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import java.util.Timer
import java.util.TimerTask

class SplashScreenFragment : Fragment(R.layout.fragment_splashscreen) {

    private lateinit var appLogo: ImageView
    private lateinit var flipValue: Timer
    private lateinit var flipAnimation: RotateAnimation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appLogo = view.findViewById(R.id.app_logo)

        flipValue = Timer()
        flipValue.schedule(object : TimerTask() {
            override fun run() {
                navigateToWelcomePage()
            }
        }, 100000) // Adjust the timing if needed

        setupRotationAnimation()
    }

    private fun setupRotationAnimation() {
        flipAnimation = RotateAnimation(
            0f, 360f,
            Animation.RELATIVE_TO_SELF, 0.5f,
            Animation.RELATIVE_TO_SELF, 0.5f
        )
        flipAnimation.duration = 2000
        flipAnimation.interpolator = LinearInterpolator()
        flipAnimation.repeatCount = Animation.INFINITE
        appLogo.startAnimation(flipAnimation)
    }

    private fun navigateToWelcomePage() {
        // Implement navigation logic to WelcomePage
        // For example:
        // val action = SplashScreenFragmentDirections.actionSplashscreenToWelcomePage()
        // findNavController().navigate(action)
    }
}