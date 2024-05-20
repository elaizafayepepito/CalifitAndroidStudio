package com.example.califit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class IpLogAdapter extends RecyclerView.Adapter<IpLogAdapter.IpLogViewHolder> {

    Context context;
    ArrayList<Pushups> pushupList;

    public IpLogAdapter(Context context, ArrayList<Pushups> pushupList) {
        this.context = context;
        this.pushupList = pushupList;
    }

    @NonNull
    @Override
    public IpLogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_ip_log_card, parent, false);
        return new IpLogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IpLogViewHolder holder, int position) {
        Pushups pushups = pushupList.get(position);
        holder.ip_repetitions.setText(pushups.getReps() + " repetitions");
        holder.ip_time.setText("Time Started: " + pushups.getTimeStarted() + "\nTime Ended: " + pushups.getTimeEnded());
        holder.ip_angle.setText(String.valueOf(pushups.getAverageAngleDepth()));
        holder.ip_date.setText(pushups.getDate());
        holder.ip_level.setText(pushups.getLevel() + " LEVEL");
    }

    @Override
    public int getItemCount() {
        return pushupList.size();
    }

    public static class IpLogViewHolder extends RecyclerView.ViewHolder {
        TextView ip_repetitions, ip_time, ip_angle, ip_date, ip_level;

        public IpLogViewHolder(@NonNull View itemView) {
            super(itemView);
            ip_repetitions = itemView.findViewById(R.id.ip_repetitions);
            ip_time = itemView.findViewById(R.id.ip_time);
            ip_angle = itemView.findViewById(R.id.ip_angle);
            ip_date = itemView.findViewById(R.id.ip_date);
            ip_level = itemView.findViewById(R.id.ip_level);
        }
    }
}


