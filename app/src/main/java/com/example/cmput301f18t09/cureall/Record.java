package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;
import java.util.Date;

public class Record {
    public String title,comment;
    public Date time;
    public GeoLocation geoLocation;
    public BodyLocation bodyLocation;
    public ArrayList<AllKindsOfPhotos> recordTrackingPhotoArrayList;

    //special fucntion
    //since getter and setter only deal with arraylist of Photos
    //we create a function to deal with single photo addition
    public void addRecordTrackingPhoto(){}
    //end

    public Record(String title, String comment, Date time) {
        this.title = title;
        this.comment = comment;
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getGeoLocation() {
        return geoLocation.getLocation();
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation.setLocation(geoLocation);
    }

    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    public ArrayList<AllKindsOfPhotos> getRecordTrackingPhotoArrayList() {
        return recordTrackingPhotoArrayList;
    }

    public void setRecordTrackingPhotoArrayList(ArrayList<AllKindsOfPhotos> recordTrackingPhotoArrayList) {
        this.recordTrackingPhotoArrayList = recordTrackingPhotoArrayList;
    }

}
