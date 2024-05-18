package com.example.califit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRegistrationFragment extends AppCompatActivity {

    private UserAccountService userAccountService;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private String id;
    private Button buttonSignUp;
    DatabaseReference accountDbRef;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_registration);

        accountDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Accounts");

        userAccountService = new UserAccountService(this);

        editTextEmail = findViewById(R.id.editTextEmailSignup);
        editTextPassword = findViewById(R.id.editTextPasswordSignup);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPasswordSignup);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //handleSignUp();
                insertAccountData();
            }
        });

        TextView textViewLogin = findViewById(R.id.textViewLogin);
        makePartOfTextClickable(textViewLogin, "Already have an account? ", "Sign In");

        // Setup for "Continue as Guest"
        TextView textViewContinueAsGuest = findViewById(R.id.textViewGuest);
        textViewContinueAsGuest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateAsGuest();
            }
        });
        TextView textViewGuest = findViewById(R.id.textViewGuest);
        String guestText = "Continue as Guest";
        SpannableString content = new SpannableString(guestText);
        content.setSpan(new UnderlineSpan(), 0, guestText.length(), 0);
        textViewContinueAsGuest.setText(content);

    }

    private void makePartOfTextClickable(TextView textView, String nonLinkText, String linkText) {
        SpannableString spannableString = new SpannableString(nonLinkText + linkText);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                navigateToLogin();
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



    private void navigateAsGuest() {
        Intent intent = new Intent(this, GuestDashboardActivity.class); // Assuming you have a GuestActivity
        startActivity(intent);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class); // Correctly referring to LoginActivity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    /*private void handleSignUp() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject accountData = new JSONObject();
        try {
            accountData.put("email", email);
            accountData.put("password", password);
        } catch (Exception e) {
            Log.e("UserRegistration", "Error creating JSON data: " + e.getMessage());
            return;
        }

        // Define a response listener to handle the successful account creation response
        Response.Listener<JSONObject> responseListener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Parse the response JSON
                    String message = response.getString("message");
                    String accountId = response.getString("account_id");

                    // Log the success message and account ID
                    Log.d("UserRegistration", "Account created successfully: " + message + ", Account ID: " + accountId);
                    Toast.makeText(UserRegistrationFragment.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                    // Navigate to the NameRegistrationFragment
                    navigateToNameRegistration(accountId);
                } catch (Exception e) {
                    Log.e("UserRegistration", "Error parsing account creation response: " + e.getMessage());
                    Toast.makeText(UserRegistrationFragment.this, "Account creation failed", Toast.LENGTH_SHORT).show();
                }
            }
        };

        // Define an error listener to handle any account creation errors
        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Log the error message and show error message to user
                Log.e("UserRegistration", "Account creation error: " + error.getMessage());
                Toast.makeText(UserRegistrationFragment.this, "Account creation error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        };

        // Call the createAccount method of UserAccountService with the data and listeners
        userAccountService.createAccount(accountData, responseListener, errorListener);
    }*/

    private void navigateToNameRegistration(String accountId) {
        Intent intent = new Intent(this, NameRegistrationFragment.class);
        intent.putExtra("account_id", accountId);
        startActivity(intent);
    }

    private void insertAccountData() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String confirmPassword = editTextConfirmPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if passwords match
        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        // Check if the email already exists in the database
        accountDbRef.orderByChild("email").equalTo(email).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Email already exists, show an error message
                    Toast.makeText(UserRegistrationFragment.this, "Email already exists in the database", Toast.LENGTH_SHORT).show();
                } else {
                    // Email does not exist, insert the data
                    Accounts accounts = new Accounts(email, password);

                    // Push the data to the database and attach a completion listener
                    accountDbRef.push().setValue(accounts, new DatabaseReference.CompletionListener() {
                        @Override
                        public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                            if (databaseError != null) {
                                // Error occurred while inserting data
                                Toast.makeText(UserRegistrationFragment.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                            } else {
                                // Data inserted successfully, get the key assigned to the newly created account
                                String accountKey = databaseReference.getKey();
                                navigateToNameRegistration(accountKey);
                                Toast.makeText(UserRegistrationFragment.this, "Account data inserted! ID: " + accountKey, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle database error
                Toast.makeText(UserRegistrationFragment.this, "Database error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
