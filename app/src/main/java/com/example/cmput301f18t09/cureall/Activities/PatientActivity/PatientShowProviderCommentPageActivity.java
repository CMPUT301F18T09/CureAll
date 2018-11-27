/**
 * Class name: PatientShowProviderCommentPageActivity
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * For this activity, user(patient) can view comment provided by care provider
 */
public class PatientShowProviderCommentPageActivity extends AppCompatActivity {
    private ImageButton backButton;
    private TextView titleTextView, problemDescription,problemProviderComment,
            titleContent,descriptionContent,commentContent;
    private Problem problem;
    private Patient patient;
    private ArrayList<Problem> problems;
    ArrayList<Record> records = new ArrayList<>();

    /**
     * get basic info for problems & records
     * also, set backButton listener
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_show_provider_comment_page);
        backButton=findViewById(R.id.backButton);
        titleTextView=findViewById(R.id.title);
        problemDescription=findViewById(R.id.problemDescription);
        problemProviderComment=findViewById(R.id.problemProviderComment);
        titleContent=findViewById(R.id.titleContent);
        descriptionContent=findViewById(R.id.descriptionContent);
        commentContent=findViewById(R.id.commentContent);
        problem = (Problem)getIntent().getSerializableExtra("problem");

        getDataFromProblemDetailPage();

        commentContent.setText(problem.getDoctorcomment());
        descriptionContent.setText((problem.getDescription()));
        titleContent.setText(problem.getTitle());

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientShowProviderCommentPageActivity.this,PatientProblemDetailPageActivity.class);

                passDataToProblemDetailPage(problem);
                intent.putExtra("ComeFromCommentViewPage","ComeFromCommentViewPage");
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void getDataFromProblemDetailPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("PatientMainPageData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("problem",null);
        Type type = new TypeToken<Problem>(){}.getType();
        problem = gson.fromJson(json,type);
    }
    public void passDataToProblemDetailPage(Problem problem){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        editor2.putString("problem",json);
        editor2.apply();
    }

}
