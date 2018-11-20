/**
 * Class name: PatientViewBodyLocationPhotoPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.PatientAdapter.PatientBodyLocationPhotoViewPageAdapter;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientRecordDetailPageAdapter;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

/**
 * For this activity, user(patient) can vire body location photos
 */
public class PatientViewBodyLocationPhotoPageActivity extends AppCompatActivity {
    private ImageButton backButton;
    private ImageView BodyLocationpRreview;
    private TextView fixedText1, selectedBodyLocation;
    private Switch genderSwitch;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();

    /**
     * useing views to show bodylocation photos
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_view_body_location_photo_page);
        backButton=findViewById(R.id.backButton);
        BodyLocationpRreview=findViewById(R.id.BodyLocationpRreview);
        fixedText1=findViewById(R.id.fixedText1);
        selectedBodyLocation=findViewById(R.id.selectedBodyLocation);
        genderSwitch = findViewById(R.id.genderSwitch);
        //internet images test...
        mImageUrls.add("http://i0.hdslb.com/bfs/archive/a9fa288d901b0e423c12666b2b8fc85102a9a58e.jpg");
        mNames.add("火控雷达");
        mImageUrls.add("https://i.pximg.net/img-original/img/2018/11/13/00/00/04/71631238_p0.jpg");
        mNames.add("火控雷达");
        mImageUrls.add("https://i.pximg.net/img-original/img/2018/10/16/00/00/05/71202074_p0.png");
        mNames.add("火控雷达");
        mImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542228299179&di=602f0d494ea936d7abb820d31ff8ab54&imgtype=0&src=http%3A%2F%2Fimg.mp.itc.cn%2Fupload%2F20170614%2F61425bf8b9504d2dbd0b131abcf4114a_th.jpg");
        mNames.add("火控雷达");
        mImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542228384901&di=8d979878e7616553c30947181a80b7fb&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Fface%2F1f0e4e5c1b133fbd6d1854901c058817325cbc4e.jpg");
        mNames.add("火控雷达");
        //ends...
        recyclerView = findViewById(R.id.listOfBodyLocationPhotos);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mAdapter = new PatientBodyLocationPhotoViewPageAdapter(this,mNames,mImageUrls);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}
