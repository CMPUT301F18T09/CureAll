package com.example.cmput301f18t09.cureall;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class PatientSignUpActivity extends AppCompatActivity{
    private ImageButton backButton, continueButton;
    private EditText userNameInput, passwordInput, confirmPasswodInput, emailAddressInput, phoneNumberInput ;
    private ImageView patientSymbol;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initalizeAllElements();
        setContentView(R.layout.activity_patient_sign_up);
    }
    public void initalizeAllElements(){
        backButton = (ImageButton) findViewById(R.id.backButton);
        backButton = (ImageButton) findViewById(R.id.continueButton);
        userNameInput = (EditText) findViewById(R.id.userNameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInputpass);
        confirmPasswodInput = (EditText) findViewById(R.id.confirmPasswodInput);
        emailAddressInput = (EditText) findViewById(R.id.emailAddressInput);
        phoneNumberInput = (EditText) findViewById(R.id.phoneNumberInput);
        patientSymbol = (ImageView) findViewById(R.id.patientSymbol);


    }
}
