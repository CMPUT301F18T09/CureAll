/**
 * Class name: UserLoginActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientListOfProblemsPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderMainPageActivity;
import com.example.cmput301f18t09.cureall.CareProvider;
import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.UserState;
import com.google.gson.Gson;

import java.security.Provider;
import java.util.ArrayList;
import java.util.List;
/**
 * This activity handles the case when user login
 * It provides the space for user to input their username and passward.
 * Only when the password and usernames matches the information in database.
 * It will bring the user to their main page.
 */
public class UserLoginActivity extends AppCompatActivity {
    private ImageView loveSymbol;
    private EditText userNameInput, passwordInput;
    private Button loginButton, backButton;
    private ArrayList<Problem> problems = new ArrayList<>();
    private ProblemController problemController = new ProblemController();
    @Override
    /**
     * initialize the buttons and edittext
     * for user to click or input
     * @param savedInstanceState
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);
        initalizeAllElements();
        Intent incomingIntent = getIntent();
        final String Role = incomingIntent.getStringExtra("Role");
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                login(Role);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    @Override
    protected void onStop() {
        super.onStop();
    }

    public void passDataToPatient(Patient patient , ArrayList<Problem> problems){
        SharedPreferences sharedPreferences2 = getSharedPreferences("LoginData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(patient);/**save in gson format*/
        String json2 = gson.toJson(problems);
        editor2.putString("patientObject",json);
        editor2.putString("patientProblems",json2);
        editor2.apply();
    }
    /**
     * The onCreate function will call login function
     * This function will identify who is login in, the patient or provider
     * For each different group of people, different operations will be handled
     * This function will match each user login information with database
     * If match success, it will bring the user to its main page.
     * @param Role
     */
    public void login(String Role){
        ArrayList<Patient> patients = new ArrayList<Patient>();
        ArrayList <CareProvider> doctors = new ArrayList<CareProvider>();
        if (Role.equals("Patient")){
            setResult(RESULT_OK);

            String Username = userNameInput.getText().toString();
            //String Password = passwordInput.getText().toString();

            UserState currentState = new UserState(this);
            if (currentState.getState()){
                ElasticSearchController.GetPatientTask getuserTask = new ElasticSearchController.GetPatientTask();
                getuserTask.execute(Username);

                try {
                    List<Patient> foundPatient= getuserTask.get();
                    patients.addAll(foundPatient);
                } catch (Exception e) {
                    //TODO after a successful login online, this current patient data will store in local
                    //TODO next time, when offline login with same account, it could read data from local
                    Log.i("Chen", "Failed to get the user from the async object");
                }
                Log.i("Read","read end");
                problems = problemController.GetProblemNum(patients.get(0).getUsername());
                //TODO implement local retrieve funct.
                Intent intent = new Intent(UserLoginActivity.this,PatientListOfProblemsPageActivity.class);
                /**should be change to save func
                 * HAS BEEN CHANGED
                 * NEED TO CONSIDER OFFLINE DATA GET, in this case, problems and patients.get(0) is null!!!!!
                 * ADD try and catch
                 */
                intent.putExtra("ComeFromLogin", "ComeFromLogin");
                passDataToPatient(patients.get(0),problems);
                /**ends
                 *
                 */
                startActivity(intent);


            }
            else {//offline behaviour
                Log.i("State","Login as patient(offline)");
                ArrayList<Patient> patient = new ArrayList<>();
                patient = PatientController.loadFromFile(UserLoginActivity.this,"userinfo.txt",patient,Username);
                if (patient.size()!=0) {
                    problems =ProblemController.loadFromFile(UserLoginActivity.this, "problems.txt", problems, Username);
                    Intent intent = new Intent(UserLoginActivity.this, PatientListOfProblemsPageActivity.class);
                    intent.putExtra("ComeFromLogin", "ComeFromLogin");
                    passDataToPatient(patient.get(0), problems);
                    startActivity(intent);
                }
            }





        }
        else{
            setResult(RESULT_OK);
            String Username = userNameInput.getText().toString();
            String Password = passwordInput.getText().toString();

            ElasticSearchController.GetDoctorTask getuserTask = new ElasticSearchController.GetDoctorTask();
            getuserTask.execute(Username);

            try {
                List<CareProvider> foundDoctor= getuserTask.get();
                Log.i("CareProvider",Integer.toString(foundDoctor.size()));
                doctors.addAll(foundDoctor);
                Log.i("CareProvider",Integer.toString(doctors.size()));

            } catch (Exception e) {
                Log.i("Error", "Failed to get the user from the async object");
            }
            Log.i("CareProviderout",Integer.toString(doctors.size()));
            String pass =  doctors.get(0).getPassword();
            if (pass.equals(Password)){
                Intent intent = new Intent(UserLoginActivity.this,ProviderMainPageActivity.class);
                /**should be change to save func
                 *
                 */
                intent.putExtra("username", doctors.get(0).getUsername());
                /**ends
                 *
                 */
                startActivity(intent);
            }
        }
    }
    /**
     * the onCreate function will call this function help initialize
     * the elements in its relative xml file
     */
    public void initalizeAllElements(){
        loveSymbol = (ImageView) findViewById(R.id.loveSymbol);
        userNameInput = (EditText) findViewById(R.id.userNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInputpass);
        loginButton = (Button) findViewById(R.id.loginButton);
        backButton = (Button) findViewById(R.id.backButton);
    }


}
