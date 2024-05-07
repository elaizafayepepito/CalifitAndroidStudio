package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WelcomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_welcome);

        Button buttonStartJourney = findViewById(R.id.button_start_journey);

        buttonStartJourney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startJourney();
            }
        });
    }

    public void startJourney() {
        Intent intent = new Intent(this, TemporaryActivity.class);
        startActivity(intent);
    }
}
