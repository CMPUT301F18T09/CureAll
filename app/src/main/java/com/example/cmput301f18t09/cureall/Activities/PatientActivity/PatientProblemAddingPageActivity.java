/**
 * Class name: PatientProblemAddingPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * For this activity, user(patient) can add problem into their profile
 */
public class PatientProblemAddingPageActivity extends AppCompatActivity {
    private TextView maxLength30, maxLength300;
    private ImageView writeSymbol;
    private Button backButton, saveButton, timeSelectButton;
    private EditText titleInput, descriptionInput;
    private String username;
    private ArrayList<Problem> problems = new ArrayList<Problem>();
    private ProblemController problemController = new ProblemController();

    /**
     * set listener for save button
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem_adding_page);
        initializedAllElements();
        getDataFromPatientMainPage();
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String username = Username.getText().toString();                                                       //get the input of year/month/day/hour/minute/
                String prob_title = titleInput.getText().toString();
                String prob_desp = descriptionInput.getText().toString();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                //Problem problem = new Problem(username,prob_title,prob_desp,currentDateTimeString,null);
                saveProblem(username,prob_title,prob_desp,currentDateTimeString);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        problems = problemController.GetProblemNum(username);
                        Intent intent = new Intent(PatientProblemAddingPageActivity.this, PatientListOfProblemsPageActivity.class);
                        passDataToMainPage(problems,username);
                        intent.putExtra("ComeFromAddingPage","ComeFromAddingPage");
                        startActivity(intent);
                        //setResult(RESULT_OK, intent);
                    }
                }, 1000);


            }
        });

    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    /**
     * set init value for elements used in this activity
     * (or give reference)
     * including buttons, textviews, title, description, username, problems
     */
    public void initializedAllElements(){
        maxLength30 = (TextView) findViewById(R.id.maxLength30);
        maxLength300 = findViewById(R.id.maxLength300);
        writeSymbol = findViewById(R.id.writeSymbol);
        backButton = (Button) findViewById(R.id.backButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        timeSelectButton = (Button) findViewById(R.id.timeSelectButton);
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        //Intent intent = getIntent();


    }
    public void getDataFromPatientMainPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("PatientMainPageData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("username",null);
        String json2 = sharedPreferences2.getString("patientProblems",null);
        Type type = new TypeToken<String>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Problem>>(){}.getType();
        username = gson.fromJson(json,type);
        problems = gson.fromJson(json2,type2);
    }
    public void passDataToMainPage(ArrayList<Problem> problems, String username){
        SharedPreferences sharedPreferences2 = getSharedPreferences("problemAddingData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(username);/**save in gson format*/
        String json2 = gson.toJson(problems);
        editor2.putString("username",json);
        editor2.putString("patientProblems",json2);
        editor2.apply();
    }


    /**
     * get problems
     * @param username  patient account name
     * @return          corresponding problems
     */
    public ArrayList<Problem> GetProblemNum(String username){
        ArrayList<Problem> problems = new ArrayList<Problem>();
        ElasticSearchController.GetProblemTask getproblemTask = new ElasticSearchController.GetProblemTask();
        getproblemTask.execute(username);

        try {
            List<Problem> foundPatient= getproblemTask.get();
            problems.addAll(foundPatient);


        } catch (Exception e) {
            Log.i("Error", "Failed to get the user from the async object");
        }

        Log.i("Read","read end");

        return problems;
    }

    /**
     * save new problems
     * @param username      whose new problem
     * @param prob_title    new problem's title
     * @param prob_desp     new problem's description
     * @param date          new problem's date
     */
    public void saveProblem(String username, String prob_title,String prob_desp,String date){
        ArrayList<Problem> problems = GetProblemNum(username);

        Problem problem = new Problem(username,prob_title,prob_desp,date,null);

        ElasticSearchParams param = new ElasticSearchParams(problems.size(),problem,username);

        ElasticSearchController.AddProblemTask addproblemTask = new ElasticSearchController.AddProblemTask();
        addproblemTask.execute(param);
    }

}
