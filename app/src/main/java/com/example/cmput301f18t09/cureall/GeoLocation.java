package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GeoLocation {
    //String Location;
    //Double lon;
    //Double lat;
    List<Double> Location;



    public GeoLocation( Double longtitude, Double latitude){
       // this.Location = Location;
        //this.lon = longtitude;
        //this.lat = latitude;
        this.Location = Arrays.asList(longtitude,latitude);

    }

   
   public void setLocation(Double lon, Double lat){
        this.Location = Arrays.asList(lon,lat);
   }

   public List<Double> getLocation(){
        return  this.Location;
   }
}
