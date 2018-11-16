package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.R;


public class PatientProblemAddingPageActivity extends AppCompatActivity {
    private TextView maxLength30, maxLength300;
    private ImageView writeSymbol;
    private ImageButton backButton, saveButton, timeSelectButton;
    private EditText titleInput, descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem_adding_page);
        initializedAllElements();

    }

    public void initializedAllElements(){
        maxLength30 = (TextView) findViewById(R.id.maxLength30);
        maxLength300 = findViewById(R.id.maxLength300);
        writeSymbol = findViewById(R.id.writeSymbol);
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);
        timeSelectButton = findViewById(R.id.timeSelectButton);
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);

    }
}
