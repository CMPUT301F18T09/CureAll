/**
 * Class name: PatientListOfProblemsPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.model.ModelLoader;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientProblemListPageAdapter;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.lang3.ObjectUtils;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * For this activity, user(patient) will view a list of problems
 */
public class PatientListOfProblemsPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button searchButton, problemAddingButton;
    private RecyclerView recyclerView;
    private PatientProblemListPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    View.OnClickListener buttomListener;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList<Problem> problems;
    private ProblemController problemController = new ProblemController();
    private ArrayList<Record> records;
    private RecordController recordController= new RecordController();
    String username;
    String user_email;
    String phone;
    String id;
    String pw;
    Patient patient;
    final int REQUEST_PROBLEM_ADDING = 1;


    //drawer..
    private DrawerLayout drawer;

    /**
     * get basic info for patient, and set reference for buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list_of_problems_page);
        searchButton = (Button) findViewById(R.id.searchButton);
        problemAddingButton = (Button) findViewById(R.id.problemAddingButton);
        //ArrayList<Problem> problems = new ArrayList<>();
        /**GET DATA FROM DIFFERENT ACTIVITIES
         * evaluate first left then right!!!
         */
        Intent intent = getIntent();
        if (intent.getStringExtra("ComeFromLogin") != null && intent.getStringExtra("ComeFromLogin").equals("ComeFromLogin")){
            getDataFromLogin();

        }
        else if (intent.getStringExtra("ComeFromAddingPage") != null && intent.getStringExtra("ComeFromAddingPage").equals("ComeFromAddingPage")){
            getDataFromProblemAdding();
            loadPatientObjectFromLocal();
        }else if(intent.getStringExtra("ComeFromProblemDetail") != null && intent.getStringExtra("ComeFromProblemDetail").equals("ComeFromProblemDetail")){
            getDataFromProblemDetail();
            //will pass patient and problems back
        }

        /**ends
         *
         */
        username = patient.getUsername();
        user_email = patient.getEmail();
        phone = patient.getPhone();
        id = patient.getPatientID();
        pw = patient.getPassword();

    }

    /**
     * set listener for search button
     * user can active 3 search methods: by body location, by key words, by geolocation
     * set listener for adapter
     */
    @Override
    protected void onStart() {
        super.onStart();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder custom = new AlertDialog.Builder(PatientListOfProblemsPageActivity.this);
                custom.setCancelable(true);
                custom.setPositiveButton("Search by Body Location", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                custom.setNegativeButton("Search by Key words", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                custom.setNeutralButton("Search by Geo-location", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                custom.show();
            }
        });
       // problems = problemController.GetProblemNum(username);


        recyclerView = findViewById(R.id.listOfProblems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PatientProblemListPageAdapter(problems);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        //Log.i("111","111");

        mAdapter.setOnItemClickListener(new PatientProblemListPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            @Override
            public void onDetailClick(int position){

                Intent intent = new Intent(PatientListOfProblemsPageActivity.this,PatientProblemDetailPageActivity.class);
                Problem problem = problems.get(position);
                records = recordController.GetRecordNum(username,problem.getId());
                passDataToProblemDetail(problem,records,problems,patient);
                intent.putExtra("ComeFromPatientMainPage", "ComeFromPatientMainPage");
                intent.putExtra("ProblemPosition",position);
                startActivity(intent);
            }
            @Override
            public void onDeleteClick(int position) {
                //TODO add local storage funct.
                ProblemController.DelteProblem(problems,position,username);
                mAdapter.notifyDataSetChanged();
            }
        });


        //drawers &toggles & toolbar...
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //get navigation contents and set them by yourselfs..
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        //test content
        TextView name = header.findViewById(R.id.nav_name);
        TextView email = header.findViewById(R.id.nav_email);
        TextView phoneNumber = header.findViewById(R.id.nav_phone);
        ImageView headScript = header.findViewById(R.id.nav_headScript);
        name.setText("ID: "+username);
        email.setText("Email:"+user_email);
        phoneNumber.setText("Phone: "+ phone);

        //for end
        problemAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientListOfProblemsPageActivity.this,PatientProblemAddingPageActivity.class);
                /**should be change to save func
                 *
                 */
                intent.putExtra("ComeFromPatientMainPage", "ComeFromPatientMainPage");
                passDataToProblemAdding(username,problems);
                savePatientObjectToLocal(patient);
                /**ends
                 *
                 */
                startActivity(intent);
                //startActivityForResult(intent,REQUEST_PROBLEM_ADDING);
                //mAdapter.notifyDataSetChanged();
            }
        });
    }
    //allow you click on navigation menus
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sync:
                Toast.makeText(this,"sync info with internet",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mapOfRecords:
                Toast.makeText(this,"Here is a map of all records",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    //if user press back option
    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    /**data passing, loading and saving part
     *
     */
    public void getDataFromLogin(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("LoginData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("patientObject",null);
        String json2 = sharedPreferences2.getString("patientProblems",null);
        Type type = new TypeToken<Patient>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Problem>>(){}.getType();
        patient = gson.fromJson(json,type);
        problems = gson.fromJson(json2,type2);

    }

    public void getDataFromProblemAdding(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("problemAddingData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("username",null);
        String json2 = sharedPreferences2.getString("patientProblems",null);
        Type type = new TypeToken<String>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Problem>>(){}.getType();
        username = gson.fromJson(json,type);
        //may don't need it
        problems = gson.fromJson(json2,type2);
        //mAdapter.notifyDataSetChanged();
    }
    public void getDataFromProblemDetail(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("patientObject",null);
        String json2 = sharedPreferences2.getString("patientProblems",null);
        Type type = new TypeToken<Patient>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Problem>>(){}.getType();
        patient = gson.fromJson(json,type);
        problems = gson.fromJson(json2,type2);
    }
    public void passDataToProblemAdding(String username, ArrayList<Problem> problems){
        SharedPreferences sharedPreferences2 = getSharedPreferences("PatientMainPageData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(username);/**save in gson format*/
        String json2 = gson.toJson(problems);
        editor2.putString("username",json);
        editor2.putString("patientProblems",json2);
        editor2.apply();
    }
    public void passDataToProblemDetail(Problem problem, ArrayList<Record> records, ArrayList<Problem> problems, Patient patient){
        SharedPreferences sharedPreferences2 = getSharedPreferences("PatientMainPageData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        String json2 = gson.toJson(records);
        String json3 = gson.toJson(problems);
        String json4 = gson.toJson(patient);
        editor2.putString("problem",json);
        editor2.putString("records",json2);
        editor2.putString("problems",json3);
        editor2.putString("patient",json4);
        editor2.apply();
    }
    /**
     * future save function can start from here
     */
    public void savePatientObjectToLocal(Patient patient){
        SharedPreferences sharedPreferences2 = getSharedPreferences("saveToLocal",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(patient);
        editor2.putString("patient",json);
        editor2.apply();
    }
    public void loadPatientObjectFromLocal(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("saveToLocal",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("patient",null);
        Type type = new TypeToken<Patient>(){}.getType();
        patient = gson.fromJson(json,type);
    }


}
