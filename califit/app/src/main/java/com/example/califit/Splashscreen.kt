package com.example.califit

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SplashScreenFragment : Fragment(R.layout.fragment_splashscreen) {

    private lateinit var califitLogo: ImageView
    private lateinit var flipAnimation: RotateAnimation

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        califitLogo = view.findViewById(R.id.califit_logo)
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
        flipAnimation.repeatCount = 1 // Change to 1 so it doesn't repeat indefinitely
        flipAnimation.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                // Ensure this runs on the main thread
                Handler(Looper.getMainLooper()).post {
                    navigateToWelcomePage()
                }
            }

            override fun onAnimationRepeat(animation: Animation?) {}
        })
        califitLogo.startAnimation(flipAnimation)
    }

    private fun navigateToWelcomePage() {
        //(activity as? MainActivity)?.navigateToWelcomePage()
    }
}