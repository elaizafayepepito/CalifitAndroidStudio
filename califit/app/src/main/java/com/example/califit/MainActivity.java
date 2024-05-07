package com.example.califit;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create an Intent to start SecondActivity
        //Intent intent = new Intent(this, TemporaryActivity.class);
        Intent intent = new Intent(this, SplashscreenActivity.class);
        startActivity(intent);
    }
}