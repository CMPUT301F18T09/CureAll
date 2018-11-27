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

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.R;

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
    ArrayList<Patient> patients = new ArrayList<>();
    @Override
    /**
     * create the basic interface for user to input patient name
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_add_patient);
        Intent incomingIntent = getIntent();
        //final ArrayList<Patient> username = incomingIntent.getStringExtra("username");
        doctorname = incomingIntent.getStringExtra("username");
        save = (Button)findViewById(R.id.add_button);
        patientname = (EditText)findViewById(R.id.input_patient);

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
                    Patient new_patient = new Patient(patients.get(0).getUsername(),patients.get(0).getPassword(),patients.get(0).getEmail(),patients.get(0).getPhone());
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
