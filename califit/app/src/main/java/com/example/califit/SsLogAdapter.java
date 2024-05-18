package com.example.califit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class SsLogAdapter extends RecyclerView.Adapter<SsLogAdapter.SsLogViewHolder> {

    Context context;
    ArrayList<Squats> squatList;

    public SsLogAdapter(Context context, ArrayList<Squats> squatList) {
        this.context = context;
        this.squatList = squatList;
    }

    @NonNull
    @Override
    public SsLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ss_log_card, parent, false);
        return new SsLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SsLogViewHolder holder, int position) {
        Squats squats = squatList.get(position);
        holder.ss_repetitions.setText(squats.getReps() + " repetitions");
        holder.ss_time.setText("Time Started: " + squats.getTimeStarted() + "\nTime Ended: " + squats.getTimeEnded());
        holder.ss_angle.setText(String.valueOf(squats.getAverageAngleDepth()));
        holder.ss_date.setText(squats.getDate());
    }

    @Override
    public int getItemCount() {
        return squatList.size();
    }

    public static class SsLogViewHolder extends RecyclerView.ViewHolder {
        TextView ss_repetitions, ss_time, ss_angle, ss_date;

        public SsLogViewHolder(@NonNull View itemView) {
            super(itemView);
            ss_repetitions = itemView.findViewById(R.id.ss_repetitions);
            ss_time = itemView.findViewById(R.id.ss_time);
            ss_angle = itemView.findViewById(R.id.ss_angle);
            ss_date = itemView.findViewById(R.id.ss_date);
        }
    }
}


