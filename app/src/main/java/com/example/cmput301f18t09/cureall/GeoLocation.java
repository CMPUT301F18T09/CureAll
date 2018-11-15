package com.example.cmput301f18t09.cureall;

public class GeoLocation {
    String Location;
    Double longtitude;
    Double latitude;


    public GeoLocation(String Location, Double longtitude, Double latitude){
        this.Location = Location;
        this.longtitude = longtitude;
        this.latitude = latitude;

    }

    public String getLocation(){return this.Location;}
    public Double getLongtitude(){return this.longtitude;}
    public Double getLatitude(){return this.latitude;}

    public void setLocation(String Location){ this.Location = Location;}
    public void setLongtitude(Double longtitude){this.longtitude = longtitude;}
    public void setLatitude(Double latitude){this.latitude = latitude;}
}
