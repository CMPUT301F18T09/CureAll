package com.example.cmput301f18t09.cureall;

import java.util.Arrays;
import java.util.List;
import java.io.Serializable;

public class GeoLocation implements Serializable {
    private Double lon;
    private Double lat;
    private  List<Double> Location;


    public GeoLocation(Double longtitude, Double latitude){
        this.Location = Arrays.asList(longtitude,latitude);


    }

    public List<Double> getLocation(){return this.Location;}
    public void setLocation(List<Double> Location){ this.Location = Location;}
}
