package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeoLocation {
    //String Location;
    Double lon;
    Double lat;
    List<Double> Location;



    public GeoLocation( Double longtitude, Double latitude){
       // this.Location = Location;
        //this.lon = longtitude;
        //this.lat = latitude;
        this.Location = Arrays.asList(longtitude,latitude);

    }

   // public String getLocation(){return this.Location;}
    public Double getLongtitude(){return this.lon;}
    public Double getLatitude(){return this.lat;}

   // public void setLocation(List<Double> Location){ this.Location = Location;}
    public void setLongtitude(Double longtitude){this.lon = longtitude;}
    public void setLatitude(Double latitude){this.lat = latitude;}
}
