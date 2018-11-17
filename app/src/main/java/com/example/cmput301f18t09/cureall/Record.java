package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class Record {
    public String title,comment;
    public Date time;
    public GeoLocation geoLocation;
    public BodyLocation bodyLocation;
    List<Double> Location;
    public ArrayList<AllKindsOfPhotos> recordTrackingPhotoArrayList;
    String username;
    String problemid;
    String ID;

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

    public Record(String title, String comment, Date time, GeoLocation geo, BodyLocation body,ArrayList<AllKindsOfPhotos> trackphoto){
        this.title = title;
        this.comment = comment;
        this.time = time;
        this.geoLocation = geo;
        this.bodyLocation = body;
        this.recordTrackingPhotoArrayList = trackphoto;
    }
    public String getUsername(){return  this.username;}
    public String getProblemid(){return  this.problemid;}

    public void setUsername(String username){this.username = username;}
    public void setProblemid(String problemid){this.problemid = problemid;}

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getID() {
        return this.ID;
    }

    public void setID(String ID) {
        this.ID = ID;
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

   // public String getGeoLocation() {
   //     return geoLocation.getLocation();
    //}

    //public void setGeoLocation(double longtitude,double latitude) {
   //     this.geo = Arrays.asList(longtitude,latitude);
    //}

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
