package com.example.cmput301f18t09.cureall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button newPatient = (Button) findViewById(R.id.newpatient);
        Button newDoctor = (Button) findViewById(R.id.newdoctor);
        Button Patient = (Button) findViewById(R.id.patient);
        Button Doctor = (Button) findViewById(R.id.doctor);
        Button Search = (Button) findViewById(R.id.Search);

        newPatient.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewAccount.class);
                intent.putExtra("Role", "Patient");
                startActivity(intent);
            }
        });
        newDoctor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,NewAccount.class);
                intent.putExtra("Role", "Doctor");
                startActivity(intent);
            }
        });
        Patient.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Login.class);
                intent.putExtra("Role", "Patient");
                startActivity(intent);
            }
        });
        Doctor.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Login.class);
                intent.putExtra("Role", "Doctor");
                startActivity(intent);
            }
        });
        Search.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SearchPage.class);
                intent.putExtra("Role", "Doctor");
                startActivity(intent);
            }
        });
    }
}
