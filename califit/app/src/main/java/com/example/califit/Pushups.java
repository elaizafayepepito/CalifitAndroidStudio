package com.example.califit;

public class Pushups {
    private String userId;
    private int reps;
    private String date;
    private String timeStarted;
    private String timeEnded;
    private double averageAngleDepth;

    public Pushups() {}

    public Pushups(String userId, int reps, String date, String timeStarted, String timeEnded, double averageAngleDepth) {
        this.userId = userId;
        this.reps = reps;
        this.date = date;
        this.timeStarted = timeStarted;
        this.timeEnded = timeEnded;
        this.averageAngleDepth = averageAngleDepth;
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
}
