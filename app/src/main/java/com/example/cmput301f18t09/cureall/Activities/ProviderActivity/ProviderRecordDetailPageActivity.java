/**
 * Class name: ProviderRecordDetailPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientPhotoFlowPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientRecordDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.ViewLocationOnMapActivity;
import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.ProviderAdapter.RecordDetailPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This activity is used for presenting the details of each record
 * It uses the recycleview to carry the record tracking photos
 * This activity can not view any photos and geolocation right now! This problem or bugs can be fixed in next project
 * In this version, it can only view the word information of each record
 */
public class ProviderRecordDetailPageActivity extends AppCompatActivity {
    private ImageButton backButton, geoLocationButton,viewPhotoFlow;
    private TextView recordDetailHeader, title,titleContent, comment, commentContent;
    private TextView time, timeContent, bodyLocationName,bodyLocationContent,photo;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Record record;
    private ArrayList<AllKindsOfPhotos> photoFlowShow;

    //images from internet test
    private ArrayList<AllKindsOfPhotos> recordTrackingPhotos = new ArrayList<>();
    private BodyLocation bodyLocation;
    //ends..
    @Override
    /**
     * create the necessary information for this activity view,such as the textviews and buttons
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_record_detail_page);
        initalizeAllElements();
        //test samples...
        getDataFromProblemDetailPage();
        titleContent.setText(record.getTitle());
        commentContent.setText(record.getComment());
        timeContent.setText(df.format(record.getTime()));
        if (record.getBodyLocation() == null){
            bodyLocationName.setText("no body selected");
        }else{
            bodyLocationName.setText(record.getBodyLocation().getBodyLocationName());
        }

        //
        recordTrackingPhotos=record.getRecordTrackingPhotoArrayList();
        bodyLocation = record.getBodyLocation();
        //
        recyclerView = findViewById(R.id.ListOfPhotos);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);

        mAdapter = new RecordDetailPageAdapter(this,recordTrackingPhotos);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        geoLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record.getGeoLocation() != null){
                    Intent map = new Intent(ProviderRecordDetailPageActivity.this, ViewLocationOnMapActivity.class);
                    //TODO REMINDER DONT NEED TO CHANGE HERE
                    Bundle geoLocation = new Bundle();
                    geoLocation.putDouble("log", record.getGeoLocation().getLocation().get(0));
                    geoLocation.putDouble("lat", record.getGeoLocation().getLocation().get(1));
                    map.putExtras(geoLocation);
                    startActivity(map);
                }
                else {
                    Toast.makeText(ProviderRecordDetailPageActivity.this,"You have not choose a location",Toast.LENGTH_SHORT).show();
                }

            }
        });
        viewPhotoFlow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyLocation = record.getBodyLocation();
                photoFlowShow = record.getRecordTrackingPhotoArrayList();
                if (bodyLocation != null && photoFlowShow != null) {
                    // TODO change "PatientViewBodyLocationPhotoPageActivity"
                    Intent intent = new Intent(ProviderRecordDetailPageActivity.this, PatientPhotoFlowPageActivity.class);
                    passDataToPhotoFlowPage(bodyLocation,recordTrackingPhotos);
                    intent.putExtra("ComeFromRecordDetailPage","ComeFromRecordDetailPagePROVIDER");
                    startActivity(intent);
                }else{
                    Toast.makeText(ProviderRecordDetailPageActivity.this,"You have no photos",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getDataFromProblemDetailPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProviderProblemDetailData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("record",null);
        Type type = new TypeToken<Record>(){}.getType();
        record = gson.fromJson(json,type);
    }
    public void passDataToPhotoFlowPage(BodyLocation bodyLocation2,ArrayList<AllKindsOfPhotos> photoFlowShow){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bodyLocation2);/**save in gson format*/
        String json2 = gson.toJson(photoFlowShow);
        editor2.putString("bodyLocation2",json);
        editor2.putString("photoFlowShow",json2);
        editor2.apply();
    }

    /**
     * This function is a helper to help initialize the buttons and textviews,
     * it link the elements with the source xml file
     */
    public void initalizeAllElements(){
        backButton = findViewById(R.id.backButton);
        geoLocationButton = findViewById(R.id.geoLocationButton);
        title = findViewById(R.id.title);
        titleContent = findViewById(R.id.titleContent);
        comment =  findViewById(R.id.comment);
        commentContent =  findViewById(R.id.commentContent);
        time =  findViewById(R.id.time);
        timeContent =  findViewById(R.id.timeContent);
        bodyLocationName =  findViewById(R.id.bodyLocation);
        bodyLocationContent =  findViewById(R.id.bodyLocationContent);
        viewPhotoFlow = findViewById(R.id.viewPhotoFlow);
        photo = findViewById(R.id.photo);
    }

}
