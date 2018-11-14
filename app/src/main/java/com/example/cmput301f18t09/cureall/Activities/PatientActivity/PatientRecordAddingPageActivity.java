package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.R;

public class PatientRecordAddingPageActivity extends AppCompatActivity {
    private ImageButton backButton, saveButton, fromAlbumButton,
            cameraButton,geoLocationSelectButton,timeSelectButton,bodyLocationSelectButton;
    private ImageView writeSymbol;
    private TextView maxLength30, maxLength300;
    private EditText titleInput, descriptionInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add_record_page);
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);
        fromAlbumButton = findViewById(R.id.fromAlbumButton);
        cameraButton = findViewById(R.id.cameraButton);
        geoLocationSelectButton = findViewById(R.id.geoLocationSelectButton);
        timeSelectButton = findViewById(R.id.timeSelectButton);
        bodyLocationSelectButton = findViewById(R.id.bodyLocationSelectButton);
        writeSymbol = findViewById(R.id.writeSymbol);
        maxLength30 = findViewById(R.id.maxLength30);
        maxLength300 = findViewById(R.id.maxLength300);
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);



    }

}
