package com.example.califit;

import android.annotation.SuppressLint;
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
    private DatabaseReference usersDbRef;
    private DatabaseReference accountsDbRef;
    private DatabaseReference squatsDbRef;
    private DatabaseReference crunchesDbRef;
    private DatabaseReference pushupsDbRef;
    private TextView squatsTextView;
    private TextView crunchesTextView;
    private TextView pushupsTextView;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");

        Log.d("DashboardActivity", "User ID received: " + userId);

        usersDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");
        accountsDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Accounts");
        squatsDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Squats");
        crunchesDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Crunches");
        pushupsDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Pushups");

        if (userId != null) {
            fetchUserDetailsById(userId);
        } else {
            Toast.makeText(this, "No valid user ID provided", Toast.LENGTH_SHORT).show();
        }

        squatsTextView = findViewById(R.id.squatProgress);
        crunchesTextView = findViewById(R.id.crunchProgress);
        pushupsTextView = findViewById(R.id.pushupProgress);

        fetchExerciseData(squatsDbRef, squatsTextView);
        fetchExerciseData(crunchesDbRef, crunchesTextView);
        fetchExerciseData(pushupsDbRef, pushupsTextView);

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

        // Setup for "View tcProgress"
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tcViewProgress = findViewById(R.id.tcViewProgress);
        tcViewProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToTcLogs();
            }
        });

        TextView ssViewProgress = findViewById(R.id.ssViewProgress);
        ssViewProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToSsLogs();
            }
        });

        TextView ipViewProgress = findViewById(R.id.ipViewProgress);
        ipViewProgress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToIpLogs();
            }
        });
    }

    public void navigateToTcLogs() {
        Intent intent = new Intent(this, TcList.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }

    public void navigateToSsLogs() {
        Intent intent = new Intent(this, SsList.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }

    public void navigateToIpLogs() {
        Intent intent = new Intent(this, IpList.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
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

    private void fetchExerciseData(DatabaseReference exerciseRef, final TextView textView) {
        exerciseRef.orderByChild("userId").equalTo(userId).limitToLast(1).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Get the latest exercise data
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Exercise exercise = snapshot.getValue(Exercise.class);
                        if (exercise != null) {
                            // Update the TextView with the latest exercise reps
                            textView.setText(String.valueOf(exercise.getReps()));
                        }
                    }
                } else {
                    // If no data exists, set reps to 0
                    textView.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle onCancelled event
            }
        });
    }
}
