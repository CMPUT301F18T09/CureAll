package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.ProviderAdapter.RecordDetailPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * This activity is used for presenting the details of each record
 * It uses the recycleview to carry the record tracking photos
 * This activity can not view any photos and geolocation right now! This problem or bugs can be fixed in next project
 * In this version, it can only view the word information of each record
 */
public class ProviderRecordDetailPageActivity extends AppCompatActivity {
    private ImageButton backButton, geoLocationButton;
    private TextView recordDetailHeader, title,titleContent, comment, commentContent;
    private TextView time, timeContent,bodyLocation,bodyLocationContent,photo;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private Record record;

    //images from internet test
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
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

        titleContent.setText(record.getTitle());
        commentContent.setText(record.getComment());
        timeContent.setText(df.format(record.getTime()));
        bodyLocation.setText(record.getBodyLocation().getBodyLocationName());
        //bodyLocation.setText(record.getBodyLocation());


        recyclerView = findViewById(R.id.ListOfPhotos);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mAdapter = new RecordDetailPageAdapter(this,mNames,mImageUrls);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * This function is a helper to help initialize the buttons and textviews,
     * it link the elements with the source xml file
     */
    public void initalizeAllElements(){
        Intent incomingIntent = getIntent();
        record = (Record) getIntent().getSerializableExtra("record");
        backButton = (ImageButton) findViewById(R.id.backButton);
        geoLocationButton = (ImageButton) findViewById(R.id.geoLocationButton);
        title = (TextView) findViewById(R.id.title);
        titleContent = (TextView) findViewById(R.id.titleContent);
        comment = (TextView) findViewById(R.id.comment);
        commentContent = (TextView) findViewById(R.id.commentContent);
        time = (TextView) findViewById(R.id.time);
        timeContent = (TextView) findViewById(R.id.timeContent);
        bodyLocation = (TextView) findViewById(R.id.bodyLocation);
        bodyLocationContent = (TextView) findViewById(R.id.bodyLocationContent);
        photo = (TextView) findViewById(R.id.photo);
    }

}
