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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientListOfProblemsPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderMainPageActivity;
import com.example.cmput301f18t09.cureall.model.CareProvider;
import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchController;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.model.Sync;
import com.example.cmput301f18t09.cureall.model.UserState;
import com.google.android.gms.vision.CameraSource;
import com.google.gson.Gson;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;
import java.util.Date;
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
    private ArrayList<Problem> problems;
    private ProblemController problemController = new ProblemController();
    private String Role;
    SurfaceView cameraView;
    CameraSource cameraSource;
    final int RequestCameraPermissionID = 1001;
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
        Role = incomingIntent.getStringExtra("Role");
        final Activity activity = this;
        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                login(Role);
            }
        });
        loveSymbol.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //todo
                if (Role.equals("Patient")){
                    IntentIntegrator intentIntegrator = new IntentIntegrator(activity);
                    intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
                    intentIntegrator.setPrompt("scan");
                    intentIntegrator.setCameraId(0);
                    intentIntegrator.setBeepEnabled(false);
                    intentIntegrator.setBarcodeImageEnabled(false);
                    intentIntegrator.initiateScan();
                }
                else{
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        IntentResult result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data);
        if (result!= null){
            if (result.getContents() == null){
                Toast.makeText(this,"You canceled scanning",Toast.LENGTH_SHORT).show();
            }else {
                Toast.makeText(this,result.getContents(),Toast.LENGTH_LONG).show();
                userNameInput.setText(result.getContents());
                login(Role);
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data);
        }


    }

    /**
     * behaviour of activity starts
     */
    @Override
    protected void onStart() {
        super.onStart();


    }

    /**
     * behaviour of activity stops
     */
    @Override
    protected void onStop() {
        super.onStop();
    }


    /**
     *
     * @param patient corresponding patient
     * @param problems patient's problems (in arrayList)
     */
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
                //PatientController.SaveLocalTracker(UserLoginActivity.this,Username);

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


                //TODO if user login (online) then do an sync automatically
                Sync sync = new Sync(UserLoginActivity.this,Username);
                if (!sync.Check()){//device doesnt have anyfolder belongs to the user
                    problems = problemController.GetProblemNum(patients.get(0).getUsername());
                    sync.SyncUSer(patients.get(0));
                    sync.SyncAllProblem(problems,Username);
                    sync.SyncAllRecord(Username);
                }

                else{
                    //problems = problemController.GetProblemNum(patients.get(0).getUsername());
                    Date local = new Date();
                    Date stream= new Date();
                    sync.SyncUSer(patients.get(0));

                    local= PatientController.GetLocalTracker(UserLoginActivity.this,Username,local);

                    stream = PatientController.GetOnlineTracker(Username,stream);

                    if (local.compareTo(stream)<0) {
                        Log.i("Tracker","es比较新");
                        problems = problemController.GetProblemNum(patients.get(0).getUsername());

                        sync.SyncAllProblem(problems,Username);
                        sync.SyncAllRecord(Username);

                    }
                    else{
                        Log.i("Tracker","本地比较新");
                        problems = ProblemController.loadFromFile(UserLoginActivity.this, "problems.txt", problems, Username);

                    }
                    //sync.SyncUSer(patients.get(0));
                    //sync.SyncAllProblem(problems,Username);
                    //sync.SyncAllRecord(Username);
                }
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
            Intent intent = new Intent(UserLoginActivity.this,ProviderMainPageActivity.class);
                /**should be change to save func
                 **/
                intent.putExtra("username", doctors.get(0).getUsername());
                /**ends
                 *
                 */
                startActivity(intent);

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
