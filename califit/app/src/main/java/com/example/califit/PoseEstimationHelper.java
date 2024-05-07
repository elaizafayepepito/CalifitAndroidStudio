package com.example.califit;

import com.google.mlkit.vision.pose.PoseLandmark;

public class PoseEstimationHelper {
    public float calculateAngle(PoseLandmark a, PoseLandmark b, PoseLandmark c) {
        float[] vecAB = new float[]{a.getPosition().x - b.getPosition().x, a.getPosition().y - b.getPosition().y};
        float[] vecBC = new float[]{c.getPosition().x - b.getPosition().x, c.getPosition().y - b.getPosition().y};

        double dotProduct = vecAB[0] * vecBC[0] + vecAB[1] * vecBC[1];
        double magnitudeAB = Math.sqrt(vecAB[0] * vecAB[0] + vecAB[1] * vecAB[1]);
        double magnitudeBC = Math.sqrt(vecBC[0] * vecBC[0] + vecBC[1] * vecBC[1]);

        double cosTheta = dotProduct / (magnitudeAB * magnitudeBC);

        float angle =  (float) Math.toDegrees(Math.acos(cosTheta));

        if (angle > 180.0) {
            angle = 360 - angle;
        }

        return angle;
    }
}
