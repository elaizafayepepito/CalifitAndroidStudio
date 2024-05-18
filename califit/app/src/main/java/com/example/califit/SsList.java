package com.example.califit;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SsList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    SsLogAdapter ssLogAdapter;
    ArrayList<Squats> list;
    private String userId;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ss_logs);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");

        recyclerView = findViewById(R.id.ssList);
        database = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Squats");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        ssLogAdapter = new SsLogAdapter(this,list);
        recyclerView.setAdapter(ssLogAdapter);

        database.orderByChild("userId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Squats squat = dataSnapshot.getValue(Squats.class);
                    if (squat != null) {
                        list.add(squat);
                        Log.d("SSLIST", "Squat value received: " + squat);
                    }
                }
                ssLogAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("SSLIST", "Error fetching data", error.toException());
            }
        });

    }
}
