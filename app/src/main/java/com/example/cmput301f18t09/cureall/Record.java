/**
 * Class name: Record
 *
 * Version: v1.0.0
 *
 * Date: November 1, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
/**
 * Model class for recording CareProvider
 *
 * @author Ruiqin, Pi
 * @version 1.0.0
 */
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
    /**
     * addRecordTrackingPhoto
     */
    public void addRecordTrackingPhoto(){}
    //end

    /**
     * Init for Record
     * @param title     title for new record
     * @param comment   comment for new record
     * @param time      time for new record
     */
    public Record(String title, String comment, Date time) {
        this.title = title;
        this.comment = comment;
        this.time = time;
    }

    /**
     * getter for username
     * @return username
     */
    public String getUsername(){return  this.username;}
    /**
     * getter for problemid
     * @return problemid
     */
    public String getProblemid(){return  this.problemid;}

    /**
     * setter for username
     * @param username currnet username
     */
    public void setUsername(String username){this.username = username;}

    /**
     * setter for problemid
     * @param problemid new problemid
     */
    public void setProblemid(String problemid){this.problemid = problemid;}

    /**
     * getter for id
     * @return id
     */
    public String getID() {
        return this.ID;
    }

    /**
     * setter for id
     * @param ID new id
     */
    public void setID(String ID) {
        this.ID = ID;
    }

    /**
     * getter for title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * setter for title
     * @param title new title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * getter for comment
     * @return comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * setter for comment
     * @param comment new comment
     */
    public void setComment(String comment) {
        this.comment = comment;
    }

    /**
     * getter for time
     * @return time
     */
    public Date getTime() {
        return time;
    }

    /**
     * setter for time
     * @param time new time
     */
    public void setTime(Date time) {
        this.time = time;
    }

    /**
     * getter for geoLocation
     * @return geoLocation
     */
    public GeoLocation getGeoLocation() {
        return geoLocation;
    }

    /**
     * setter for geoLocation
     * @param geoLocation new geoLocation
     */
    public void setGeoLocation(GeoLocation geoLocation) {
        this.geoLocation = geoLocation;
    }

    /**
     * getter for bodyLocation
     * @return bodyLocation
     */
    public BodyLocation getBodyLocation() {
        return bodyLocation;
    }

    /**
     * setter for bodyLocation
     * @param bodyLocation new bodyLocation
     */
    public void setBodyLocation(BodyLocation bodyLocation) {
        this.bodyLocation = bodyLocation;
    }

    /**
     * getter for recordTrackingPhotoArrayList
     * @return recordTrackingPhotoArrayList
     */
    public ArrayList<AllKindsOfPhotos> getRecordTrackingPhotoArrayList() {
        return recordTrackingPhotoArrayList;
    }

    /**
     * setter for recordTrackingPhotoArrayList
     * @param recordTrackingPhotoArrayList new recordTrackingPhotoArrayList
     */
    public void setRecordTrackingPhotoArrayList(ArrayList<AllKindsOfPhotos> recordTrackingPhotoArrayList) {
        this.recordTrackingPhotoArrayList = recordTrackingPhotoArrayList;
    }

}
