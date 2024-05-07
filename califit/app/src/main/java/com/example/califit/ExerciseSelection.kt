package com.example.califit

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class ExerciseSelectionActivity : AppCompatActivity() {

    private var selectedExercise: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise_selection)
    }

    fun onSelectExercise(view: View) {
        // This method will be called when any exercise is clicked
        selectedExercise = when (view.id) {
            R.id.exerciseTabletopCrunch -> "Tabletop Crunch"
            R.id.exerciseInclinedPushup -> "Inclined Pushup"
            R.id.exerciseSumoSquat -> "Sumo Squat"
            else -> null
        }
    }

    fun onProceed(view: View) {
        // Handle proceed click here
        if (selectedExercise != null) {
            // Navigate to next screen or display selected exercise
        }
    }
}
