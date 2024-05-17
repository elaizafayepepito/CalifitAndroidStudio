package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

public class GenderRegistrationFragment extends AppCompatActivity {

    private String accountId;
    private String firstname;
    private String lastname;
    private String age;
    private Button buttonFinish;
    private String gender;
    private Button buttonMale;
    private Button buttonFemale;
    private UserAccountService userAccountService;
    DatabaseReference userDbRef;

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

        userAccountService = new UserAccountService(this);

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
                //createUser();
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

    private void createUser() {
        try {
            JSONObject userData = new JSONObject();
            userData.put("accountId", accountId);
            userData.put("firstname", firstname);
            userData.put("lastname", lastname);
            userData.put("age", Integer.parseInt(age));
            userData.put("gender", gender);

            userAccountService.createUser(userData, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        if (message.equals("User created successfully")) {
                            Toast.makeText(GenderRegistrationFragment.this, message, Toast.LENGTH_LONG).show();
                            navigateToDashboard();
                        } else {
                            Toast.makeText(GenderRegistrationFragment.this, message, Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(GenderRegistrationFragment.this, "Error parsing response", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    Toast.makeText(GenderRegistrationFragment.this, "Error creating user: " + error.getMessage(), Toast.LENGTH_LONG).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error creating user data", Toast.LENGTH_LONG).show();
        }
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
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
                                String userKey = databaseReference.getKey();
                                navigateToDashboard();
                                Toast.makeText(GenderRegistrationFragment.this, "User data inserted! ID: " + userKey, Toast.LENGTH_SHORT).show();
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
