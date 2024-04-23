package com.example.califit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class ExerciseSelection : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_exercise)

        val exerciseCard1 = findViewById<ExerciseCardView>(R.id.exerciseCard1)
        exerciseCard1.setCardTitle("Tabletop Crunch")
        exerciseCard1.setImageResource(R.drawable.tabletop_crunch)

        val exerciseCard2 = findViewById<ExerciseCardView>(R.id.exerciseCard2)
        exerciseCard2.setCardTitle("Inclined Pushup")
        exerciseCard2.setImageResource(R.drawable.inclined_pushup)

        val exerciseCard3 = findViewById<ExerciseCardView>(R.id.exerciseCard3)
        exerciseCard3.setCardTitle("Sumo Squat")
        exerciseCard3.setImageResource(R.drawable.sumo_squat)
    }
}
