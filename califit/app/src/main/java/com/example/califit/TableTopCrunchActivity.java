package com.example.califit;

import static com.example.califit.SumoSquatActivity.computeAverage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.Camera;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.ExperimentalGetImage;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.google.mlkit.vision.pose.defaults.PoseDetectorOptions;

import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutionException;

public class TableTopCrunchActivity extends AppCompatActivity {
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private TextView counterTextView;
    private Button saveButton;
    private DatabaseReference crunchDbRef;
    private String timeStarted;
    ArrayList<Float> crunchAngleList = new ArrayList<>();

    int PERMISSION_REQUESTS = 1;

    PreviewView previewView;

    // Base pose detector with streaming frames, when depending on the pose-detection sdk
    PoseDetectorOptions options =
            new PoseDetectorOptions.Builder()
                    .setDetectorMode(PoseDetectorOptions.STREAM_MODE)
                    .build();

    PoseDetector poseDetector = PoseDetection.getClient(options);

    Canvas canvas;

    Paint cPaint = new Paint();
    Paint lPaint = new Paint();

    Display display;

    Bitmap bitmap4Save;

    ArrayList<Bitmap> bitmapArrayList = new ArrayList<>();
    ArrayList<Bitmap> bitmap4DisplayArrayList = new ArrayList<>();

    ArrayList<Pose> poseArrayList = new ArrayList<>();

    int counter = 0;
    String stage = "";
    boolean isRunning = false;

    MediaPlayer mediaPlayer;

    @ExperimentalGetImage
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crunch);

        crunchDbRef = FirebaseDatabase.getInstance("https://califitdb-default-rtdb.asia-southeast1.firebasedatabase.app/").getReference().child("Crunches");

        Intent intent = getIntent();
        timeStarted = intent.getStringExtra("time_started");

        cameraProviderFuture = ProcessCameraProvider.getInstance(this);

        previewView = findViewById(R.id.crunchPreviewView);

        display = findViewById(R.id.crunchDisplayOverlay);

        saveButton = findViewById(R.id.crunchSaveButton);

        cPaint.setColor(Color.RED);
        cPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        cPaint.setStrokeWidth(5);

        lPaint.setColor(Color.YELLOW);
        lPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        lPaint.setStrokeWidth(4);

        mediaPlayer = MediaPlayer.create(this, R.raw.beep_sound);

        counterTextView = findViewById(R.id.crunchCounterTextView);

        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
                bindPreview(cameraProvider);
            } catch (ExecutionException | InterruptedException e) {
                // No errors need to be handled for this Future.
                // This should never be reached.
            }
        }, ContextCompat.getMainExecutor(this));

        if (!allPermissionsGranted()) {
            getRuntimePermissions();
        }

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCrunchData();
            }
        });
    }

    Runnable RunMlkit = new Runnable() {
        @Override
        public void run() {
            poseDetector.process(InputImage.fromBitmap(bitmapArrayList.get(0),0)).addOnSuccessListener(new OnSuccessListener<Pose>() {
                @Override
                public void onSuccess(Pose pose) {
                    poseArrayList.add(pose);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });
        }
    };

    @ExperimentalGetImage
    void bindPreview(@NonNull ProcessCameraProvider cameraProvider) {
        Preview preview = new Preview.Builder()
                .build();

        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_FRONT)
                .build();

        preview.setSurfaceProvider(previewView.getSurfaceProvider());

        ImageAnalysis imageAnalysis =
                new ImageAnalysis.Builder()
//                         enable the following line if RGBA output is needed.
                        .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
//                        .setTargetResolution(new Size(1280, 720))
                        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                        .build();

        imageAnalysis.setAnalyzer(ActivityCompat.getMainExecutor(this), new ImageAnalysis.Analyzer() {
            @Override
            public void analyze(@NonNull ImageProxy imageProxy) {
                int rotationDegrees = imageProxy.getImageInfo().getRotationDegrees();
                // insert your code here.
                // after done, release the ImageProxy object
                ByteBuffer byteBuffer = imageProxy.getImage().getPlanes()[0].getBuffer();
                byteBuffer.rewind();
                Bitmap bitmap = Bitmap.createBitmap(imageProxy.getWidth(), imageProxy.getHeight(), Bitmap.Config.ARGB_8888);
                bitmap.copyPixelsFromBuffer(byteBuffer);

                Matrix matrix = new Matrix();
                matrix.postRotate(270);
                matrix.postScale(-1,1);
                Bitmap rotatedBitmap = Bitmap.createBitmap(bitmap,0,0,imageProxy.getWidth(), imageProxy.getHeight(),matrix,false);

                bitmapArrayList.add(rotatedBitmap);

                if (poseArrayList.size() >= 1) {
                    // Post UI-related code to the main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            canvas = new Canvas(bitmapArrayList.get(0));

                            List<PoseLandmark> allPoseLandmarks = poseArrayList.get(0).getAllPoseLandmarks();

                            // Define connections between adjacent landmarks
                            int[][] connections = {
                                    {PoseLandmark.LEFT_SHOULDER, PoseLandmark.RIGHT_SHOULDER},
                                    {PoseLandmark.LEFT_SHOULDER, PoseLandmark.LEFT_ELBOW},
                                    {PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_ELBOW},
                                    {PoseLandmark.LEFT_ELBOW, PoseLandmark.LEFT_WRIST},
                                    {PoseLandmark.RIGHT_ELBOW, PoseLandmark.RIGHT_WRIST},
                                    {PoseLandmark.LEFT_HIP, PoseLandmark.RIGHT_HIP},
                                    {PoseLandmark.LEFT_HIP, PoseLandmark.LEFT_KNEE},
                                    {PoseLandmark.RIGHT_HIP, PoseLandmark.RIGHT_KNEE},
                                    {PoseLandmark.LEFT_KNEE, PoseLandmark.LEFT_ANKLE},
                                    {PoseLandmark.RIGHT_KNEE, PoseLandmark.RIGHT_ANKLE},
                                    {PoseLandmark.LEFT_SHOULDER, PoseLandmark.LEFT_HIP},
                                    {PoseLandmark.RIGHT_SHOULDER, PoseLandmark.RIGHT_HIP},
                                    {PoseLandmark.LEFT_PINKY, PoseLandmark.LEFT_INDEX},
                                    {PoseLandmark.LEFT_WRIST, PoseLandmark.LEFT_INDEX},
                                    {PoseLandmark.LEFT_WRIST, PoseLandmark.LEFT_PINKY},
                                    {PoseLandmark.LEFT_WRIST, PoseLandmark.LEFT_THUMB},
                                    {PoseLandmark.RIGHT_PINKY, PoseLandmark.RIGHT_INDEX},
                                    {PoseLandmark.RIGHT_WRIST, PoseLandmark.RIGHT_INDEX},
                                    {PoseLandmark.RIGHT_WRIST, PoseLandmark.RIGHT_PINKY},
                                    {PoseLandmark.RIGHT_WRIST, PoseLandmark.RIGHT_THUMB},
                                    {PoseLandmark.LEFT_HEEL, PoseLandmark.LEFT_FOOT_INDEX},
                                    {PoseLandmark.LEFT_ANKLE, PoseLandmark.LEFT_FOOT_INDEX},
                                    {PoseLandmark.LEFT_ANKLE, PoseLandmark.LEFT_HEEL},
                                    {PoseLandmark.RIGHT_HEEL, PoseLandmark.RIGHT_FOOT_INDEX},
                                    {PoseLandmark.RIGHT_ANKLE, PoseLandmark.RIGHT_FOOT_INDEX},
                                    {PoseLandmark.RIGHT_ANKLE, PoseLandmark.RIGHT_HEEL},
                                    {PoseLandmark.LEFT_MOUTH, PoseLandmark.RIGHT_MOUTH},
                                    {PoseLandmark.LEFT_EAR, PoseLandmark.LEFT_EYE_OUTER},
                                    {PoseLandmark.LEFT_EYE_OUTER, PoseLandmark.LEFT_EYE},
                                    {PoseLandmark.LEFT_EYE, PoseLandmark.LEFT_EYE_INNER},
                                    {PoseLandmark.LEFT_EYE_INNER, PoseLandmark.NOSE},
                                    {PoseLandmark.RIGHT_EAR, PoseLandmark.RIGHT_EYE_OUTER},
                                    {PoseLandmark.RIGHT_EYE_OUTER, PoseLandmark.RIGHT_EYE},
                                    {PoseLandmark.RIGHT_EYE, PoseLandmark.RIGHT_EYE_INNER},
                                    {PoseLandmark.RIGHT_EYE_INNER, PoseLandmark.NOSE},
                            };

                            // Draw lines connecting adjacent landmarks
                            for (int[] connection : connections) {
                                PoseLandmark startLandmark = allPoseLandmarks.get(connection[0]);
                                PoseLandmark endLandmark = allPoseLandmarks.get(connection[1]);
                                canvas.drawLine(
                                        startLandmark.getPosition().x,
                                        startLandmark.getPosition().y,
                                        endLandmark.getPosition().x,
                                        endLandmark.getPosition().y,
                                        lPaint
                                );
                            }

                            // Draw circles for each landmark
                            for (PoseLandmark poseLandmark : allPoseLandmarks) {
                                canvas.drawCircle(poseLandmark.getPosition().x, poseLandmark.getPosition().y, 2, cPaint);
                            }

                            Pose pose = poseArrayList.get(0);

                            //Tabletop Crunch
                            // Get landmarks for left leg
                            PoseLandmark leftShoulder = pose.getPoseLandmark(PoseLandmark.LEFT_SHOULDER);
                            PoseLandmark leftHip = pose.getPoseLandmark(PoseLandmark.LEFT_HIP);
                            PoseLandmark leftKnee = pose.getPoseLandmark(PoseLandmark.LEFT_KNEE);

                            // Get landmarks for right leg
                            PoseLandmark rightShoulder = pose.getPoseLandmark(PoseLandmark.RIGHT_SHOULDER);
                            PoseLandmark rightHip = pose.getPoseLandmark(PoseLandmark.RIGHT_HIP);
                            PoseLandmark rightKnee = pose.getPoseLandmark(PoseLandmark.RIGHT_KNEE);

                            // Calculate angles
                            float leftAngle = calculateAngle(leftShoulder, leftHip, leftKnee);
                            float rightAngle = calculateAngle(rightShoulder, rightHip, rightKnee);
                            Log.d("LeftLimb:", "Left Limb:" + leftAngle);
                            Log.d("RightLimb:", "Right Limb:" + rightAngle);

                            // Determine stage of tabletop crunch movement
                            if (leftAngle > 90 || rightAngle > 90) {
                                stage = "down";
                                Log.d("Stage:", "DOWN " + stage);
                            }
                            if (stage.equals("down") && leftAngle <= 90 && rightAngle <= 90) {
                                float angleAverage = (leftAngle + rightAngle) / 2;
                                stage = "up";
                                Log.d("Stage:", "UP " + stage);
                                counter++;
                                crunchAngleList.add(angleAverage);
                                mediaPlayer.start();
                            }

                            Log.d("RepCounter", "Tabletop Crunch detected. Count: " + counter);


                            counterTextView.setText(String.valueOf(counter));

                            bitmap4DisplayArrayList.clear();
                            bitmap4DisplayArrayList.add(bitmapArrayList.get(0));
                            bitmap4Save = bitmapArrayList.get(bitmapArrayList.size()-1);
                            bitmapArrayList.clear();
                            bitmapArrayList.add(bitmap4Save);
                            poseArrayList.clear();
                            isRunning = false;
                        }
                    });
                }

                if (poseArrayList.size() == 0 && bitmapArrayList.size() >= 1 && !isRunning) {
                    RunMlkit.run();
                    isRunning = true;
                }

                if (bitmap4DisplayArrayList.size() >= 1) {
                    display.getBitmap(bitmap4DisplayArrayList.get(0));
                }

                imageProxy.close();
            }
        });

        Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, imageAnalysis, preview);
    }


    private String[] getRequiredPermissions() {
        try {
            PackageInfo info =
                    this.getPackageManager()
                            .getPackageInfo(this.getPackageName(), PackageManager.GET_PERMISSIONS);
            String[] ps = info.requestedPermissions;
            if (ps != null && ps.length > 0) {
                return ps;
            } else {
                return new String[0];
            }
        } catch (Exception e) {
            return new String[0];
        }
    }

    private boolean allPermissionsGranted() {
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isPermissionGranted(Context context, String permission) {
        if (ContextCompat.checkSelfPermission(context, permission)
                == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    private void getRuntimePermissions() {
        List<String> allNeededPermissions = new ArrayList<>();
        for (String permission : getRequiredPermissions()) {
            if (!isPermissionGranted(this, permission)) {
                allNeededPermissions.add(permission);
            }
        }

        if (!allNeededPermissions.isEmpty()) {
            ActivityCompat.requestPermissions(
                    this, allNeededPermissions.toArray(new String[0]), PERMISSION_REQUESTS);
        }
    }

    // Utility method to calculate the angle between three points
    private float calculateAngle(PoseLandmark a, PoseLandmark b, PoseLandmark c) {
        //float[] vecAB = new float[]{b.getPosition().x - a.getPosition().x, b.getPosition().y - a.getPosition().y};
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

    private String classifyCrunchLevel(double average) {
        if (average < 60) {
            return "EXPERT";
        } else if (average >= 60 && average <= 75){
            return "INTERMEDIATE";
        } else {
            return "BEGINNER";
        }
    }

    private void saveCrunchData() {
        Log.d("CRUNCH ANGLE LIST", "Values" + crunchAngleList);
        String userId = getUserIdFromPreferences();
        int reps = counter;
        String date = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());
        String timeStarted = this.timeStarted;
        String timeEnded = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
        double averageAngleDepth = Double.parseDouble(String.format("%.2f", computeAverage(crunchAngleList)));
        String level = classifyCrunchLevel(averageAngleDepth);
        Log.d("SQUAT LEVEL", "Value: " + level);

        // Create a Squats object with the data
        Crunches crunch = new Crunches(userId, reps, date, timeStarted, timeEnded, averageAngleDepth, level);

        // Push the data to the database and attach a completion listener
        crunchDbRef.push().setValue(crunch, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseError != null) {
                    // Error occurred while inserting data
                    Toast.makeText(TableTopCrunchActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    // Data inserted successfully, get the key assigned to the newly added pushup data
                    String pushupKey = databaseReference.getKey();
                    backToDashboard();
                    Toast.makeText(TableTopCrunchActivity.this, "Pushup data saved successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void backToDashboard() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);

        if (sharedPreferences.contains("user_id")) {
            navigateToDashboard(getUserIdFromPreferences());
        } else {
            navigateToGuestDashboard();
        }
    }

    public String getUserIdFromPreferences() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_details", MODE_PRIVATE);
        return sharedPreferences.getString("user_id", null);
    }

    private void navigateToGuestDashboard () {
        Intent intent = new Intent(this, GuestDashboardActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToDashboard(String userId) {
        Intent intent = new Intent(this, DashboardActivity.class);
        intent.putExtra("user_id", userId);
        startActivity(intent);
        finish();
    }
}
