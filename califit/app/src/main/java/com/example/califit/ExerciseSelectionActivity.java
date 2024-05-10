package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_selection);

        LinearLayout tabletopCrunchLayout = findViewById(R.id.exerciseTabletopCrunch);
        LinearLayout inclinedPushupLayout = findViewById(R.id.exerciseInclinedPushup);
        LinearLayout sumoSquatLayout = findViewById(R.id.exerciseSumoSquat);

        tabletopCrunchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToTabletopCrunch();
            }
        });

        inclinedPushupLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToInclinedPushup();
            }
        });

        sumoSquatLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                proceedToSumoSquat();
            }
        });
    }

    public void proceedToTabletopCrunch() {
        startActivity(new Intent(ExerciseSelectionActivity.this, TableTopCrunchActivity.class));
    }

    public void proceedToInclinedPushup() {
        startActivity(new Intent(ExerciseSelectionActivity.this, InclinedPushupActivity.class));
    }

    public void proceedToSumoSquat() {
        startActivity(new Intent(ExerciseSelectionActivity.this, SumoSquatActivity.class));
    }
}