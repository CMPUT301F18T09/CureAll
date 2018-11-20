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

public class MainActivity extends AppCompatActivity {
    private Button imagePatientlogin, imageProviderlogin, imagePatientSignUp,imageProviderSignUp;
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
        Button jump = (Button)findViewById(R.id.jump);
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PatientBodyLocationPhotoAddingPageActivity.class);
                Bundle bundle = new Bundle();

                intent.putExtras(bundle);

                startActivity(intent);
            }
        });
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



    public void initalizeAllElements(){
        loveSymbol = (ImageView) findViewById(R.id.loveSymbol);
        textPatient = (TextView) findViewById(R.id.textViewPatient);
        textProvider = (TextView) findViewById(R.id.textViewProvider);
    }
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
    public void openPatientSignUpActivity(){
        Intent intent = new Intent(this, PatientSignUpActivity.class);
        startActivity(intent);
    }
    public void openProviderSignUpActivity(){
        Intent intent = new Intent(this, ProviderSignUpActivity.class);
        startActivity(intent);
    }
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
