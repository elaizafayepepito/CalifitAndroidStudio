package com.example.califit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class NameRegistrationFragment extends Fragment {

    private EditText editTextFirstName;
    private EditText editTextLastName;
    private Button buttonNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_name_registration, container, false);

        editTextFirstName = view.findViewById(R.id.editTextFirstName);
        editTextLastName = view.findViewById(R.id.editTextLastName);
        buttonNext = view.findViewById(R.id.buttonNext);

        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = editTextFirstName.getText().toString().trim();
                String lastName = editTextLastName.getText().toString().trim();
                if (firstName.isEmpty() || lastName.isEmpty()) {
                    // Show error message if needed
                } else {
                    navigateToAgeRegistration();
                }
            }
        });

        return view;
    }

    private void navigateToAgeRegistration() {
        AgeRegistrationFragment ageRegistrationFragment = new AgeRegistrationFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, ageRegistrationFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
