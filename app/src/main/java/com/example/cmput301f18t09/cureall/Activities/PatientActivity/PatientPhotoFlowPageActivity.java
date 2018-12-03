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

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderRecordDetailPageActivity;
import com.example.cmput301f18t09.cureall.model.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.model.BodyLocation;
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
    private Switch aSwitch;
    private TextView fixedText1;
    private ArrayList<AllKindsOfPhotos> photos = new ArrayList<>();
    private ArrayList<String> mImageBitmaps = new ArrayList<>();
    private BodyLocation bodyLocation;
    private ViewPager viewPager;
    private photoFlowPageAdapter photoFlowPageAdapter;
    private String thecomer;
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
        aSwitch = findViewById(R.id.switch2);
        getDataFromRecordDetailPage();
        Intent intent = getIntent();
        thecomer = intent.getStringExtra("ComeFromRecordDetailPage");
        if(photos!=null){
            for (AllKindsOfPhotos each : photos) {
                mImageBitmaps.add(each.getPhotoLocation());/**new*/
            }
        }
        viewPager = findViewById(R.id.viewPager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        photoFlowPageAdapter = new photoFlowPageAdapter(this, mImageBitmaps);
        viewPager.setAdapter(photoFlowPageAdapter);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mImageBitmaps = new ArrayList<>();
                if (isChecked){
                    if (bodyLocation != null) {
                        for (AllKindsOfPhotos each : bodyLocation.getBodyLocationPhotoArrayList()) {
                            mImageBitmaps.add(each.getPhotoLocation());/**new*/
                            photoFlowPageAdapter.updateItems(mImageBitmaps);
                        }
                        viewPager.setAdapter(photoFlowPageAdapter);
                    }else {
                        Toast.makeText(PatientPhotoFlowPageActivity.this,"You have no photos to switch",Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (photos != null) {
                        for (AllKindsOfPhotos each : photos) {
                            mImageBitmaps.add(each.getPhotoLocation());/**new*/
                            photoFlowPageAdapter.updateItems(mImageBitmaps);
                        }
                        viewPager.setAdapter(photoFlowPageAdapter);
                    }else{
                        Toast.makeText(PatientPhotoFlowPageActivity.this,"You have no photos to switch",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thecomer.equals("ComeFromRecordDetailPagePATIENT")){
                    Intent intent = new Intent(PatientPhotoFlowPageActivity.this, PatientRecordDetailPageActivity.class);
                    intent.putExtra("ComeFromPhotoPage","ComeFromPhotoPage");
                    startActivity(intent);
                }else if(thecomer.equals("ComeFromRecordDetailPagePROVIDER")){
                    Intent intent = new Intent(PatientPhotoFlowPageActivity.this, ProviderRecordDetailPageActivity.class);
                    intent.putExtra("ComeFromPhotoPage","ComeFromPhotoPage");
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
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
