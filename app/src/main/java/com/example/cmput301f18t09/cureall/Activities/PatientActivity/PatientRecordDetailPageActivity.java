package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientProblemDetailPageAdapter;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientRecordDetailPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PatientRecordDetailPageActivity extends AppCompatActivity {
    private ImageButton backButton, geoLocationButton,viewBodyLocationPhotoButton;
    private TextView recordDetailHeader, title,titleContent, comment, commentContent;
    private TextView time, timeContent,bodyLocation,bodyLocationContent,photo;
    private RecyclerView recyclerView;
    private PatientRecordDetailPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Record record;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    //images from internet test
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    ///
    private BodyLocation bodyLocation2;
    //ends..

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_record_detail_page);
        initalizeAllElements();

        //test samples...
        /**
         ArrayList<AllKindsOfPhotos> examplePhotoList = new ArrayList<>();
         examplePhotoList.add(new AllKindsOfPhotos("from c disk", "recordTracking", 20.5,12.1,23.2));
         examplePhotoList.add(new AllKindsOfPhotos("from d disk", "recordTracking", 20.5,12.1,23.2));
         **/
        //test ends...
        //internet images test...
        /*mImageUrls.add("http://i0.hdslb.com/bfs/archive/a9fa288d901b0e423c12666b2b8fc85102a9a58e.jpg");
        mNames.add("火控雷达");
        mImageUrls.add("https://i.pximg.net/img-original/img/2018/11/13/00/00/04/71631238_p0.jpg");
        mNames.add("火控雷达");
        mImageUrls.add("https://i.pximg.net/img-original/img/2018/10/16/00/00/05/71202074_p0.png");
        mNames.add("火控雷达");
        mImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542228299179&di=602f0d494ea936d7abb820d31ff8ab54&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170614%2F61425bf8b9504d2dbd0b131abcf4114a_th.jpg");
        mNames.add("火控雷达");
        mImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542228384901&di=8d979878e7616553c30947181a80b7fb&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Fface%2F1f0e4e5c1b133fbd6d1854901c058817325cbc4e.jpg");
        mNames.add("火控雷达");*/
        //ends...



        recyclerView = findViewById(R.id.ListOfPhotos);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        mAdapter = new PatientRecordDetailPageAdapter(this,mNames,mImageUrls);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


    }
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
        /////////////////////////////////////////////////////////////////////
        record = (Record)getIntent().getSerializableExtra("record");
        titleContent.setText(record.getTitle());
        commentContent.setText(record.getComment());
        timeContent.setText(df.format(record.getTime()) );
        bodyLocationContent.setText(record.getBodyLocation().getBodyLocationName());
        ArrayList<AllKindsOfPhotos> photos = record.getRecordTrackingPhotoArrayList();
        for(AllKindsOfPhotos each : photos)
        {
            mImageUrls.add(each.getPhotoLocation());
            mNames.add("123");
        }



    }
    @Override
    protected void onStart() {
        super.onStart();
        viewBodyLocationPhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bodyLocation2 = record.getBodyLocation();
                Intent intent = new Intent(PatientRecordDetailPageActivity.this, PatientPhotoFlowPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("body", bodyLocation2);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });


    }
}
