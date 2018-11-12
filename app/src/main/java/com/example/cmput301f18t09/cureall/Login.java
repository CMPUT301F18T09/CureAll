package com.example.cmput301f18t09.cureall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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
        if (Role.equals("Patient")){
            setResult(RESULT_OK);

            String Username = username.getText().toString();
            String Password = password.getText().toString();

            ElasticSearchController.GetTweetsTask getuserTask = new ElasticSearchController.GetTweetsTask();
            getuserTask.execute(Username);

            try {
                List<Patient> foundPatient= getuserTask.get();
                patients.addAll(foundPatient);


            } catch (Exception e) {
                Log.i("Error", "Failed to get the user from the async object");
            }

            String pass =  patients.get(0).getPassword();
            Log.i("GetPass", pass);

        }
    }
}
