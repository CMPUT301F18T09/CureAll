package com.example.cmput301f18t09.cureall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newPatient = (Button) findViewById(R.id.newpatient);
        Button newDoctor = (Button) findViewById(R.id.newdoctor);
        Button Patient = (Button) findViewById(R.id.patient);
        Button Doctor = (Button) findViewById(R.id.doctor);
        Button Search = (Button) findViewById(R.id.Search);
        Button Record = (Button) findViewById(R.id.Record);

        newPatient.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewAccount.class);
                intent.putExtra("Role", "Patient");
                startActivity(intent);
            }
        });
        newDoctor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewAccount.class);
                intent.putExtra("Role", "Doctor");
                startActivity(intent);
            }
        });
        Patient.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                ArrayList<Patient> examplePatientList = new ArrayList<>();
                //ElasticSearchController.GetPatientListTask getPatientListTask = new ElasticSearchController.GetPatientListTask();
                ElasticSearchController.GetPatientTask getPatientListTask = new ElasticSearchController.GetPatientTask();
                getPatientListTask.execute("connor");
                try {
                    List<Patient> foundPatient= getPatientListTask.get();
                    examplePatientList.addAll(foundPatient);


                } catch (Exception e) {
                    Log.i("Error", "Failed to get the user from the async object");
                }
                /*Intent intent = new Intent(MainActivity.this,Login.class);
                intent.putExtra("Role", "Patient");
                startActivity(intent);*/
            }
        });
        Doctor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Login.class);
                intent.putExtra("Role", "Doctor");
                startActivity(intent);
                /*ArrayList<Patient> examplePatientList = new ArrayList<>();
                //ElasticSearchController.GetPatientListTask getPatientListTask = new ElasticSearchController.GetPatientListTask();
                ElasticSearchController.GetPatientTask getPatientListTask = new ElasticSearchController.GetPatientTask();
                getPatientListTask.execute("connor");
                try {
                    List<Patient> foundPatient= getPatientListTask.get();
                    examplePatientList.addAll(foundPatient);


                } catch (Exception e) {
                    Log.i("Error", "Failed to get the user from the async object");
                }*/
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchPage.class);
                intent.putExtra("Role", "Doctor");
                startActivity(intent);
            }
        });
        Record.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RecordPage.class);
                intent.putExtra("Role", "Doctor");
                startActivity(intent);
            }
        });
    }
}
