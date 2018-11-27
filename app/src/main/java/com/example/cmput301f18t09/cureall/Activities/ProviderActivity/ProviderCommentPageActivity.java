/**
 * Class name: ProviderCommentPageActivity
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
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * This activity is used for provider to give comment to each patient's problem
 */
public class ProviderCommentPageActivity extends AppCompatActivity {
    private ImageButton backButton, saveButton;
    private EditText providerComments;
    private Problem problem;
    private Patient patient;
    @Override
    /**
     * create the basic buttons, edittexts.. and data
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_comment_page);
        backButton = (ImageButton) findViewById(R.id.backButton);
        saveButton = (ImageButton) findViewById(R.id.saveButton);
        providerComments = (EditText) findViewById(R.id.providerComments);
        loadDataFromLocal("ProviderProblemDetailData");
    }

    /**
     * The save button is used to save provider comments to a particular patient's problem
     * These comments can be checked from patient's point of view.
     * uses elastic search to store the data to the server.
     */
    @Override
    protected void onStart() {

        super.onStart();

        saveButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Log.i("Patient",patient.getEmail());
                problem.setDoctorcomment(providerComments.getText().toString());
                ElasticSearchController.EditProblemTask editProblemTask = new ElasticSearchController.EditProblemTask();
                ElasticSearchParams params = new ElasticSearchParams("",problem,problem.getId());
                editProblemTask.execute(params);
                Intent intent = new Intent(ProviderCommentPageActivity.this,ProviderProblemDetailPageActivity.class);

                saveDataToLocal(patient,problem);
                intent.putExtra("ComeFromCommentAddingPage","ComeFromCommentAddingPage");
                startActivity(intent);
            }
        });
    }
    public void loadDataFromLocal(String name){
        SharedPreferences sharedPreferences2 = getSharedPreferences(name,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("patient",null);
        String json2 = sharedPreferences2.getString("problem",null);
        Type type = new TypeToken<Patient>(){}.getType();
        Type type2 = new TypeToken<Problem>(){}.getType();
        patient = gson.fromJson(json,type);
        problem = gson.fromJson(json2,type2);
    }
    public void saveDataToLocal(Patient patient, Problem problem){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProviderProblemDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(patient);
        String json2 = gson.toJson(problem);
        editor2.putString("patient",json);
        editor2.putString("problem",json2);
        editor2.apply();
    }


}
