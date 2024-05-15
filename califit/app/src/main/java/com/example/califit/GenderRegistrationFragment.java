package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class GenderRegistrationFragment extends AppCompatActivity {

    private String accountId;
    private String firstname;
    private String lastname;
    private String age;
    private Button buttonFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gender_registration);

        Intent intent = getIntent();
        accountId = intent.getStringExtra("account_id");
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");
        age = intent.getStringExtra("age");
        Log.d("GenderRegistration", "Account ID received: " + accountId);
        Log.d("GenderRegistration", "Firstname received: " + firstname);
        Log.d("GenderRegistration", "Lastname received: " + lastname);
        Log.d("GenderRegistration", "Age received: " + age);

        buttonFinish = findViewById(R.id.buttonFinish);

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToDashboard();
            }
        });
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }
}
