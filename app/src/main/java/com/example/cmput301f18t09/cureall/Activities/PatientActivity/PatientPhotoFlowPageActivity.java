/**
 * Class name: PatientPhotoFlowPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.PatientAdapter.photoFlowPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * For this activity, user(patient) can view a flow of bodyLocation photos
 * NOTICE!
 * In next project, this page will present a photo flow for record tracking photos
 * But now, This activity is temporarily used to show your bodylocation photos, the record tracking photos can only be view in the recycleview of record detail page
 */
public class PatientPhotoFlowPageActivity extends AppCompatActivity {
    private ImageButton backButton;
    private Button showFront;
    private TextView fixedText1;
    private ArrayList<AllKindsOfPhotos> photos = new ArrayList<>();
    private ArrayList<String> mImageBitmaps = new ArrayList<>();
    private BodyLocation bodyLocation;

    /**
     * set init value for elements used in this activity
     * (or give reference)
     * including buttons, textviews, bodylocations, photos
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_photo_flow_page);
        backButton = findViewById(R.id.backButton);
        fixedText1 = findViewById(R.id.fixedText1);
        showFront = findViewById(R.id.showFrontAndBack);

        getDataFromRecordDetailPage();
        //photos = bodyLocation.getBodyLocationPhotoArrayList();
        if(photos!=null){

            for (AllKindsOfPhotos each : photos) {

                mImageBitmaps.add(each.getPhotoLocation());/**new*/
            }
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        ViewPager viewPager = findViewById(R.id.viewPager);
        photoFlowPageAdapter adapter = new photoFlowPageAdapter(this, mImageBitmaps);
        viewPager.setAdapter(adapter);
    }

    public void getDataFromRecordDetailPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordDetailData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("bodyLocation2",null);
        String json2 = sharedPreferences2.getString("photoFlowShow",null);
        Type type = new TypeToken<BodyLocation>(){}.getType();
        Type type2 = new TypeToken<ArrayList<AllKindsOfPhotos>>(){}.getType();
        bodyLocation = gson.fromJson(json,type);
        photos = gson.fromJson(json2,type2);
    }

}
