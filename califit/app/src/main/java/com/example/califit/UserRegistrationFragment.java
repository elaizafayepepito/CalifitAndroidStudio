package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class UserRegistrationFragment extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_user_registration);

        Button buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNameRegistration();
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
        Intent intent = new Intent(this, ExerciseSelectionActivity.class); // Assuming you have a GuestActivity
        startActivity(intent);
    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class); // Correctly referring to LoginActivity
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    private void navigateToNameRegistration() {
        Intent intent = new Intent(this, NameRegistrationFragment.class);
        startActivity(intent);
    }
}
