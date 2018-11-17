package com.example.cmput301f18t09.cureall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RecordPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_page);
        final Button save = (Button) findViewById(R.id.save_record);
        Button show = (Button) findViewById(R.id.show_record);

        save.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String problemid = "connor2000002";
                String username = "connor";


                AllKindsOfPhotos body1= new AllKindsOfPhotos("This is the address of bodyphoto1","jpg",234.123,123.123,12342.12);
                AllKindsOfPhotos body2= new AllKindsOfPhotos("This is the address of bodyphoto2","jpg",234.123,123.123,12342.12);

                ArrayList<AllKindsOfPhotos> bodyphoto = new ArrayList<AllKindsOfPhotos>();
                bodyphoto.add(body1);
                bodyphoto.add(body2);


                BodyLocation body = new BodyLocation("arm",bodyphoto);

                AllKindsOfPhotos track1= new AllKindsOfPhotos("This is the address of track1","jpg",234.123,123.123,12342.12);
                AllKindsOfPhotos track2= new AllKindsOfPhotos("This is the address of track2","jpg",234.123,123.123,12342.12);
                AllKindsOfPhotos track3= new AllKindsOfPhotos("This is the address of track3","jpg",234.123,123.123,12342.12);
                AllKindsOfPhotos track4= new AllKindsOfPhotos("This is the address of track4","jpg",234.123,123.123,12342.12);

                ArrayList<AllKindsOfPhotos> trackphoto = new ArrayList<AllKindsOfPhotos>();
                trackphoto.add(track1);
                trackphoto.add(track2);
                trackphoto.add(track3);
                trackphoto.add(track4);

                GeoLocation geo = new GeoLocation(-71.34,40.12);

                String title = "This is test record4 title";
                String comment = "This is test record commeny";
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                Date date = new Date();

                Record record =new Record(title,comment,date, geo, body,trackphoto);
                record.setUsername(username);
                record.setProblemid(problemid);
                //record.setGeoLocation(-71.34,40.12);
                ElasticSearchParams param = new ElasticSearchParams(username,record,problemid);

                ElasticSearchController.AddRecordTask addRecordTask = new ElasticSearchController.AddRecordTask();
                addRecordTask.execute(param);

            }
        });


        show.setOnClickListener(new View.OnClickListener() {
            ArrayList<Record> records = new ArrayList<Record>();
            public void onClick(View v) {
                /*ElasticSearchController.SearchGeoTask searchGeoTask = new ElasticSearchController.SearchGeoTask();
                searchGeoTask.execute("a");

                try {
                    List<Record> foundPatient= searchGeoTask.get();
                    records.addAll(foundPatient);


                } catch (Exception e) {
                    Log.i("Error", "Failed to get the user from the async object");
                }

                Log.i("Read","read end");
                Log.i("Read",Integer.toString(records.size()));*/

                ElasticSearchController.GetRecordTask getRecordTask = new ElasticSearchController.GetRecordTask();
                ElasticSearchParams params = new ElasticSearchParams("connor","connor2000002");
                getRecordTask.execute(params);

                try {
                    List<Record> foundPatient= getRecordTask.get();
                    records.addAll(foundPatient);


                } catch (Exception e) {
                    Log.i("Error", "Failed to get the user from the async object");
                }

                Log.i("Read","read end");
                Log.i("Read",Integer.toString(records.size()));
            }
        });


    }
}
