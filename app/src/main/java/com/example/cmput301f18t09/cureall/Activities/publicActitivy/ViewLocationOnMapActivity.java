/**
 * Class name: ViewLocationOnMapActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.cmput301f18t09.cureall.model.GeoLocation;
import com.example.cmput301f18t09.cureall.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

/**
 * This map activity is only for viewing a record's geolocation
 * which means it can only show the location but not choose a location.
 * There is another map activity that allows the user choose geo location
 */
public class ViewLocationOnMapActivity extends AppCompatActivity implements OnMapReadyCallback{
    private double log, lat;
    ArrayList<GeoLocation> geoLocationArrayList ;
    ArrayList<Double> logList;
    ArrayList<Double> latList;
    ArrayList<LatLng> latLngs;

    /**
     * prepare the element (geolocation: latitude and longitude) for map
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.geo);
        assert mapFragment != null;
        mapFragment.getMapAsync((OnMapReadyCallback) this);
        //get geoLocation coordinate passed from recordDetail
        geoLocationArrayList = new ArrayList<>();
        logList = new ArrayList<>();
        latList = new ArrayList<>();
        latLngs = new ArrayList<>();
        //Bundle geoLocation = getIntent().getExtras();
        geoLocationArrayList = (ArrayList<GeoLocation>) getIntent().getSerializableExtra("GeolocationList");


/*        if (geoLocationArrayList!= null){
            log = geoLocationArrayList.getDouble("log");
            lat = geoLocationArrayList.getDouble("lat");
        }*/
        for (GeoLocation geoLocation:geoLocationArrayList)
        {
            latLngs.add(new LatLng(geoLocation.getLatitude(),geoLocation.getLongitude()));

        }

    }

    /**
     * open the map and add this geolocation into the map with a marker.
     * @param googleMap
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {


       //LatLng geolocation1 = new LatLng(lat, log);
        for(LatLng each:latLngs) {
            googleMap.addMarker(new MarkerOptions().position(each));
        }

    }
}
