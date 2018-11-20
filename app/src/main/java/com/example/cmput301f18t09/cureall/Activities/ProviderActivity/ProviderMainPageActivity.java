package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.ProviderAdapter.ProviderMainPageAdapter;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;
import java.util.List;

public class ProviderMainPageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ProviderMainPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<Patient> examplePatientList;
    private ImageButton addPatientButton, searchProblemRecordButton;
    String username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main_page);
        addPatientButton = (ImageButton) findViewById(R.id.addPatientButton);
        searchProblemRecordButton =(ImageButton) findViewById(R.id.searchProblemRecordButton);
        //test samples...
        Intent incomingIntent = getIntent();
        //final ArrayList<Patient> username = incomingIntent.getStringExtra("username");
        username = incomingIntent.getStringExtra("username");
        examplePatientList = new ArrayList<>();



        ElasticSearchController.GetPatientListTask getPatientListTask = new ElasticSearchController.GetPatientListTask();
        getPatientListTask.execute(username);
        try {
            List<Patient> foundPatient= getPatientListTask.get();
            examplePatientList.addAll(foundPatient);


        } catch (Exception e) {
            Log.i("Error", "Failed to get the user from the async object");
        }
        //test ends...


    }

    @Override
    protected void onStart() {

        recyclerView = findViewById(R.id.listOfPatients);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProviderMainPageAdapter(examplePatientList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        super.onStart();
        searchProblemRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProviderMainPageActivity.this,"Leo you should implement this button for searching..", Toast.LENGTH_SHORT).show();

            }
        });
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProviderMainPageActivity.this,"Add patient button..", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(ProviderMainPageActivity.this,ProviderAddPatientActivity.class);
                intent.putExtra("username", username);
                startActivity(intent);

            }
        });



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
                Bundle bundle = new Bundle();
                bundle.putSerializable("patient", patient);
                intent.putExtras(bundle);
//                intent.putExtra("problem",problem.getId());
                startActivity(intent);
            }
        });


    }

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
}
