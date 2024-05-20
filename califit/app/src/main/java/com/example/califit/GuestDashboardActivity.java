package com.example.califit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GuestDashboardActivity extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard_guest);

        Button buttonStartJourney = findViewById(R.id.startButton);

        buttonStartJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToExerciseSelection();
            }
        });

        Button crunchDemoButton = findViewById(R.id.crunchDemoBtn);
        crunchDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://drive.google.com/file/d/15XBmvu3igicBvT6csGxq6sez2aEOLRg3/view?usp=sharing";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        Button pushupDemoButton = findViewById(R.id.pushupDemoBtn);
        pushupDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://drive.google.com/file/d/1Mk35brOvqD_QRQpnpUFHkHOmjeMggw3Z/view?usp=sharing";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        Button squatDemoButton = findViewById(R.id.squatDemoBtn);
        squatDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://drive.google.com/file/d/1NdczJDFi-7IL9qBhcDtlEOqxFB6-0YEK/view?usp=sharing";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

    }
    public void navigateToExerciseSelection() {
        Intent intent = new Intent(this, ExerciseSelectionActivity.class);
        startActivity(intent);
    }

}

