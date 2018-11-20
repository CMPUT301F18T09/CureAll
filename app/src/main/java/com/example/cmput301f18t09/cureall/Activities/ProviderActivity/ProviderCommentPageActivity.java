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
        Intent incomingIntent = getIntent();
        problem = (Problem) getIntent().getSerializableExtra("problem");
        patient = (Patient)getIntent().getSerializableExtra("patient");
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
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem",problem);
                bundle.putSerializable("patient",patient);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
