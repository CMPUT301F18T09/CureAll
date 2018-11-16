package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.PatientPaperDollSelectionPageActivity;
import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.PaperDollController.BodyPart;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;
import com.google.gson.Gson;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PatientRecordAddingPageActivity extends AppCompatActivity {
    private ArrayList<String> photoPaths = new ArrayList<String>();
    private ImageButton backButton, saveButton, fromAlbumButton,
            cameraButton,geoLocationSelectButton,timeSelectButton,bodyLocationSelectButton;
    private ImageView writeSymbol;
    private TextView maxLength30, maxLength300;
    private EditText titleInput, descriptionInput;
    private String title;
    private String description;
    private Date date;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private RecordController recordController;
    private Record record;
    private BodyLocation bodyLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_add_record_page);
        initializer();
        //photoPaths = (ArrayList<String>) getIntent().getSerializableExtra("photo paths");
        if (record == null) {
            title = titleInput.getText().toString();
            description = descriptionInput.getText().toString();
            date = new Date();
            record = recordController.getNewRecord(title, description, date);
        }


        bodyLocationSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                Intent intent = new Intent(PatientRecordAddingPageActivity.this, PatientPaperDollSelectionPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record", record);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            }
        });



    }

    private void initializer()
    {
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
        recordController = new RecordController();
        record = (Record) getIntent().getSerializableExtra("record");
        if (record != null) {
            bodyLocation = record.getBodyLocation();
        }
       // bodyLocation = (BodyLocation) getIntent().getSerializableExtra("body");
    }



}
