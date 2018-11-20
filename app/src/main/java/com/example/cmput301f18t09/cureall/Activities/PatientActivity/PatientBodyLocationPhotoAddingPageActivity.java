package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class PatientBodyLocationPhotoAddingPageActivity extends AppCompatActivity {
    private ImageView bodySelectionSymbol;
    private ImageButton backButton, saveButton, frontPhotoButton, backPhotoButton, cameraButton;
    private TextView selectedBodyLocation, fixedText1,fixedText2,fixedText3,fixedText4,fixedText5;
    private String mCurrentPhotoPath;
    private int switcher;
    private ArrayList<String> pictures = new ArrayList<String>();
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Record record;
    private BodyLocation bodyLocation;
    private Problem problem;
    private Patient patient;
    private ArrayList<Record> records;
    private ArrayList<Problem> problems;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_body_location_photo_adding_page);
        initializedAllElements();



        frontPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher = 0;
                dispatchTakePictureIntent();
                //Log.i("pics", String.valueOf(pictures.size()));

            }
        });
        backPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher = 1;
                dispatchTakePictureIntent();

            }
        });
        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher = 3;
                Log.i("pics", String.valueOf(pictures.size()));
                dispatchTakePictureIntent();

            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyLocation.setBodyLocationPhotoArrayList(pictures);
                record.setBodyLocation(bodyLocation);
                Intent intent = new Intent(PatientBodyLocationPhotoAddingPageActivity.this, PatientRecordAddingPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem",problem);
                bundle.putSerializable("record", record);
                bundle.putSerializable("records", records);
                bundle.putSerializable("patient",patient);
                bundle.putSerializable("problems", problems);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientBodyLocationPhotoAddingPageActivity.this, PatientPaperDollSelectionPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem",problem);
                bundle.putSerializable("record", record);
                bundle.putSerializable("records", records);
                bundle.putSerializable("patient",patient);
                bundle.putSerializable("problems", problems);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

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
        patient =(Patient)getIntent().getSerializableExtra("patient");
        record = (Record) getIntent().getSerializableExtra("record");
        problem = (Problem)getIntent().getSerializableExtra("problem");
        records = (ArrayList<Record>)getIntent().getSerializableExtra("records");
        problems = (ArrayList<Problem>)getIntent().getSerializableExtra("problems");
        bodyLocation = record.getBodyLocation();
        if (bodyLocation != null)
        {
            selectedBodyLocation.setText(bodyLocation.getBodyLocationName());
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File

            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.cmput301f18t09.cureall.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            Drawable drawable = new BitmapDrawable(bitmap);
            if (switcher == 0)
            {
                frontPhotoButton.setBackground(drawable);
            }
            else if (switcher == 1){
                backPhotoButton.setBackground(drawable);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        pictures.add(mCurrentPhotoPath);
        return image;
    }

}
