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

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchController;
import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.model.Sync;
import com.example.cmput301f18t09.cureall.model.UserState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    private Calendar cal;
    private int year ;
    private int month ;
    private int day ;
    private int hour ;
    private int min ;
    private Date date;

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
    }

    /**
     * behaviour of activity starts
     */
    @Override
    protected void onStart() {
        super.onStart();
        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String username = Username.getText().toString();                                                       //get the input of year/month/day/hour/minute/
                String prob_title = titleInput.getText().toString();
                String prob_desp = descriptionInput.getText().toString();
                // the statement below replaced by a customer selected date
                /*String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());*/
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(date);
                //Problem problem = new Problem(username,prob_title,prob_desp,currentDateTimeString,null);
                //TODO local save and sync function
                problems = saveProblem(username,prob_title,prob_desp,currentDateTimeString);
                PatientController.SaveLocalTracker(PatientProblemAddingPageActivity.this,username);

                UserState currentState = new UserState(PatientProblemAddingPageActivity.this);
                if (currentState.getState()){
                    Sync sync = new Sync(PatientProblemAddingPageActivity.this,username);
                    sync.UpdateTracker(username);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // Actions to do after 10 seconds
                            Intent intent = new Intent(PatientProblemAddingPageActivity.this, PatientListOfProblemsPageActivity.class);
                            passDataToMainPage(problems,username);
                            intent.putExtra("ComeFromAddingPage","ComeFromAddingPage");
                            startActivity(intent);
                        }
                    }, 2000);

                }
                else{
                   // problems = new ArrayList<>();
                    problems = ProblemController.loadFromFile(PatientProblemAddingPageActivity.this,"problems.txt",problems,username);
                    Intent intent = new Intent(PatientProblemAddingPageActivity.this, PatientListOfProblemsPageActivity.class);
                    passDataToMainPage(problems,username);
                    intent.putExtra("ComeFromAddingPage","ComeFromAddingPage");
                    startActivity(intent);
                }
            }
        });
        timeSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        cal = Calendar.getInstance();
                        //Date date = emotions.get(finalPosition).getDate();


                        cal.setTime(date);

                        year = cal.get(Calendar.YEAR);
                        month =cal.get(Calendar.MONTH);
                        day = cal.get(Calendar.DAY_OF_MONTH);
                        hour = cal.get(Calendar.HOUR_OF_DAY);
                        min =cal.get(Calendar.MINUTE);
                        // emotion used to remember which item are being edited
                        //final Mood emotion = emotions.get(finalPosition);

                        //using DatePicker + TimePicker to avoid user input some not correct values

                        new TimePickerDialog(PatientProblemAddingPageActivity.this,
                                AlertDialog.THEME_HOLO_LIGHT, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                //cal.setTime(emotions.get(finalPosition).getDate());
                                cal.setTime(date);
                                year = cal.get(Calendar.YEAR);
                                month =cal.get(Calendar.MONTH);
                                day = cal.get(Calendar.DAY_OF_MONTH);
                                cal.set(year,month,day,i,i1);
                                date = cal.getTime();

                                //setTime(finalPosition,year,month,day,i,i1,cal,emotions, adapter);
                                //FileEditor.saveInFile(HistoryOperation.this,FeelsBook.FILENAME,emotions);
                            }
                        }   ,hour
                                ,min
                                ,true).show();

                        new DatePickerDialog(PatientProblemAddingPageActivity.this,
                                AlertDialog.THEME_HOLO_LIGHT, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                                cal.set(i,i1,i2);
                                date = cal.getTime();
                                //setDate(finalPosition,i,i1,i2,cal,emotions,adapter);
                                //FileEditor.saveInFile(HistoryOperation.this,FeelsBook.FILENAME,emotions);

                                //finalPosition = emotions.indexOf(emotion);
                            }
                        }   ,year
                                ,month
                                ,day).show();

            }
        });
    }

    /**
     * behaviour of activity stops
     */
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
        maxLength30 = findViewById(R.id.maxLength30);
        maxLength300 = findViewById(R.id.maxLength300);
        writeSymbol = findViewById(R.id.writeSymbol);
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);
        timeSelectButton = findViewById(R.id.timeSelectButton);
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        date = new Date();
    }

    /**
     * get data from PatientMainPage
     */
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

    /**
     *
     * @param problems arrayList of problems
     * @param username username
     */
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
    //TODO
    public ArrayList<Problem> saveProblem(String username, String prob_title,String prob_desp,String date){
        Problem p = new Problem(username,prob_title,prob_desp,date,null);
        Problem problem = new Problem(username, prob_title, prob_desp, date, null);
        UserState currentState = new UserState(PatientProblemAddingPageActivity.this);
        if (currentState.getState()) {
            ArrayList<Problem> problems = GetProblemNum(username);
            problem.setState("Online");
            ElasticSearchParams param = new ElasticSearchParams(problems.size(), problem, username);

            ElasticSearchController.AddProblemTask addproblemTask = new ElasticSearchController.AddProblemTask();
            addproblemTask.execute(problem);
            try{
                p = addproblemTask.get();
            }catch(Exception e){
                Log.i("Problem","Something wrong happend at saveProblem function in PatientProblemAddingPage");
            }
            Log.i("ID",p.getId());
            Sync sync = new Sync(PatientProblemAddingPageActivity.this,username);
            sync.UpdateTracker(username);

        }
        else{
            String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
            p.setId(p.getTime()+currentDateTimeString);
            p.setState("Offline");


        }
        //TODO renew local file.(up to date)
        ArrayList<Problem> problems = new ArrayList<>();
        problems = ProblemController.loadFromFile(PatientProblemAddingPageActivity.this,"problems.txt",problems,username);
        Log.i("ID",p.getId());
        problems.add(p);

        PatientController.SaveLocalTracker(PatientProblemAddingPageActivity.this,username);
        ProblemController.saveInFile(PatientProblemAddingPageActivity.this,"problems.txt",problems,username);

        return problems;
    }

//TODO
}
