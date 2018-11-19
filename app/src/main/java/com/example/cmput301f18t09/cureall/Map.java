package com.example.cmput301f18t09.cureall;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity implements OnMapReadyCallback{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.geo);
        assert mapFragment != null;
        mapFragment.getMapAsync((OnMapReadyCallback) this);
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
    LatLng geolocation = new LatLng(37.421, -122.084);
    googleMap.addMarker(new MarkerOptions().position(geolocation));
    }


}
