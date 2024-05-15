package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class NameRegistrationFragment extends AppCompatActivity {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private Button buttonNext;
    private String accountId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_name_registration);

        Intent intent = getIntent();
        accountId = intent.getStringExtra("account_id");
        Log.d("NameRegistration", "Account ID received: " + intent.getStringExtra("account_id"));

        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextLastName = findViewById(R.id.editTextLastName);

        Button buttonNext = findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAgeRegistration();
            }
        });
    }

    private void navigateToAgeRegistration() {
        Intent intent = new Intent(this, AgeRegistrationFragment.class);
        intent.putExtra("account_id", accountId);
        intent.putExtra("firstname", editTextFirstName.getText().toString().trim());
        intent.putExtra("lastname", editTextLastName.getText().toString().trim());
        startActivity(intent);
    }
}
