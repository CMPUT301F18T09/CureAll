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

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.ViewLocationOnMapActivity;
import com.example.cmput301f18t09.cureall.model.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.model.BodyLocation;
import com.example.cmput301f18t09.cureall.model.GeoLocation;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientRecordDetailPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.model.Record;
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
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private BodyLocation bodyLocation2;
    //ends..
    private ArrayList<AllKindsOfPhotos> photos;
    private ArrayList<AllKindsOfPhotos> photoFlowShow;
    private String targetPage;
    private boolean checker;
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

        Intent intent = getIntent();
        if (intent.getStringExtra("ComeFromProblemDetail") != null && intent.getStringExtra("ComeFromProblemDetail").equals("ComeFromProblemDetail")){
            getDataFromLocal("ProblemDetailData");
        }
        else if(intent.getStringExtra("ComeFromPhotoPage") != null && intent.getStringExtra("ComeFromPhotoPage").equals("ComeFromPhotoPage")){
            getDataFromLocal("updateRecordPhotos");
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
        photos = record.getRecordTrackingPhotoArrayList();
        recyclerView = findViewById(R.id.ListOfPhotos);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mAdapter = new PatientRecordDetailPageAdapter(this,photos);/**new*/
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * set listeners for buttons
     */
    @Override
    protected void onStart() {
        super.onStart();
        targetPage ="default";
        //set viewBodyLocationPhotoButton listener
        viewBodyLocationPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyLocation2 = record.getBodyLocation();
                photoFlowShow = record.getRecordTrackingPhotoArrayList();
                if (bodyLocation2 != null && photoFlowShow != null) {
                    Intent intent = new Intent(PatientRecordDetailPageActivity.this, PatientPhotoFlowPageActivity.class);
                    passDataToPhotoFlowPage(bodyLocation2, photoFlowShow);
                    saveDataChanged(record);
                    intent.putExtra("ComeFromRecordDetailPage", "ComeFromRecordDetailPagePATIENT");
                    startActivity(intent);
                }else{
                    Toast.makeText(PatientRecordDetailPageActivity.this,"You have no photos",Toast.LENGTH_SHORT).show();
                }
            }
        });

        //set backButton listener
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientRecordDetailPageActivity.this, PatientProblemDetailPageActivity.class);
                intent.putExtra("ComeFromRecordDetailPage","ComeFromRecordDetailPage");
                //this is used to pass data
                saveDataChanged(record);
                startActivity(intent);
            }
        });
        //2018-11-19 addons
        geoLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (record.getGeoLocation() != null){
                    Intent map = new Intent(PatientRecordDetailPageActivity.this, ViewLocationOnMapActivity.class);
                    Bundle geoLocation = new Bundle();
                    ArrayList<GeoLocation> geoLocationArrayList = new ArrayList<>();
                    geoLocationArrayList.add(record.getGeoLocation());
                    geoLocation.putSerializable("GeolocationList",geoLocationArrayList);
                    map.putExtras(geoLocation);
                    targetPage = "map";
                    startActivity(map);
                }
                else {
                    Toast.makeText(PatientRecordDetailPageActivity.this,"You have not choose a location",Toast.LENGTH_SHORT).show();
                }
            }
        });
        mAdapter.setOnItemClickListener(new PatientRecordDetailPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                customDialog(photos.get(position).getPhotoType());
            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.UP){
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                if (direction == ItemTouchHelper.UP){
                    mAdapter.deletePhotos(viewHolder.getAdapterPosition());

                    //TODO record set state

                    saveDataChanged(record);
                    if(mAdapter.getItemCount() == 0){
                        Toast.makeText(PatientRecordDetailPageActivity.this
                                ,"You have no more photos",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }).attachToRecyclerView(recyclerView);
        recyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if(mAdapter.getItemCount() == 0){
                    Toast.makeText(PatientRecordDetailPageActivity.this
                            ,"You have no more photos",Toast.LENGTH_SHORT).show();
                }
            }
        });




    }
    //on start ends...

    /**
     *
     * @param name photo name
     */
    public void customDialog(String name) {
        final android.support.v7.app.AlertDialog.Builder builderSingle = new android.support.v7.app.AlertDialog.Builder(this);
        builderSingle.setTitle("Photo Name");
        String message = name;
        builderSingle.setMessage(message);

        builderSingle.setNegativeButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                }
        );
        builderSingle.show();
    }

    /**
     * behaviour of activity stops
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (targetPage.equals("default")){
            finish();
        }
    }

    /**
     *
     * @param record the current record
     */
    public void saveDataChanged(Record record){
        SharedPreferences sharedPreferences2 = getSharedPreferences("updateRecordPhotos",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(record);
        editor2.putString("record",json);
        editor2.apply();
    }

    /**
     *
     * @param name file name
     */
    public void getDataFromLocal(String name){
        SharedPreferences sharedPreferences2 = getSharedPreferences(name,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("record",null);
        Type type = new TypeToken<Record>(){}.getType();
        record = gson.fromJson(json,type);
    }

    /**
     *
     * @param bodyLocation2 body location
     * @param photoFlowShow photo arrayList
     */
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
     * set init value for elements used in this activity
     * (or give reference)
     * including (Image)buttons, textviews, record's info
     */
    public void initalizeAllElements(){
        backButton =  findViewById(R.id.backButton);
        geoLocationButton =  findViewById(R.id.geoLocationButton);
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
}
