package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.R;

public class PatientEditInfoActivity  extends AppCompatActivity{
    private EditText newEmail, newPhone;
    private Button saveButton;
    private String email, phone, user;
    private Patient patient;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_edit_info);

        saveButton = (Button) findViewById(R.id.infoSave);
        newEmail = (EditText)findViewById(R.id.newEmail);
        newPhone = (EditText)findViewById(R.id.newPhone);
//        patient = (Patient)getIntent().getSerializableExtra("patient");

//        email =
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = newEmail.getText().toString();
                phone = newPhone.getText().toString();
            }
        });
    }
}
