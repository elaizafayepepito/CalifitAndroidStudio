package com.example.califit;
import java.io.Serializable;

public class Exercise implements Serializable {
    private String userId;
    private int reps;
    private String date;
    private String timeStarted;
    private String timeEnded;
    private double averageAngleDepth;
    private String level;

    public Exercise() {}

    public Exercise(String userId, int reps, String date, String timeStarted, String timeEnded, double averageAngleDepth, String level) {
        this.userId = userId;
        this.reps = reps;
        this.date = date;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.averageAngleDepth = averageAngleDepth;
        this.level = level;
    }

    public String getUserId() {
        return userId;
    }

    public int getReps() {
        return reps;
    }

    public String getDate() {
        return date;
    }

    public String getTimeStarted() {
        return timeStarted;
    }

    public String getTimeEnded() {
        return timeEnded;
    }

    public double getAverageAngleDepth() {
        return averageAngleDepth;
    }

    public String getLevel() {
        return level;
    }
}
