/**
 * Class name: PatientSignUpActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.MainActivity;
import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchController;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.controller.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.model.UserState;

import java.util.ArrayList;

/**
 * For this activity, user(patient) can sign up for an account
 */
public class PatientSignUpActivity extends AppCompatActivity{
    private Button backButton, continueButton;
    private EditText username, email, phone ;
    private ImageView patientSymbol;
    private ArrayList<Patient> patients = new ArrayList<>();

    /**
     * start up
     * @param savedInstanceState (build in)
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);
        initalizeAllElements();
        Sign();
    }

    /**
     * get all info needed for creating a new patient account from EditText
     */
    public void initalizeAllElements(){
        backButton = (Button) findViewById(R.id.backButton);
        continueButton = (Button) findViewById(R.id.continueButton);
        username = (EditText) findViewById(R.id.userNameInput);
        email = (EditText) findViewById(R.id.emailAddressInput);
        phone = (EditText) findViewById(R.id.phoneNumberInput);
        patientSymbol = (ImageView) findViewById(R.id.patientSymbol);


    }

    /**
     * set continueButton listener
     * sign for a patient
     */
    public void Sign(){

        continueButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String Username = username.getText().toString();                                                       //get the input of year/month/day/hour/minute/
                if (username.length() < 8){
                    Toast.makeText(PatientSignUpActivity.this,"8 characters required!",Toast.LENGTH_SHORT).show();
                }
                else {
                    String Emial = email.getText().toString();
                    String Phone = phone.getText().toString();

                    UserState currentState = new UserState(PatientSignUpActivity.this);
                    if(currentState.getState()){
                        Log.i("Patient","choose sign up as a patient online");
                        Patient user = new Patient(Username,Emial,Phone);

                        setResult(RESULT_OK);

                        //TODO replace this with elastic search
                        ElasticSearchController.AddPatientTask addUserTask = new ElasticSearchController.AddPatientTask();
                        addUserTask.execute(user);
                        // TODO we might also need a local save here
                        patients.add(user);
                        PatientController.saveInFile(PatientSignUpActivity.this,"userinfo.txt",patients,Username);
                    }

                    Log.i("Patient","choose sign up as a patient offline");
                    Patient user = new Patient(Username,Emial,Phone);
                    patients.add(user);
                    PatientController.saveInFile(PatientSignUpActivity.this,"userinfo.txt",patients,Username);


                    Intent intent = new Intent(PatientSignUpActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });
    }}
