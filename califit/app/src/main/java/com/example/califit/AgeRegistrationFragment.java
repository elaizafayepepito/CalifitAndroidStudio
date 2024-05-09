package com.example.califit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class AgeRegistrationFragment extends Fragment {

    private EditText editTextAge;
    private Button buttonNextAge;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_age_registration, container, false);

        editTextAge = view.findViewById(R.id.editTextAge);
        buttonNextAge = view.findViewById(R.id.buttonNextAge);

        buttonNextAge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToGenderRegistration();
            }
        });

        return view;
    }

    private void navigateToGenderRegistration() {
        String ageInputString = editTextAge.getText().toString();
        Integer ageInput = null;
        try {
            ageInput = Integer.parseInt(ageInputString);
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Please enter a valid age.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (ageInput != null && ageInput > 0) {
            GenderRegistrationFragment genderRegistrationFragment = new GenderRegistrationFragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, genderRegistrationFragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        } else {
            Toast.makeText(getContext(), "Please enter a valid age.", Toast.LENGTH_SHORT).show();
        }
    }
}
