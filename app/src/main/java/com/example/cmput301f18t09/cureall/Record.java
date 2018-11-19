package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;

public class Record implements Serializable{
    public String title,comment;
    public Date time;
    public GeoLocation geoLocation;
    public BodyLocation bodyLocation;
    public ArrayList<AllKindsOfPhotos> recordTrackingPhotoArrayList;
    public String username;
    public String problemid;
    public String ID;
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
    public String getUsername(){return  this.username;}
    public String getProblemid(){return  this.problemid;}
    public void setUsername(String username){this.username = username;}
    public void setProblemid(String problemid){this.problemid = problemid;}
    public String getID() {
        return this.ID;
    }
    public void setID(String ID) {
        this.ID = ID;
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

    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
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
