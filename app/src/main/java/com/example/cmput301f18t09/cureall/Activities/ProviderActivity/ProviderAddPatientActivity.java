/**
 * Class name: ProviderAddPatientActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.Manifest;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseArray;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.R;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * This is an activity is used for provider to add a patient.
 * Provider will input a patient's username in order to add into a patient list
 */
public class ProviderAddPatientActivity extends AppCompatActivity {
    Button save;
    String doctorname;
    EditText patientname;
    private TextView textView;
    ArrayList<Patient> patients = new ArrayList<>();
    SurfaceView cameraView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case RequestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    try {
                        cameraSource.start(cameraView.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    /**
     * create the basic interface for user to input patient name
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_add_patient);
        Intent incomingIntent = getIntent();
        doctorname = incomingIntent.getStringExtra("username");
        save = findViewById(R.id.add_button);
        patientname = findViewById(R.id.input_patient);
        //new stuff...
        textView = findViewById(R.id.username);
        cameraView = findViewById(R.id.cameraView);
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(this).setBarcodeFormats(Barcode.QR_CODE)
                .build();
        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 640)
                .build();
        cameraView.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    //Request permission
                    ActivityCompat.requestPermissions(ProviderAddPatientActivity.this,
                            new String[]{Manifest.permission.CAMERA},RequestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraView.getHolder());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

            }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0){
                    textView.post(new Runnable() {
                        @Override
                        public void run() {
                            //create vibrate
                            Vibrator vibrator = (Vibrator) getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            vibrator.vibrate(1000);
                            patientname.setText(qrcodes.valueAt(0).displayValue);
                        }
                    });
                }
            }
        });
    }

    /**
     * when provider click the save button, it will try to search if the input user name match with database
     * If yes, the get the patient information and add it into patient list
     * Then with a delay for pass data into data base
     * It will bring the provider back to the former page
     */
    protected void onStart() {
        super.onStart();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patient = patientname.getText().toString();
                ElasticSearchController.GetPatientTask getPatientTask = new ElasticSearchController.GetPatientTask();
                getPatientTask.execute(patient);
                try {
                    List<Patient> foundPatient= getPatientTask.get();
                    patients.addAll(foundPatient);
                    Patient new_patient = new Patient(patients.get(0).getUsername(),patients.get(0).getEmail(),patients.get(0).getPhone());
                    new_patient.setDoctorID(doctorname);
                    ElasticSearchController.AddPatientTask addPatientTask = new ElasticSearchController.AddPatientTask();
                    addPatientTask.execute(new_patient);
                } catch (Exception e) {
                    Log.i("Error", "Failed to get the user from the async object");
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        Intent intent = new Intent(ProviderAddPatientActivity.this, ProviderMainPageActivity.class);
                        intent.putExtra("username", doctorname);
                        startActivity(intent);
                    }
                },1000);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}
