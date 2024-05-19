package com.example.califit;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private TextView profileName;
    private TextView profileAge;
    private TextView profileGender;
    private TextView profileEmail;
    private TextView profilePassword;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.completeName);
        profileAge = findViewById(R.id.userAge);
        profileGender = findViewById(R.id.userGender);
        profileEmail = findViewById(R.id.userEmail);
        profilePassword = findViewById(R.id.userPassword);

        Users user = getUserDetailsFromPreferences();
        String email = getEmailFromPreferences();
        String password = getPasswordFromPreferences();

        if (user != null) {
            profileName.setText(user.getFirstname() + " " + user.getLastname());
            profileAge.setText(String.valueOf(user.getAge()));
            profileGender.setText(user.getGender());
            profileEmail.setText(email);
            profilePassword.setText(password);
        } else {
            Log.d("ProfileActivity", "No user details found in SharedPreferences");
        }

        buttonLogout = findViewById(R.id.logoutButton);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    private void logout() {
        finishAffinity();
    }

    public Users getUserDetailsFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        String userId = sharedPreferences.getString("user_id", null);
        String accountId = sharedPreferences.getString("account_id", null);
        String firstname = sharedPreferences.getString("firstname", null);
        String lastname = sharedPreferences.getString("lastname", null);
        int age = sharedPreferences.getInt("age", -1);
        String gender = sharedPreferences.getString("gender", null);

        if (userId != null && accountId != null && firstname != null && lastname != null && age != -1 && gender != null) {
            return new Users(accountId, firstname, lastname, age, gender);
        }
        return null;
    }

    public String getEmailFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        return sharedPreferences.getString("email", null);
    }

    public String getPasswordFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        return sharedPreferences.getString("password", null);
    }
}
