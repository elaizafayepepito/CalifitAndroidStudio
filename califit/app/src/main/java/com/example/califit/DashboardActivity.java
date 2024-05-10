package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Button buttonStartJourney = findViewById(R.id.startButton);

        buttonStartJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToExerciseSelection();
            }
        });
    }

    public void navigateToExerciseSelection() {
        Intent intent = new Intent(this, ExerciseSelectionActivity.class);
        startActivity(intent);
    }
}
