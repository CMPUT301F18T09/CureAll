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

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.PatientAdapter.photoFlowPageAdapter;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

/**
 * For this activity, user(patient) can view a flow of bodyLocation photos
 * NOTICE!
 * In next project, this page will present a photo flow for record tracking photos
 * But now, This activity is temporarily used to show your bodylocation photos, the record tracking photos can only be view in the recycleview of record detail page
 */
public class PatientPhotoFlowPageActivity extends AppCompatActivity {
    private ImageButton backButton;
    private TextView fixedText1;
    private ArrayList<String> imageUrls = new ArrayList<>();
    private ArrayList<String> imageUrls2 = new ArrayList<>();
    private BodyLocation bodyLocation;

    /**
     * set init value for elements used in this activity
     * (or give reference)
     * including buttons, textviews, bodylocations, imageUrls
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_photo_flow_page);
        backButton = findViewById(R.id.backButton);
        fixedText1 = findViewById(R.id.fixedText1);
        bodyLocation = (BodyLocation)getIntent().getSerializableExtra("body");
        imageUrls = bodyLocation.getBodyLocationPhotoArrayList();
        //modify url format
        for (String url : imageUrls){
            imageUrls2.add("file://"+url);
        }
        //viewPager...
        /*imageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542265804198&di=681947a18d595e101bf49cd909597207&imgtype=0&src=http%3A%2F%2Fupload.mnw.cn%2F2017%2F1227%2F1514344378520.jpg");
        imageUrls.add("file:///storage/emulated/0/sina/weibo/weibo/test2.jpg");*/


        ViewPager viewPager = findViewById(R.id.viewPager);
        photoFlowPageAdapter adapter = new photoFlowPageAdapter(this,imageUrls2);
        viewPager.setAdapter(adapter);

    }

}
