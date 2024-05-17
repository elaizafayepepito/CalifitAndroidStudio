package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private UserAccountService userAccountService;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private DatabaseReference accountDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        accountDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Accounts");

        userAccountService = new UserAccountService(this);

        Button buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handleLogin();
                login();
            }
        });

        // Add the TextView for signing up
        TextView textViewSignup = findViewById(R.id.textViewSignup);
        makePartOfTextClickable(textViewSignup, "Don't have an account? ", "Signup");

        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    private void makePartOfTextClickable(TextView textView, String nonLinkText, String linkText) {
        SpannableString spannableString = new SpannableString(nonLinkText + linkText);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                navigateToUserRegistration();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);  // Set to false if you don't want underline
                ds.setColor(getResources().getColor(R.color.white)); // Define this color in your colors.xml
            }
        };

        spannableString.setSpan(clickableSpan, nonLinkText.length(), (nonLinkText + linkText).length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spannableString);
        textView.setMovementMethod(LinkMovementMethod.getInstance()); // This makes the link clickable
    }

    public void navigateToUserRegistration() {
        Intent intent = new Intent(this, UserRegistrationFragment.class);
        startActivity(intent);
    }

    private void navigateToDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }

    private void handleLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", email);
            loginData.put("password", password);
        } catch (Exception e) {
            Log.e("LoginActivity", "Error creating login data: " + e.getMessage());
            return;
        }

        // Define a response listener to handle the successful login response
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Parse the response JSON
                    String message = response.getString("message");
                    String accountId = response.getString("account_id");

                    // Log the success message and account ID
                    Log.d("LOGIN", "Login successful: " + message + ", Account ID: " + accountId);

                    // Only navigate to the DashboardActivity if login is successful
                    navigateToDashboard();

                } catch (Exception e) {
                    Log.e("LOGIN", "Error parsing login response: " + e.getMessage());
                    showLoginErrorMessage("An error occurred while processing the login response.");
                }
            }
        };

        // Define an error listener to handle any login errors
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the error message and show error message to user
                Log.e("LOGIN", "Login error: " + error.getMessage());
                showLoginErrorMessage("Incorrect email or password. Please try again.");
            }
        };

        // Call the login method of UserAccountService with the data and listeners
        userAccountService.login(loginData, responseListener, errorListener);
    }

    private void showLoginErrorMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void login() {
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Email and password cannot be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        accountDbRef.orderByChild("email").equalTo(email).get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {
                if (!task.isSuccessful()) {
                    Log.e("firebase", "Error getting data", task.getException());
                    Toast.makeText(LoginActivity.this, "Error getting data from database", Toast.LENGTH_SHORT).show();
                } else {
                    DataSnapshot dataSnapshot = task.getResult();
                    if (dataSnapshot.exists()) {
                        // Email found, now check password
                        boolean isPasswordCorrect = false;
                        for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                            Accounts account = accountSnapshot.getValue(Accounts.class);
                            if (account != null && account.getPassword().equals(password)) {
                                // Correct password
                                isPasswordCorrect = true;
                                navigateToDashboard();
                                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        if (!isPasswordCorrect) {
                            // Password incorrect
                            Toast.makeText(LoginActivity.this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        // Email not found
                        Toast.makeText(LoginActivity.this, "Email not found. Please sign up.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
