package com.example.cmput301f18t09.cureall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class CareProviderMainpage extends AppCompatActivity {
    EditText p1;
    EditText p2;
    EditText p3;
    Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_care_provider_mainpage);

        Intent incomingIntent = getIntent();
        //final ArrayList<Patient> username = incomingIntent.getStringExtra("username");
        final String username = incomingIntent.getStringExtra("username");

        p1 = (EditText) findViewById(R.id.add_patient1);
        p2 = (EditText) findViewById(R.id.add_patient2);
        p3 = (EditText) findViewById(R.id.add_patient3);

        add = (Button) findViewById(R.id.add_patient);



        add.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               String P1 = p1.getText().toString();


               ArrayList<Patient> patients = new ArrayList<>();
               ElasticSearchController.GetPatientTask getPatientTask = new ElasticSearchController.GetPatientTask();
               getPatientTask.execute(P1);
               try {
                   List<Patient> foundPatient= getPatientTask.get();
                   patients.addAll(foundPatient);
                   Patient new_patient = new Patient(patients.get(0).getUsername(),patients.get(0).getPassword(),patients.get(0).getEmail(),patients.get(0).getPhone());
                   new_patient.setDoctorID(username);

                   ElasticSearchController.AddPatientTask addPatientTask = new ElasticSearchController.AddPatientTask();
                   addPatientTask.execute(new_patient);



               } catch (Exception e) {
                   Log.i("Error", "Failed to get the user from the async object");
               }
            }



        });
    }


}
