package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AgeRegistrationFragment extends AppCompatActivity {

    private EditText editTextAge;
    private Button buttonNextAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_age_registration);

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
        startActivity(intent);
    }
}
