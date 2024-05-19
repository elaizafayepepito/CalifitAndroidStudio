package com.example.califit;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private UserAccountService userAccountService;
    private EditText editTextEmail;
    private EditText editTextPassword;
    private ImageView ivTogglePassword;
    private boolean isPasswordVisible;
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
        ivTogglePassword = findViewById(R.id.ivTogglePassword);

        setupPasswordVisibilityToggle();
    }

    private void setupPasswordVisibilityToggle() {
        ivTogglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Change icon to eye closed
                    ivTogglePassword.setImageResource(R.drawable.ph_eye_open);
                    // Set EditText to hide password
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    editTextPassword.setSelection(editTextPassword.getText().length());
                } else {
                    // Change icon to eye open
                    ivTogglePassword.setImageResource(R.drawable.ph_eye_closed);
                    // Set EditText to show password
                    editTextPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    editTextPassword.setSelection(editTextPassword.getText().length());
                }
                isPasswordVisible = !isPasswordVisible;
            }
        });
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

    /*private void navigateToDashboard(String accountId) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("account_id", accountId);
        startActivity(intent);
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
                                String accountId = accountSnapshot.getKey();
                                navigateToDashboard(accountId);
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
    }*/
    private void navigateToDashboard(String userId) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
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
                        boolean isPasswordCorrect = false;
                        for (DataSnapshot accountSnapshot : dataSnapshot.getChildren()) {
                            Accounts account = accountSnapshot.getValue(Accounts.class);
                            if (account != null && account.getPassword().equals(password)) {
                                // Correct password
                                isPasswordCorrect = true;
                                String accountId = accountSnapshot.getKey();
                                navigateToDashboardWithUserId(accountId);
                                Toast.makeText(LoginActivity.this, "Login successful!", Toast.LENGTH_SHORT).show();
                                break;
                            }
                        }
                        if (!isPasswordCorrect) {
                            Toast.makeText(LoginActivity.this, "Incorrect password. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Email not found. Please sign up.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void navigateToDashboardWithUserId(String accountId) {
        DatabaseReference userRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Users");
        userRef.orderByChild("accountId").equalTo(accountId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                    Users user = userSnapshot.getValue(Users.class);
                    if (user != null) {
                        String userKey = userSnapshot.getKey();
                        saveUserDetailsInPreferences(user, userKey);
                        navigateToDashboard(userKey);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(LoginActivity.this, "Error retrieving user details", Toast.LENGTH_SHORT).show();
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
}
