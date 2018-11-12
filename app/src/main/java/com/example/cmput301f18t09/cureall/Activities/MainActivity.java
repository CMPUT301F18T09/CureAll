package com.example.cmput301f18t09.cureall.Activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.R;

public class MainActivity extends AppCompatActivity {
    private ImageButton imagePatientlogin, imageProviderlogin, imagePatientSignUp,imageProviderSignUp;
    private TextView textPatient,textProvider;
    private ImageView loveSymbol;
    View.OnClickListener buttonListener;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initalizeAllElements();
        setContentView(R.layout.activity_main);
    }
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
                        openUserLoginActivity();
                        break;
                    case R.id.ProviderLogin:
                        Toast.makeText(MainActivity.this, "Provider login", Toast.LENGTH_SHORT).show();
                        openUserLoginActivity();
                        break;
                }
            }
        };
        setButtonOnclick();


    }



    public void initalizeAllElements(){
        loveSymbol = (ImageView) findViewById(R.id.loveSymbol);
        textPatient = (TextView) findViewById(R.id.textViewPatient);
        textProvider = (TextView) findViewById(R.id.textViewProvider);
    }
    public void setButtonOnclick(){
        imagePatientlogin = (ImageButton) findViewById(R.id.patientLogin);
        imageProviderlogin = (ImageButton) findViewById(R.id.ProviderLogin);
        imagePatientSignUp = (ImageButton) findViewById(R.id.patientSignUp);
        imageProviderSignUp = (ImageButton) findViewById(R.id.ProviderSignUp);
        imagePatientlogin.setOnClickListener(buttonListener);
        imageProviderlogin.setOnClickListener(buttonListener);
        imagePatientSignUp.setOnClickListener(buttonListener);
        imageProviderSignUp.setOnClickListener(buttonListener);
    }
    public void openPatientSignUpActivity(){
        Intent intent = new Intent(this, PatientSignUpActivity.class);
        startActivity(intent);
    }
    public void openProviderSignUpActivity(){
        Intent intent = new Intent(this, ProviderSignUpActivity.class);
        startActivity(intent);
    }
    public void openUserLoginActivity(){
        Intent intent = new Intent(this, UserLoginActivity.class);
        startActivity(intent);
    }
}
