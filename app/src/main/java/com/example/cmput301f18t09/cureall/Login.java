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

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.login_username);
        password = (EditText) findViewById(R.id.login_password);
        login = (Button) findViewById(R.id.login);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent incomingIntent = getIntent();
        final String Role = incomingIntent.getStringExtra("Role");

        login.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                login(Role);
            }
        });
    }

    public void login(String Role){
        ArrayList <Patient> patients = new ArrayList<Patient>();
        ArrayList <CareProvider> doctors = new ArrayList<CareProvider>();
        if (Role.equals("Patient")){
            setResult(RESULT_OK);

            String Username = username.getText().toString();
            String Password = password.getText().toString();

            ElasticSearchController.GetPatientTask getuserTask = new ElasticSearchController.GetPatientTask();
            getuserTask.execute(Username);

            try {
                List<Patient> foundPatient= getuserTask.get();
                patients.addAll(foundPatient);


            } catch (Exception e) {
                Log.i("Error", "Failed to get the user from the async object");
            }

            Log.i("Read","read end");

            String pass =  patients.get(0).getPassword();
            if (pass.equals(Password)){
                Intent intent = new Intent(Login.this,PatientMainPage.class);
                intent.putExtra("username", patients.get(0).getUsername());
                intent.putExtra("email",patients.get(0).getEmail());
                intent.putExtra("phone",patients.get(0).getPhone());
                intent.putExtra("id",patients.get(0).getPatientID());
                intent.putExtra("password",patients.get(0).getPassword());
                //Patient user = patients.get(0);
               // intent.putExtra(,patients);
                startActivity(intent);
            }

        }
        else{

            setResult(RESULT_OK);

            String Username = username.getText().toString();
            String Password = password.getText().toString();

            ElasticSearchController.GetDoctorTask getuserTask = new ElasticSearchController.GetDoctorTask();
            getuserTask.execute(Username);

            try {
                List<CareProvider> foundDoctor= getuserTask.get();
                Log.i("CareProvider",Integer.toString(foundDoctor.size()));
                doctors.addAll(foundDoctor);
                Log.i("CareProvider",Integer.toString(doctors.size()));

            } catch (Exception e) {
                Log.i("Error", "Failed to get the user from the async object");
            }
            Log.i("CareProviderout",Integer.toString(doctors.size()));
            String pass =  doctors.get(0).getPassword();
            if (pass.equals(Password)){
                Intent intent = new Intent(Login.this,CareProviderMainpage.class);
                intent.putExtra("username", doctors.get(0).getUsername());
                startActivity(intent);
            }

        }
    }
}
