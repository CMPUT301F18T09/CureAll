/**
 * Class name: LocationPickerActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientRecordAddingPageActivity;
import com.example.cmput301f18t09.cureall.model.GeoLocation;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.model.Record;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * This is an activity that allows the user choose a geolocation
 * This is is not a map activity, this activity allows you to get
 * connect with google map service and help you to save the information
 * you got from the map, such as the location coordinate or unique name of
 * the selected place.
 * It needs the data from former pages, such as the records, problem id,
 * The purpose is to save to geolocation information to its corresponding record
 */
public class LocationPickerActivity extends AppCompatActivity {

    private Button button, saveButton;
    private TextView textView;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private String comefrom ="";
    private String geolocation;
    private LatLng location;
    //variables from former activities..
    private Problem problem;
    private Record record;
    private GeoLocation recordGeoLocation;
    private Double longitude;
    private Double latitude;
    /**
     * initialize some buttons and their listeners for user to click
     * such as the button, allows to choose gelocation and the buttons to save.
     * It also contains the check segment to check if we can connect the internet
     * so that user can open the googlemap service.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        button = (Button) findViewById(R.id.GetCurrentLocation);
        saveButton =findViewById(R.id.saveButton);
        textView = (TextView) findViewById(R.id.View);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        // new added for check whether from search activity
       // comefrom = getIntent().getStringExtra("mode");

        getDataFromRecordAddingPage();

        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                textView.append("\n " + location.getLatitude() + " " + location.getLongitude());
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                    Manifest.permission.INTERNET}, 10);
            return;
        } else {
            configureButton();
        }

        GeoLocation = (TextView) findViewById(R.id.PickGeoLocation);
        /**
         * this button listener can identify if you want to open the google map
         */
        GeoLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder = new PlacePicker.IntentBuilder();
                try {
                    startActivityForResult(builder.build(LocationPickerActivity.this), PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    /**
     * get the result when back from google map
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }
    /**
     * This fucntion is to get your current geoLocation by gps
     * this function is not finished as we will be finished in project 5
     */
    private void configureButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager.requestLocationUpdates("gps", 0, 0, locationListener);
            }
        });
    }


    int PLACE_PICKER_REQUEST = 1;
    private TextView GeoLocation;

    /**
     * when back from google map
     * save the required information for records
     * gives a toast to show your chosen location's coordinate
     * @param requestCode
     * @param resultCode
     * @param data
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == PLACE_PICKER_REQUEST){
            if (resultCode == RESULT_OK){
                Place place = PlacePicker.getPlace(this,data);
                location = place.getLatLng();
                //location is geolocation...
                //can be converted to string and double...
                Toast.makeText(this, Double.toString(location.latitude) + " " + Double.toString(location.longitude), Toast.LENGTH_SHORT).show();
                geolocation = String.format("%s",place.getAddress());//(String) place.getAddress();
                GeoLocation.setText(geolocation);
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        /**
         * this is the function of save button
         * it can check if user select a geolocation
         * If not, it will reminder the user to choose one.
         * If yes, it will save the data and pass the records and problems
         * back to the records detail page
         */
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             /*   if (comefrom.equals(null))
                {*/

                    if (geolocation == null) {
                        Toast.makeText(LocationPickerActivity.this, "You havn't choose and location yet!", Toast.LENGTH_SHORT).show();
                    } else {
                        if (record != null && problem != null) {
                            longitude = location.longitude;
                            latitude = location.latitude;
                            recordGeoLocation = new GeoLocation(longitude, latitude);
                        }

                        record.setGeoLocation(recordGeoLocation);
/*                    ElasticSearchParams param = new ElasticSearchParams(record.getUsername(),record,record.getProblemid());
                    ElasticSearchController.AddRecordTask addRecordTask = new ElasticSearchController.AddRecordTask();
                    addRecordTask.execute(param);*/
                        Intent passGeoLocationBack = new Intent(LocationPickerActivity.this, PatientRecordAddingPageActivity.class);
                        //              bundle.putSerializable("record", record);
                        passDataToRecordAddingPage(problem, record);
                        passGeoLocationBack.putExtra("ComeFromGeoLocationSelectionPage", "ComeFromGeoLocationSelectionPage");
                        startActivity(passGeoLocationBack);

                    }
          //  }
    /*        else if(comefrom.equals("ComeFromRecordSearch")){
                    longitude = location.longitude;
                    latitude = location.latitude;
                    recordGeoLocation = new GeoLocation(longitude, latitude);
                    Intent passGeoLocationBack = new Intent(LocationPickerActivity.this, SearchActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ComefromLocationPicker",recordGeoLocation);
                    //              bundle.putSerializable("record", record);
                    passGeoLocationBack.putExtra("search method","record");
                    passGeoLocationBack.putExtras(bundle);
                    startActivity(passGeoLocationBack);
                }*/

            }
        });
    }
    /**
     * when leave this activity, kill it
     */
    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }

    public void getDataFromRecordAddingPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordAddingData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json1 = sharedPreferences2.getString("problem",null);
        String json2= sharedPreferences2.getString("record",null);
        Type type1 = new TypeToken<Problem>(){}.getType();
        Type type2 = new TypeToken<Record>(){}.getType();
        problem = gson.fromJson(json1,type1);
        record =gson.fromJson(json2,type2);
    }
    public void passDataToRecordAddingPage(Problem problem, Record record){
        SharedPreferences sharedPreferences2 = getSharedPreferences("GeoLocationData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        String json2 = gson.toJson(record);
        editor2.putString("problem",json);
        editor2.putString("record",json2);
        editor2.apply();
    }
}
