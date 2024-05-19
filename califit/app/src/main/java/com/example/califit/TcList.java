package com.example.califit;

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

public class TcList extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseReference database;
    TcLogAdapter tcLogAdapter;
    ArrayList<Crunches> list;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tc_logs);

        Intent intent = getIntent();
        userId = intent.getStringExtra("user_id");

        recyclerView = findViewById(R.id.tcList);
        database = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Crunches");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        tcLogAdapter = new TcLogAdapter(this,list);
        recyclerView.setAdapter(tcLogAdapter);

        database.orderByChild("userId").equalTo(userId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot: snapshot.getChildren()) {
                    Crunches crunch = dataSnapshot.getValue(Crunches.class);
                    if (crunch != null) {
                        list.add(crunch);
                        Log.d("TCLIST", "Crunch value received: " + crunch);
                    }
                }
                tcLogAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("TCLIST", "Error fetching data", error.toException());
            }
        });

    }
}
