package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseSelectionActivity extends AppCompatActivity {

    private String exerciseType = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_selection);

        Button buttonProceed = findViewById(R.id.buttonProceed);

        buttonProceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onProceed();
            }
        });

        LinearLayout tabletopCrunchLayout = findViewById(R.id.exerciseTabletopCrunch);
        LinearLayout inclinedPushupLayout = findViewById(R.id.exerciseInclinedPushup);
        LinearLayout sumoSquatLayout = findViewById(R.id.exerciseSumoSquat);

        tabletopCrunchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectExercise("Tabletop Crunch");
            }
        });

        inclinedPushupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectExercise("Inclined Pushup");
            }
        });

        sumoSquatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSelectExercise("Sumo Squat");
            }
        });
    }

    public void onSelectExercise(String exerciseType) {
        this.exerciseType = exerciseType;
    }

    public void onProceed() {
        if (exerciseType.equals("Tabletop Crunch")) {
            startActivity(new Intent(ExerciseSelectionActivity.this, TableTopCrunchActivity.class));
        } else if (exerciseType.equals("Inclined Pushup")) {
            startActivity(new Intent(ExerciseSelectionActivity.this, InclinedPushupActivity.class));
        } else if (exerciseType.equals("Sumo Squat")) {
            startActivity(new Intent(ExerciseSelectionActivity.this, SumoSquatActivity.class));
        } else {
            Log.d("NullExerciseType", "Please select an exercise.");
        }
    }
}