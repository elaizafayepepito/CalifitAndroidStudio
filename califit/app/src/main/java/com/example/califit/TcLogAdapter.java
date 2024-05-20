package com.example.califit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TcLogAdapter extends RecyclerView.Adapter<TcLogAdapter.TcLogViewHolder> {

    Context context;
    ArrayList<Crunches> crunchList;

    public TcLogAdapter(Context context, ArrayList<Crunches> crunchList) {
        this.context = context;
        this.crunchList = crunchList;
    }

    @NonNull
    @Override
    public TcLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_tc_log_card, parent, false);
        return new TcLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TcLogViewHolder holder, int position) {
        Crunches crunches = crunchList.get(position);
        holder.tc_repetitions.setText(crunches.getReps() + " repetitions");
        holder.tc_time.setText("Time Started: " + crunches.getTimeStarted() + "\nTime Ended: " + crunches.getTimeEnded());
        holder.tc_angle.setText(String.valueOf(crunches.getAverageAngleDepth()));
        holder.tc_date.setText(crunches.getDate());
        holder.tc_level.setText(crunches.getLevel() + " LEVEL");
    }

    @Override
    public int getItemCount() {
        return crunchList.size();
    }

    public static class TcLogViewHolder extends RecyclerView.ViewHolder {
        TextView tc_repetitions, tc_time, tc_angle, tc_date, tc_level;

        public TcLogViewHolder(@NonNull View itemView) {
            super(itemView);
            tc_repetitions = itemView.findViewById(R.id.tc_repetitions);
            tc_time = itemView.findViewById(R.id.tc_time);
            tc_angle = itemView.findViewById(R.id.tc_angle);
            tc_date = itemView.findViewById(R.id.tc_date);
            tc_level = itemView.findViewById(R.id.tc_level);
        }
    }
}


