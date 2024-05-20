package com.example.califit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GenderRegistrationFragment extends AppCompatActivity {

    private String accountId;
    private String firstname;
    private String lastname;
    private String age;
    private Button buttonFinish;
    private String gender;
    private Button buttonMale;
    private Button buttonFemale;
    private String userKey;
    private DatabaseReference userDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gender_registration);

        userDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");

        Intent intent = getIntent();
        accountId = intent.getStringExtra("account_id");
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");
        age = intent.getStringExtra("age");
        Log.d("GenderRegistration", "Account ID received: " + accountId);
        Log.d("GenderRegistration", "Firstname received: " + firstname);
        Log.d("GenderRegistration", "Lastname received: " + lastname);
        Log.d("GenderRegistration", "Age received: " + age);

        buttonMale = findViewById(R.id.buttonMale);
        buttonFemale = findViewById(R.id.buttonFemale);
        buttonFinish = findViewById(R.id.buttonFinish);

        buttonMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGender("Male");
            }
        });

        buttonFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectGender("Female");
            }
        });

        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertUserData();
            }
        });
    }

    private void selectGender(String selectedGender) {
        if (selectedGender.equals("Male")) {
            gender = "Male";
            buttonMale.setEnabled(false);
            buttonFemale.setEnabled(true);
        } else if (selectedGender.equals("Female")) {
            gender = "Female";
            buttonMale.setEnabled(true);
            buttonFemale.setEnabled(false);
        }
        Log.d("GenderRegistration", "Selected gender: " + gender);
    }

    private void navigateToDashboard(String userId) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
    }

    public void saveUserDetailsInPreferences(Users user) {
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("user_id", userKey);
        editor.putString("account_id", user.getAccountId());
        editor.putString("firstname", user.getFirstname());
        editor.putString("lastname", user.getLastname());
        editor.putInt("age", user.getAge());
        editor.putString("gender", user.getGender());
        editor.apply(); // or editor.commit();
    }

    private void navigateToDashboardWithUserDetails(String userId) {
        userDbRef.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Users user = snapshot.getValue(Users.class);
                    if (user != null) {
                        saveUserDetailsInPreferences(user);
                        navigateToDashboard(userId);
                        Log.d("GenderRegistrationActivity", "User details: " + user.toString());
                    }
                } else {
                    Toast.makeText(GenderRegistrationFragment.this, "User not found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DashboardActivity", "Error fetching account details", error.toException());
                Toast.makeText(GenderRegistrationFragment.this, "Error fetching account details", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void insertUserData() {
        String accountId = this.accountId;
        String firstname = this.firstname;
        String lastname = this.lastname;
        int age = Integer.parseInt(this.age);
        String gender = this.gender;

        if (gender.isEmpty()) {
            Toast.makeText(GenderRegistrationFragment.this, "Please select a gender", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email already exists in the database
        userDbRef.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Account ID already exists, show an error message
                    Toast.makeText(GenderRegistrationFragment.this, "Account ID already exists in the database", Toast.LENGTH_SHORT).show();
                } else {
                    // Account ID does not exist, insert the data
                    Users users = new Users(accountId, firstname, lastname, age, gender);

                    // Push the data to the database and attach a completion listener
                    userDbRef.push().setValue(users, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                // Error occurred while inserting data
                                Toast.makeText(GenderRegistrationFragment.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                // Data inserted successfully, get the key assigned to the newly created account
                                userKey = databaseReference.getKey();
                                navigateToDashboardWithUserDetails(userKey);
                                Toast.makeText(GenderRegistrationFragment.this, "User created successfully!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(GenderRegistrationFragment.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
