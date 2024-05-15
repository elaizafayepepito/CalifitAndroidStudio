package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

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
                createUser();
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
}
