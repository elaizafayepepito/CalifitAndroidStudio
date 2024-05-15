package com.example.califit;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
                String url = "https://cebuinstituteoftechnology-my.sharepoint.com/:v:/g/personal/ginalyn_caneda_cit_edu/Eb8KH_U_ZFBEkW_eCsImIUUBEDw_srHsh7AOJgTVyKawHg?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=ht9NT0";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        Button pushupDemoButton = findViewById(R.id.pushupDemoBtn);
        pushupDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://cebuinstituteoftechnology-my.sharepoint.com/:v:/g/personal/ginalyn_caneda_cit_edu/ERQm29RtfHpOiFW_HBqR_CABRXCbbD9dm1F9JsrfM7ibaA?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=EDyA9O";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        Button squatDemoButton = findViewById(R.id.squatDemoBtn);
        squatDemoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://cebuinstituteoftechnology-my.sharepoint.com/:v:/g/personal/ginalyn_caneda_cit_edu/ERJ-e9hlKPNArp9yk-cV6RcBwTzfE5cLXwW-KcqOwwdpRw?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0NvcHkifX0&e=xSPIDi";
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

