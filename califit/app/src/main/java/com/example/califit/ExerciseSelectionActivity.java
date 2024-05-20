package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

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
        String timeStarted = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Intent intent = new Intent(ExerciseSelectionActivity.this, TableTopCrunchActivity.class);
        intent.putExtra("time_started", timeStarted);
        startActivity(intent);
    }

    public void proceedToInclinedPushup() {
        String timeStarted = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Intent intent = new Intent(ExerciseSelectionActivity.this, InclinedPushupActivity.class);
        intent.putExtra("time_started", timeStarted);
        startActivity(intent);
    }

    public void proceedToSumoSquat() {
        String timeStarted = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        Intent intent = new Intent(ExerciseSelectionActivity.this, SumoSquatActivity.class);
        intent.putExtra("time_started", timeStarted);
        startActivity(intent);
    }
}