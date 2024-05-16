package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class AgeRegistrationFragment extends AppCompatActivity {

    private EditText editTextAge;
    private Button buttonNextAge;
    private String accountId;
    private String firstname;
    private String lastname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_age_registration);

        Intent intent = getIntent();
        accountId = intent.getStringExtra("account_id");
        firstname = intent.getStringExtra("firstname");
        lastname = intent.getStringExtra("lastname");
        Log.d("AgeRegistration", "Account ID received: " + accountId);
        Log.d("AgeRegistration", "Firstname received: " + firstname);
        Log.d("AgeRegistration", "Lastname received: " + lastname);

        editTextAge = findViewById(R.id.editTextAge);

        Button buttonNextAge = findViewById(R.id.buttonNextAge);

        buttonNextAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGenderRegistration();
            }
        });
    }

    private void navigateToGenderRegistration() {
        Intent intent = new Intent(this, GenderRegistrationFragment.class);
        intent.putExtra("account_id", accountId);
        intent.putExtra("firstname", firstname);
        intent.putExtra("lastname", lastname);
        intent.putExtra("age", editTextAge.getText().toString().trim());
        startActivity(intent);
    }
}
