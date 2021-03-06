/**
 * Class name: ProviderMainPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.SearchActivity;
import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchController;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.ProviderAdapter.ProviderMainPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This activity is for care provider to view a list of patients
 * It uses recycle view to carry the patients in cardviews
 * Some thing still need to fix is some variable type need to be set into private or public..
 */
public class ProviderMainPageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProviderMainPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Patient> examplePatientList;
    private ImageButton addPatientButton, searchProblemRecordButton;
    private TextView providerMainPage;
    String username;

    /**
     * create the necessary elements in the view, absed on the resource xml file
     * Use the elasticsearch to pull all assigned patient from cloud service back to local
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main_page);
        addPatientButton = (ImageButton) findViewById(R.id.addPatientButton);
        searchProblemRecordButton =(ImageButton) findViewById(R.id.searchProblemRecordButton);
        providerMainPage = findViewById(R.id.providerMainPage);
        examplePatientList = new ArrayList<>();
        //test samples...
        Intent incomingIntent = getIntent();
        username = incomingIntent.getStringExtra("username");
        ElasticSearchController.GetPatientListTask getPatientListTask = new ElasticSearchController.GetPatientListTask();
        getPatientListTask.execute(username);
        try {
            List<Patient> foundPatient= getPatientListTask.get();
            examplePatientList.addAll(foundPatient);
        } catch (Exception e) {
            Log.i("Error", "Failed to get the user from the async object");
            //TODO implememnt local data retrive function here
        }
        //test ends...
        // Try to implement searching, now it is 4:50 am
        searchProblemRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
     /*           String title ="";
                boolean a = false;
                // if click this button, then get all problems
                ElasticSearchController.GetAllProblemTask getAllProblemTask = new ElasticSearchController.GetAllProblemTask();
                getAllProblemTask.execute();
                ArrayList<Problem> problems = new ArrayList<>();
                try {
                    problems = getAllProblemTask.get();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for(Problem each:problems)
                {
                     title = each.getTitle();

                     a=stringContainsItemFromList(title, new String[]{"BMW", "Test"});
                }*/
           /*     ArrayList<Record> records = new ArrayList<>();
                records = RecordSearchController.GeoSearch(37.4219,-122.0839);
                records.size();*/
                AlertDialog.Builder custom = new AlertDialog.Builder(ProviderMainPageActivity.this);
                custom.setCancelable(true);

                custom.setPositiveButton("Problem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
        ;                   Intent intent = new Intent(ProviderMainPageActivity.this,SearchActivity.class);
                            intent.putExtra("search method","problem");
                            startActivity(intent);

                    }
                });
                custom.setNegativeButton("Record", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ProviderMainPageActivity.this,SearchActivity.class);
                        intent.putExtra("search method","record");
                        startActivity(intent);


                    }
                });
                custom.show();
            }

        });

    }

    /**
     *
     * @param inputStr the input string
     * @param items items in stringList
     * @return true/false
     */
    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }

    /**
     * create the button listeners inside or outside of recycleview, that user can click them for different purpose.
     */
    @Override
    protected void onStart() {
        super.onStart();
        recyclerView = findViewById(R.id.listOfPatients);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProviderMainPageAdapter(examplePatientList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        /**
         * The search function has not been applied into this project yet!
         */
/*        searchProblemRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProviderMainPageActivity.this,"Leo you should implement this button for searching..", Toast.LENGTH_SHORT).show();

            }
        });*/
        /**
         * click this button can bring to the page for adding patient by name
         */
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProviderMainPageActivity.this,"Add patient button..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(ProviderMainPageActivity.this,ProviderAddPatientActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);

            }
        });


        /**
         * allows the user to click the item inside the recycleview, thus we have to use adapter.setOnItemListener
         * For each patient, provider can view their details
         */
        mAdapter.setOnItemClickListener(new ProviderMainPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Patient patient = examplePatientList.get(position);
                Log.i("Patient",patient.getEmail());
                customDialog(patient.getUsername(),patient.getEmail(),patient.getPhone());

            }
            @Override
            public void onDetailClick(int position){
                Intent intent = new Intent(ProviderMainPageActivity.this,ProviderAListOfProblemsPageActivity.class);
                Patient patient = examplePatientList.get(position);
                passDataToAListOfProblemPage(patient);
                intent.putExtra("ComeFromProviderMainPage","ComeFromProviderMainPage");
                startActivity(intent);
            }
        });


    }
/*
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }*/

    /**
     * This dialog is used to show patient infos, once you click the patient item in recycleview
     * @param name username
     * @param Email user's email
     * @param Phone user's phone
     */
    public void customDialog(String name, String Email, String Phone) {
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setTitle("Patient Info");
        String message = "username:\t\t:" + name + "\nuserEmail:\t\t:" + Email + "\nuserPhone\t\t:" + Phone;
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "Got",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }
        );
        builderSingle.show();
    }
    /**save
     * and
     * get
     */
    public void passDataToAListOfProblemPage(Patient patient){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProviderMainPageData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(patient);
        editor2.putString("patient",json);
        editor2.apply();
    }

}
