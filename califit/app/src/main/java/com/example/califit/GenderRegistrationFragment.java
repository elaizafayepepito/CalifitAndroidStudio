package com.example.califit;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import androidx.fragment.app.Fragment;

public class GenderRegistrationFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_gender_registration, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button buttonFinish = view.findViewById(R.id.buttonFinish);
        ImageView imageViewGender = view.findViewById(R.id.imageViewGender);

        // Set up your UI event listeners here
        buttonFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToExerciseSelectionActivity();
            }
        });

        // Optionally, set an image resource to ImageView
       // imageViewGender.setImageResource(R.drawable.your_image_resource); // Make sure this resource exists in your drawable folder
    }

    private void navigateToExerciseSelectionActivity() {
        Intent intent = new Intent(getActivity(), ExerciseSelectionActivity.class);
        startActivity(intent);
    }
}
