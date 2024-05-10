package com.example.califit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class UserRegistrationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_registration, container, false);

        Button signUpButton = view.findViewById(R.id.buttonSignUp);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToNameRegistration();
            }
        });

        return view;
    }

    private void navigateToNameRegistration() {
        NameRegistrationFragment nameRegistrationFragment = new NameRegistrationFragment();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, nameRegistrationFragment); // Assume 'fragment_container' is your FrameLayout ID in the Activity's layout
        fragmentTransaction.addToBackStack(null); // This line is optional, it allows users to navigate back to the previous Fragment by pressing the back button
        fragmentTransaction.commit();
    }
}
