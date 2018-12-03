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

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchController;
import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.model.Problem;
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

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        Intent intent = new Intent(ProviderCommentPageActivity.this,ProviderProblemDetailPageActivity.class);
                        //because the problem changed, which causes patient obj also changed, save changed obj into local
                        saveDataToLocal(patient,problem);
                        // tell problem detail page, this is from add comment page with changed problem
                        intent.putExtra("ComeFromCommentAddingPage","ComeFromCommentAddingPage");
                        setResult(Activity.RESULT_OK,intent);
                        finish();
                    }
                }, 2000);
            }
        });
    }

    /**
     *
     * @param name file name
     */
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

    /**
     *
     * @param patient problem's owner
     * @param problem the problem to be saved
     */
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
