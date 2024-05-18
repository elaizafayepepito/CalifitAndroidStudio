package com.example.califit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DashboardActivity extends AppCompatActivity {

    private String userId;
    private String accountId;
    private DatabaseReference usersDbRef;
    private DatabaseReference accountsDbRef;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");
        accountId = intent.getStringExtra("account_id");

        Log.d("DashboardActivity", "User ID received: " + userId);
        Log.d("DashboardActivity", "Account ID received: " + accountId);

        usersDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");
        accountsDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Accounts");

        if (userId != null) {
            fetchUserDetailsById(userId);
        } else if (accountId != null) {
            fetchUserDetailsByAccountId(accountId);
        } else {
            Toast.makeText(this, "No valid user ID or account ID provided", Toast.LENGTH_SHORT).show();
        }

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

        TextView textViewEditProfile = findViewById(R.id.editProfile);
        textViewEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToProfile();
            }
        });

    }

    public void navigateToExerciseSelection() {
        Intent intent = new Intent(this, ExerciseSelectionActivity.class);
        startActivity(intent);
    }

    public void navigateToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

    private void fetchUserDetailsById(String userId) {
        usersDbRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null) {
                        saveUserDetailsInPreferences(user, userId);
                        Log.d("DashboardActivity", "User details: " + user.toString());
                        fetchAccountDetails(user.getAccountId());
                    }
                } else {
                    Toast.makeText(DashboardActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DashboardActivity", "Error fetching user details", error.toException());
                Toast.makeText(DashboardActivity.this, "Error fetching user details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchUserDetailsByAccountId(String accountId) {
        usersDbRef.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot userSnapshot : snapshot.getChildren()) {
                        Users user = userSnapshot.getValue(Users.class);
                        if (user != null) {
                            saveUserDetailsInPreferences(user, userSnapshot.getKey());
                            Log.d("DashboardActivity", "User details: " + user.toString());
                            fetchAccountDetails(user.getAccountId());
                        }
                    }
                } else {
                    Toast.makeText(DashboardActivity.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DashboardActivity", "Error fetching user details", error.toException());
                Toast.makeText(DashboardActivity.this, "Error fetching user details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchAccountDetails(String accountId) {
        accountsDbRef.child(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Accounts account = snapshot.getValue(Accounts.class);
                    if (account != null) {
                        saveAccountDetailsInPreferences(account);
                        Log.d("DashboardActivity", "Account details: " + account.toString());
                    }
                } else {
                    Toast.makeText(DashboardActivity.this, "Account not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DashboardActivity", "Error fetching account details", error.toException());
                Toast.makeText(DashboardActivity.this, "Error fetching account details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void saveUserDetailsInPreferences(Users user, String userId) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", userId);
        editor.putString("account_id", user.getAccountId());
        editor.putString("firstname", user.getFirstname());
        editor.putString("lastname", user.getLastname());
        editor.putInt("age", user.getAge());
        editor.putString("gender", user.getGender());
        editor.apply(); // or editor.commit();
    }

    public void saveAccountDetailsInPreferences(Accounts account) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", account.getEmail());
        editor.putString("password", account.getPassword());
        editor.apply(); // or editor.commit();
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
            this.userId = userId;
            return new Users(accountId, firstname, lastname, age, gender);
        }
        return null;
    }

}
