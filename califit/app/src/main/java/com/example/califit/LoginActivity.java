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

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private UserAccountService userAccountService;
    private EditText editTextEmail;
    private EditText editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);

        userAccountService = new UserAccountService(this);

        Button buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogin();
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

    /*private void handleLogin() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString();

        JSONObject loginData = new JSONObject();
        try {
            loginData.put("email", email);
            loginData.put("password", password);
        } catch (Exception e) {
            Log.e("SumoSquatActivity", "Error creating test login data: " + e.getMessage());
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

                    // Perform any other required operations upon successful login
                } catch (Exception e) {
                    Log.e("LOGIN", "Error parsing login response: " + e.getMessage());
                }
            }
        };

        // Define an error listener to handle any login errors
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the error message
                Log.e("LOGIN", "Login error: " + error.getMessage());
            }
        };

        // Call the login method of UserAccountService with the test data and listeners
        userAccountService.login(loginData, responseListener, errorListener);
    }*/
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
}
