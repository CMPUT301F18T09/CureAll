package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.R;

public class PatientBodyLocationPhotoAddingPageActivity extends AppCompatActivity {
    private ImageView bodySelectionSymbol;
    private ImageButton backButton, saveButton, frontPhotoButton, backPhotoButton, cameraButton;
    private TextView selectedBodyLocation, fixedText1,fixedText2,fixedText3,fixedText4,fixedText5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_body_location_photo_adding_page);
        initializedAllElements();
    }

    public void initializedAllElements(){
        bodySelectionSymbol = findViewById(R.id.bodySelectionSymbol);
        backButton = findViewById(R.id.backButton);
        saveButton = findViewById(R.id.saveButton);
        frontPhotoButton = findViewById(R.id.frontPhotoButton);
        backPhotoButton = findViewById(R.id.backPhotoButton);
        cameraButton = findViewById(R.id.cameraButton);
        selectedBodyLocation = findViewById(R.id.selectedBodyLocation);
        fixedText1 = findViewById(R.id.fixedText1);
        fixedText2 = findViewById(R.id.fixedText2);
        fixedText3 = findViewById(R.id.fixedText3);
        fixedText4 = findViewById(R.id.fixedText4);
        fixedText5 = findViewById(R.id.fixedText5);
    }
}
