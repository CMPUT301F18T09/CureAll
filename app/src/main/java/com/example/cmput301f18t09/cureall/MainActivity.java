package com.example.cmput301f18t09.cureall;

import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ImageButton imagePatientlogin, imageProviderlogin, imagePatientSignUp,imageProviderSignUp;
    private TextView textPatient,textProvider;
    private ImageView loveSymbol;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initalizeAllElements();
        setContentView(R.layout.activity_main);
    }

    public void initalizeAllElements(){
        loveSymbol = (ImageView) findViewById(R.id.loveSymbol);
        textPatient = (TextView) findViewById(R.id.textViewPatient);
        textProvider = (TextView) findViewById(R.id.textViewProvider);
        imagePatientlogin = (ImageButton) findViewById(R.id.patientLogin);
        imageProviderlogin = (ImageButton) findViewById(R.id.ProviderLogin);
        imagePatientSignUp = (ImageButton) findViewById(R.id.patientSignUp);
        imageProviderSignUp = (ImageButton) findViewById(R.id.ProviderSignUp);

    }
}
