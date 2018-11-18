package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.MainActivity;
import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.R;

public class PatientSignUpActivity extends AppCompatActivity{
    private Button backButton, continueButton;
    private EditText username, password, rePassword, email, phone ;
    private ImageView patientSymbol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_sign_up);
        initalizeAllElements();
        Sign();

    }
    public void initalizeAllElements(){
        backButton = (Button) findViewById(R.id.backButton);
        continueButton = (Button) findViewById(R.id.continueButton);
        username = (EditText) findViewById(R.id.userNameInput);
        password = (EditText) findViewById(R.id.passwordInputpass);
        rePassword = (EditText) findViewById(R.id.confirmPasswodInput);
        email = (EditText) findViewById(R.id.emailAddressInput);
        phone = (EditText) findViewById(R.id.phoneNumberInput);
        patientSymbol = (ImageView) findViewById(R.id.patientSymbol);


    }

    public void Sign(){
        /*final String Username = username.getText().toString();                                                       //get the input of year/month/day/hour/minute/
        final String Emial = emial.getText().toString();
        final String Phone = phone.getText().toString();
        final String Password = password.getText().toString();
        final String RePassword = rePassword.getText().toString();*/



        continueButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String Username = username.getText().toString();                                                       //get the input of year/month/day/hour/minute/
                String Emial = email.getText().toString();
                String Phone = phone.getText().toString();
                String Password = password.getText().toString();
                String RePassword = rePassword.getText().toString();
                if (Password.equals(RePassword)){

                        Log.i("Patient","choose sign up as a patient");
                        Patient user = new Patient(Username,Password,Emial,Phone);
                        //NormalTweet newtweent = new NormalTweet("Well, Will.");
                        setResult(RESULT_OK);

                        //saveInFile(); // TODO replace this with elastic search
                        ElasticSearchController.AddPatientTask addUserTask = new ElasticSearchController.AddPatientTask();
                        addUserTask.execute(user);

                }

                Intent intent = new Intent(PatientSignUpActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
