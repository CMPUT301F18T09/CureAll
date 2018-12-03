/**
 * Class name: MainActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientBodyLocationPhotoAddingPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientSignUpActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderSignUpActivity;
import com.example.cmput301f18t09.cureall.R;
/**
 * This is the main activity when user open this app
 * It provider users choices to login in or sign up as patients or care provider
 */

public class MainActivity extends AppCompatActivity {
    private Button imagePatientlogin, imageProviderlogin, imagePatientSignUp,imageProviderSignUp;
    private TextView textPatient,textProvider;
    private ImageView loveSymbol;
    /**
     * initialize this activity page
     * with some corresponding elements showing on the view
     */
    View.OnClickListener buttonListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initalizeAllElements();
        setContentView(R.layout.activity_main);
    }
    /**
     * set up the buttons with listeners
     * such that different buttons can understand user's operations
     * the users can click different buttons inorder to login or sign up
     */
    @Override
    protected void onStart(){
        super.onStart();
        buttonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()) {
                    case R.id.patientSignUp:/**this button is used to clean the preview*/
                        Toast.makeText(MainActivity.this, "Patient Sign Up", Toast.LENGTH_SHORT).show();
                        openPatientSignUpActivity();
                        break;
                    case R.id.ProviderSignUp:
                        Toast.makeText(MainActivity.this, "Provider Sign Up", Toast.LENGTH_SHORT).show();
                        openProviderSignUpActivity();
                        break;
                    case R.id.patientLogin:
                        Toast.makeText(MainActivity.this, "Patient Login", Toast.LENGTH_SHORT).show();
                        openUserLoginActivity("Patient");
                        break;
                    case R.id.ProviderLogin:
                        Toast.makeText(MainActivity.this, "Provider login", Toast.LENGTH_SHORT).show();
                        openUserLoginActivity("Provider");
                        break;
                }
            }
        };
        setButtonOnclick();


    }


    /**
     * register the elements from xml into the activity
     * make sure they can be present in app
     */
    public void initalizeAllElements(){
        loveSymbol = (ImageView) findViewById(R.id.loveSymbol);
        textPatient = (TextView) findViewById(R.id.textViewPatient);
        textProvider = (TextView) findViewById(R.id.textViewProvider);
    }
    /**
     * register each button with its listener such that
     * they can get the order from user's operation
     */
    public void setButtonOnclick(){
        imagePatientlogin = (Button) findViewById(R.id.patientLogin);
        imageProviderlogin = (Button) findViewById(R.id.ProviderLogin);

        imagePatientSignUp = (Button) findViewById(R.id.patientSignUp);
        imageProviderSignUp = (Button) findViewById(R.id.ProviderSignUp);
        imagePatientlogin.setOnClickListener(buttonListener);
        imageProviderlogin.setOnClickListener(buttonListener);
        imagePatientSignUp.setOnClickListener(buttonListener);
        imageProviderSignUp.setOnClickListener(buttonListener);
    }
    /**
     * open the patient sign up page
     */
    public void openPatientSignUpActivity(){
        Intent intent = new Intent(this, PatientSignUpActivity.class);
        startActivity(intent);
    }
    /**
     * open the provider sign up page
     */
    public void openProviderSignUpActivity(){
        Intent intent = new Intent(this, ProviderSignUpActivity.class);
        startActivity(intent);
    }
    /**
     * open the usr login page
     * @param comer A String identify who click the login button
     */
    public void openUserLoginActivity(String comer){
        Intent intent = new Intent(this, UserLoginActivity.class);
        String theComer = comer;
        if (theComer.equals("Patient")){
            intent.putExtra("Role", theComer);
        }
        else{
            intent.putExtra("Role", theComer);
        }
        startActivity(intent);
    }
}
