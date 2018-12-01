/**
 * Class name: PatientBodyLocationPhotoAddingPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * For this activity, user(patient) can add photo for body location
 */
public class PatientBodyLocationPhotoAddingPageActivity extends AppCompatActivity {
    private ImageView bodySelectionSymbol;
    private ImageButton backButton, saveButton, frontPhotoButton, backPhotoButton, cameraButton;
    private TextView selectedBodyLocation, fixedText1,fixedText2,fixedText4,fixedText5;
    private ArrayList<AllKindsOfPhotos> pictures = new ArrayList<>();
    static final int FRONT_IMAGE_CAPTURE = 1;
    static final int BACK_IMAGE_CAPTURE = 2;
    private Record record;
    private BodyLocation bodyLocation;



    /**
     * set all listeners for buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_body_location_photo_adding_page);
        initializedAllElements();
        getDataFromPaperDollSelectionPage();
        bodyLocation = record.getBodyLocation();
        if (bodyLocation != null)
        {
            selectedBodyLocation.setText(bodyLocation.getBodyLocationName());
        }
    }
    @Override
    protected void onStart() {
        super.onStart();
        //set frontPhotoButton listener
        frontPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(FRONT_IMAGE_CAPTURE);
                //Log.i("pics", String.valueOf(pictures.size()));

            }
        });

        //set backPhotoButton listener
        backPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(BACK_IMAGE_CAPTURE);

            }
        });

        //set saveButton listener
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO add a check if patient has two body location pictures
                if (frontPhotoButton.getDrawable().getConstantState()
                        != getResources().getDrawable(R.drawable.body_location_button).getConstantState() &&
                        backPhotoButton.getDrawable().getConstantState() !=
                                getResources().getDrawable(R.drawable.body_location_button).getConstantState()) {
                    bodyLocation.setBodyLocationPhotoArrayList(pictures);
                    record.setBodyLocation(bodyLocation);
                    Intent intent = new Intent(PatientBodyLocationPhotoAddingPageActivity.this, PatientRecordAddingPageActivity.class);
                    passDataToRecordAddingPage(record);
                    intent.putExtra("ComeFromBodyLocationPhotoAddingPage", "ComeFromBodyLocationPhotoAddingPage");
                    startActivity(intent);
                } else {
                    Toast.makeText(PatientBodyLocationPhotoAddingPageActivity.this, "You have to sumbit front or back body location pictures!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set backButton listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientBodyLocationPhotoAddingPageActivity.this, PatientPaperDollSelectionPageActivity.class);
                //TODO
                intent.putExtra("ComeFromBodyLocationPhotoAddingPage","ComeFromBodyLocationPhotoAddingPage");
                startActivity(intent);
            }
        });
    }

    /**
     * active next activity: take photos
     */
    private void dispatchTakePictureIntent(Integer code) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        startActivityForResult(takePictureIntent, code);
    }

    /**
     * deal with the result for activity done
     * @param requestCode   (build in)
     * @param resultCode    (build in)
     * @param data          (build in)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        if (requestCode == FRONT_IMAGE_CAPTURE) {
            AllKindsOfPhotos newpicture = new AllKindsOfPhotos(bitmap,temp,"Front Picture",0.0,0.0,0.0);
            frontPhotoButton.setImageBitmap(bitmap);
            for (AllKindsOfPhotos each: pictures){
                if (each.getPhotoType() .equals("Front Picture")){
                    pictures.remove(each);
                }
            }
            pictures.add(newpicture);

        }
        else if (requestCode == BACK_IMAGE_CAPTURE){
            AllKindsOfPhotos newpicture = new AllKindsOfPhotos(bitmap,temp,"Back Picture",0.0,0.0,0.0);
            backPhotoButton.setImageBitmap(bitmap);
            for (AllKindsOfPhotos each: pictures){
                if (each.getPhotoType() .equals("Back Picture")){
                    pictures.remove(each);
                }
            }
            pictures.add(newpicture);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    public void getDataFromPaperDollSelectionPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("PaperDollSelectionData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("record",null);
        Type type = new TypeToken<Record>(){}.getType();
        record = gson.fromJson(json,type);
    }
    public void passDataToRecordAddingPage(Record record){
        SharedPreferences sharedPreferences2 = getSharedPreferences("BodyLocationPhotoAddingData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(record);/**save in gson format*/
        editor2.putString("record",json);
        editor2.apply();
    }
    /**
     * set init value for elements used in this activity
     * (or give reference)
     * including buttons, textviews, patients, records, problems, bodylocations
     */
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
        fixedText4 = findViewById(R.id.fixedText4);
        fixedText5 = findViewById(R.id.fixedText5);
    }
}
