package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;
import java.util.List;

public class ProviderAddPatientActivity extends AppCompatActivity {
    Button save;
    String doctorname;
    EditText patientname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_add_patient);
        Intent incomingIntent = getIntent();
        //final ArrayList<Patient> username = incomingIntent.getStringExtra("username");
        doctorname = incomingIntent.getStringExtra("username");
        save = (Button)findViewById(R.id.add_button);
        patientname = (EditText)findViewById(R.id.input_patient);

    }
    protected void onStart() {

        super.onStart();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String patient = patientname.getText().toString();
                ArrayList<Patient> patients = new ArrayList<>();
                ElasticSearchController.GetPatientTask getPatientTask = new ElasticSearchController.GetPatientTask();
                getPatientTask.execute(patient);
                try {
                    List<Patient> foundPatient= getPatientTask.get();
                    patients.addAll(foundPatient);
                    Patient new_patient = new Patient(patients.get(0).getUsername(),patients.get(0).getPassword(),patients.get(0).getEmail(),patients.get(0).getPhone());
                    new_patient.setDoctorID(doctorname);

                    ElasticSearchController.AddPatientTask addPatientTask = new ElasticSearchController.AddPatientTask();
                    addPatientTask.execute(new_patient);



                } catch (Exception e) {
                    Log.i("Error", "Failed to get the user from the async object");
                }

                Intent intent = new Intent(ProviderAddPatientActivity.this,ProviderMainPageActivity.class);

                intent.putExtra("doctorname", doctorname);
                intent.putExtra("patientname",patients.get(0).getUsername());
                intent.putExtra("patientEmail",patients.get(0).getEmail());
                intent.putExtra("patientPassword",patients.get(0).getPassword());
                intent.putExtra("patientPhone",patients.get(0).getPhone());

                setResult(0,intent);
                ProviderAddPatientActivity.this.finish();

            }
        });
    }
}
