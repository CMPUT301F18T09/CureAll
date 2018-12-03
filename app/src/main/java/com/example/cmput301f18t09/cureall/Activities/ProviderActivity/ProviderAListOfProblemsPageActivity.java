/**
 * Class name: ProviderAListOfProblemsPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProviderAdapter.ProblemListPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * This activity is used to present the problems of a patient
 * It is very similar to the patient versions, but it do not have delete or add functions
 * And provider can only view detail of each problem by click them
 */
public class ProviderAListOfProblemsPageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProblemListPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton backButton;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Patient patient;
    private ArrayList<Problem> exampleProblemList;
    private TextView Name ,Patient_problem;
    @Override
    /**
     * create everything necessary for this activity,such as adapter, recycleview, textviews and some necessary data
     * Also uses elastic search to get the information from server..
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_alist_of_problems);
        backButton = (ImageButton) findViewById(R.id.backButton);
        Name = (TextView) findViewById(R.id.patientName);
        Patient_problem = findViewById(R.id.Patient_problem);
        getDataFromProviderMainPage();
        exampleProblemList = new ArrayList<Problem>();
        ElasticSearchController.GetProblemTask getproblemTask = new ElasticSearchController.GetProblemTask();
        getproblemTask.execute(patient.getUsername());

        try {
            List<Problem> foundPatient= getproblemTask.get();
            exampleProblemList.addAll(foundPatient);


        } catch (Exception e) {
            Log.i("Error", "Failed to get the user from the async object");
        }

        Log.i("Read","read end"+Integer.toString(exampleProblemList.size()));

        Name.setText("name : "+patient.getUsername());
        recyclerView = findViewById(R.id.listOfProblems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProblemListPageAdapter(exampleProblemList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Once provider click the items inside the recycleview, it will bring the provider to a page
     * that present the details for each problem
     */
    @Override
    protected void onStart() {

        super.onStart();
        mAdapter.setOnItemClickListener(new ProblemListPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Problem problem = exampleProblemList.get(position);
                Log.i("Patient",patient.getEmail());

                Intent intent = new Intent(ProviderAListOfProblemsPageActivity.this,ProviderProblemDetailPageActivity.class);
                passDataToProblemDetailPage(patient,problem);
                intent.putExtra("ComeFromAListOfProblemPage","ComeFromAListOfProblemPage");
                startActivity(intent);

            }

            /**
             * This fucntion has not been used yet!
             * @param position
             */
            @Override
            public void onDetailClick(int position){
                //dont implement
            }
        });
    }
    public void getDataFromProviderMainPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProviderMainPageData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("patient",null);
        Type type = new TypeToken<Patient>(){}.getType();
        patient = gson.fromJson(json,type);
    }
    public void passDataToProblemDetailPage(Patient patient, Problem problem){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemListPageData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(patient);
        String json2 = gson.toJson(problem);
        editor2.putString("patient",json);
        editor2.putString("problem",json2);
        editor2.apply();
    }


}
