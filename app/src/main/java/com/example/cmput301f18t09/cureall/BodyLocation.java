/**
 * Class name: BodyLocation
 *
 * Version: v1.0.0
 *
 * Date: November 1, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Model class for recording BodyLocation
 *
 * @author Ruiqin, Pi
 * @version 1.0.0
 */
public class BodyLocation implements Serializable {
    private String bodyLocationName;
    private ArrayList<String> photoPaths;

    //special fucntion
    public String getLocationFromPaperDollPhoto(){
        return "arm";
    }
    //only deal with one photo addition
    public void addBodyLocationPhoto(){ }
    //end

    /**
     * Init for class BodyLocation
     * @param bodyLocationName              name of bodyLocation
     * @param bodyLocationPhotoArrayList    an ArrayList of bodylocationPhoto
     */
    public BodyLocation(String bodyLocationName, ArrayList<String> bodyLocationPhotoArrayList) {
        this.bodyLocationName = bodyLocationName;
        this.photoPaths = bodyLocationPhotoArrayList;
    }

    /**
     * getter for bodyLocationName
     * @return bodyLocationName
     */
    public String getBodyLocationName() {
        return bodyLocationName;
    }

    /**
     * setter for bodyLocationName
     * @param bodyLocationName      new bodyLocationName
     */
    public void setBodyLocationName(String bodyLocationName) {
        this.bodyLocationName = bodyLocationName;
    }

    /**
     * getter for photoPaths
     * @return photoPaths
     */
    public ArrayList<String> getBodyLocationPhotoArrayList() {
        return photoPaths;
    }

    /**
     * setter for bodyLocationPhotoArrayList
     * @param bodyLocationPhotoArrayList        new bodyLocationPhotoArrayList
     */
    public void setBodyLocationPhotoArrayList(ArrayList<String> bodyLocationPhotoArrayList) {
        this.photoPaths = bodyLocationPhotoArrayList;
    }

}
