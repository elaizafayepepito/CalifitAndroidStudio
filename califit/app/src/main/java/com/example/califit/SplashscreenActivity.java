package com.example.califit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashscreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);

        ImageView logo = findViewById(R.id.logo);
        Animation rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        logo.startAnimation(rotateAnimation);

        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

                if (sharedPreferences.contains("user_id")) {
                    String value = sharedPreferences.getString("user_id", null);
                    Intent intent = new Intent(SplashscreenActivity.this, DashboardActivity.class);
                    intent.putExtra("user_id", value);
                    startActivity(intent);
                } else {
                    startActivity(new Intent(SplashscreenActivity.this, WelcomeActivity.class));
                }
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
    }
}
