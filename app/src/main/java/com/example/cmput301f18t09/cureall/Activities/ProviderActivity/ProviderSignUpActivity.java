/**
 * Class name: ProviderSignUpActivity
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
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.MainActivity;
import com.example.cmput301f18t09.cureall.model.CareProvider;
import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.model.UserState;

import java.util.ArrayList;

/**
 * This is a page allows the provider to sign up.
 * It has not been finished yet, we have use other ways to create some test provider account
 * It will be finished by next project
 * account:
 * user name: wade
 * passward: wpass
 */
public class ProviderSignUpActivity extends AppCompatActivity{
    private Button backButton, continueButton;
    private EditText userNameInput, emailAddressInput, phoneNumberInput ;
    private ImageView patientSymbol;
    private ArrayList<CareProvider> careProviders;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_sign_up);
        initalizeAllElements();
        Sign();
    }

    /**
     * sign up as provider
     */
    public void Sign(){
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userNameInput.getText().toString();
                if (username.length() < 8){
                    Toast.makeText(ProviderSignUpActivity.this,"8 characters required!",Toast.LENGTH_SHORT).show();
                }
                else{
                    String Email = emailAddressInput.getText().toString();
                    String phone = phoneNumberInput.getText().toString();

                    UserState currentState = new UserState(ProviderSignUpActivity.this);

                    Log.i("Provider","choose sign up as a provider online");
                    CareProvider provider = new CareProvider(username,Email,phone);

                    setResult(RESULT_OK);
                    ElasticSearchController.AddDoctorTask addDoctorTask = new ElasticSearchController.AddDoctorTask();
                    addDoctorTask.execute(provider);
                    careProviders.add(provider);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            // Actions to do after 10 seconds
                            Intent intent = new Intent(ProviderSignUpActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                    }, 2000);
                }

            }
        });




    }

    /**
     * initialization
     */
    public void initalizeAllElements(){
        backButton =  findViewById(R.id.backButton);
        continueButton =  findViewById(R.id.continueButton);
        userNameInput = (EditText) findViewById(R.id.userNameInput);
        emailAddressInput = (EditText) findViewById(R.id.emailAddressInput);
        phoneNumberInput = (EditText) findViewById(R.id.phoneNumberInput);
        patientSymbol = (ImageView) findViewById(R.id.ProviderSymbol);
        careProviders = new ArrayList<>();
    }
}