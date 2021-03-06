/**
 * Class name: GeoLocation
 *
 * Version: v1.0.0
 *
 * Date: November 1, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.model;

import java.util.Arrays;
import java.util.List;
import java.io.Serializable;
/**
 * Model class for recording CareProvider
 *
 * @author Ruiqin, Pi
 * @version 1.0.0
 */
public class GeoLocation implements Serializable {
    double lon;
    double lat;
    private List<Double> Location;

    /**
     * Init for GeoLocation
     * @param longtitude    real world longtitude coordinate
     * @param latitude      real world latitude coordinate
     */
    public GeoLocation(Double longtitude, Double latitude){
        this.Location = Arrays.asList(longtitude,latitude);
        this.lon = longtitude;
        this.lat = latitude;

    }

    /**
     * getter for Longitude
     * @return lon
     */
    public double getLongitude() {
        return lon;
    }

    /**
     * getter for Latitude
     * @return lat
     */
    public double getLatitude() {
        return lat;
    }
    /**
     * getter for geolocation
     * @return  an ArrayList of (longtitude,latitude)
     */
    public List<Double> getLocation(){return this.Location;}

    /**
     * setter for geolocation
     * @param Location an ArrayList of (longtitude,latitude)
     */
    public void setLocation(List<Double> Location){ this.Location = Location;}
}
