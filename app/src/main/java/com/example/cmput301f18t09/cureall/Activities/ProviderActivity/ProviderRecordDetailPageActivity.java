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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_record_detail_page);
        initalizeAllElements();
        //test samples...
        /**
        ArrayList<AllKindsOfPhotos> examplePhotoList = new ArrayList<>();
        examplePhotoList.add(new AllKindsOfPhotos("from c disk", "recordTracking", 20.5,12.1,23.2));
        examplePhotoList.add(new AllKindsOfPhotos("from d disk", "recordTracking", 20.5,12.1,23.2));
         **/
        //test ends...
        //internet images test...
        /*mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("austrailia");
        mImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542148629544&di=d14b9d52db9b6cbaab936e114d097921&imgtype=0&src=http%3A%2F%2Fi2.hdslb.com%2Fbfs%2Fface%2Fd0f5ea1cc344cfff872bb2f769c3fb729f2daa9b.jpg");
        mNames.add("paojie");
        mImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542148629540&di=1e3428905e841614cc73ceec738fe4e3&imgtype=0&src=http%3A%2F%2Fspider.nosdn.127.net%2F975288865f4cc48175c0b848cc68684e.jpeg");
        mNames.add("御坂美琴");
        mImageUrls.add("http://03imgmini.eastday.com/mobile/20181110/20181110155653_d41d8cd98f00b204e9800998ecf8427e_4.jpeg");
        mNames.add("死掉的芙兰达");
        mImageUrls.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1542150416782&di=6008780c2edc7531e5330d018f1ad769&imgtype=0&src=http%3A%2F%2Fimg4.dwstatic.com%2Fdf%2F1311%2F247931471395%2F247931649921.gif");
        mNames.add("用生命卖萌的芙兰达");*/
        //ends...

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
