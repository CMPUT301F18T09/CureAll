/**
 * Class name: PatientRecordDetailPageActivity
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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.ViewLocationOnMapActivity;
import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientRecordDetailPageAdapter;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * For this activity, user(patient) will view all details for a record
 */
public class PatientRecordDetailPageActivity extends AppCompatActivity {
    private ImageButton backButton, geoLocationButton,viewBodyLocationPhotoButton;
    private TextView recordDetailHeader, title,titleContent, comment, commentContent;
    private TextView time, timeContent,bodyLocation,bodyLocationContent,photo;
    private RecyclerView recyclerView;
    private PatientRecordDetailPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Record record;
    private Problem problem;
    private ArrayList<Problem> problems;
    private Patient patient;
    private ArrayList<Record> records;

    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //images from internet test
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    ///
    private BodyLocation bodyLocation2;
    //ends..

    /**
     * set init value for elements used in this activity
     * (or give reference)
     * including recyclerView, adapter
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_detail_page);
        initalizeAllElements();
        recyclerView = findViewById(R.id.ListOfPhotos);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mAdapter = new PatientRecordDetailPageAdapter(this,mNames,mImageUrls);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        Intent intent = getIntent();
        if (intent.getStringExtra("ComeFromProblemDetail") != null && intent.getStringExtra("ComeFromProblemDetail").equals("ComeFromProblemDetail")){
            getDataFromProblemDetailPage();
        }

        /**
         * set the tile and description based on what you entered in record adding page!
         */
        titleContent.setText(record.getTitle());
        commentContent.setText(record.getComment());
        timeContent.setText(df.format(record.getTime()) );
        if (record.getBodyLocation()!=null) {
            bodyLocationContent.setText(record.getBodyLocation().getBodyLocationName());
        }
        ArrayList<AllKindsOfPhotos> photos = record.getRecordTrackingPhotoArrayList();
        if(photos!=null){

            for (AllKindsOfPhotos each : photos) {
                mImageUrls.add(each.getPhotoLocation());
                mNames.add("123");
            }
        }
    }

    /**
     * set init value for elements used in this activity
     * (or give reference)
     * including (Image)buttons, textviews, record's info
     */
    public void initalizeAllElements(){
        backButton = (ImageButton) findViewById(R.id.backButton);
        geoLocationButton = (ImageButton) findViewById(R.id.geoLocationButton);
        title = findViewById(R.id.title);
        titleContent = findViewById(R.id.titleContent);
        comment = findViewById(R.id.comment);
        commentContent = findViewById(R.id.commentContent);
        time = findViewById(R.id.time);
        timeContent = findViewById(R.id.timeContent);
        bodyLocation = findViewById(R.id.bodyLocation);
        bodyLocationContent = findViewById(R.id.bodyLocationContent);
        photo = findViewById(R.id.photo);
        viewBodyLocationPhotoButton = findViewById(R.id.viewBodyLocationPhotoButton);
        recordDetailHeader = findViewById(R.id.recordDetailHeader);
    }

    /**
     * set listeners for buttons
     */
    @Override
    protected void onStart() {
        super.onStart();

        //set viewBodyLocationPhotoButton listener
        viewBodyLocationPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyLocation2 = record.getBodyLocation();
                // TODO change "PatientViewBodyLocationPhotoPageActivity"
                Intent intent = new Intent(PatientRecordDetailPageActivity.this, PatientPhotoFlowPageActivity.class);
                passDataToPhotoFlowPage(bodyLocation2);
                intent.putExtra("ComeFromRecordDetailPage","ComeFromRecordDetailPage");
                startActivity(intent);
            }
        });

        //set backButton listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientRecordDetailPageActivity.this, PatientProblemDetailPageActivity.class);
                intent.putExtra("ComeFromRecordDetailPage","ComeFromRecordDetailPage");
                startActivity(intent);
            }
        });
        //2018-11-19 addons
        final int PLACE_PICKER_REQUEST = 1;
        geoLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent map = new Intent(PatientRecordDetailPageActivity.this, ViewLocationOnMapActivity.class);
                //TODO REMINDER DONT NEED TO CHANGE HERE
                Bundle geoLocation = new Bundle();
                geoLocation.putDouble("log", record.getGeoLocation().getLocation().get(0));
                geoLocation.putDouble("lat", record.getGeoLocation().getLocation().get(1));
                map.putExtras(geoLocation);
                startActivity(map);
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        //TODO activity need to terminate
    }

    public void getDataFromProblemDetailPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("record",null);
        Type type = new TypeToken<Record>(){}.getType();
        record = gson.fromJson(json,type);
    }
    public void passDataToPhotoFlowPage(BodyLocation bodyLocation2){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(bodyLocation2);/**save in gson format*/
        editor2.putString("bodyLocation2",json);
        editor2.apply();
    }

}
