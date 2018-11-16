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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.PaperDollController.BodyPart;
import com.example.cmput301f18t09.cureall.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PatientBodyLocationPhotoAddingPageActivity extends AppCompatActivity {
    private ImageView bodySelectionSymbol;
    private ImageButton backButton, saveButton, frontPhotoButton, backPhotoButton, cameraButton;
    private TextView selectedBodyLocation, fixedText1,fixedText2,fixedText3,fixedText4,fixedText5;
    private String mCurrentPhotoPath;
    private int switcher;
    static final int REQUEST_IMAGE_CAPTURE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BodyPart bodyPart = (BodyPart) getIntent().getSerializableExtra("body part");
        setContentView(R.layout.activity_patient_body_location_photo_adding_page);
        initializedAllElements();

        switch (bodyPart){
            case HAND:
                selectedBodyLocation.setText("HAND");
                break;
            case HEAD:
                selectedBodyLocation.setText("HEAD");
                break;
            case HIP:
                selectedBodyLocation.setText("HIP");
                break;
            case FOOT:
                selectedBodyLocation.setText("FOOT");
                break;
            case KNEE:
                selectedBodyLocation.setText("KNEE");
                break;
            case CHEST:
                selectedBodyLocation.setText("CHEST");
                break;
            case FOREARM:
                selectedBodyLocation.setText("FOREARM");
                break;
            case SHOULDER:
                selectedBodyLocation.setText("SHOULDER");
                break;
            case LOWER_LEG:
                selectedBodyLocation.setText("LOWER LEG");
                break;
            case UPPER_LEG:
                selectedBodyLocation.setText("UPPER LEG");
                break;
            case LOWER_BACK:
                selectedBodyLocation.setText("LOWER BACK");
                break;
            case UPPER_BACK:
                selectedBodyLocation.setText("UPPER BACK");
                break;
            case NULL:
                break;
        }

        frontPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher = 0;
                dispatchTakePictureIntent();

            }
        });
        backPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switcher = 1;
                dispatchTakePictureIntent();

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
            // Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
            Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath);
            Drawable drawable = new BitmapDrawable(bitmap);
            // Button front = (Button) findViewById(R.id.Front);
            //Toast.makeText(BodyLocation.this,"neck!!!!!!", Toast.LENGTH_SHORT).show();
            if (switcher == 0)
            {
                frontPhotoButton.setBackground(drawable);
            }
            else{
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
        return image;
    }

}
