/**
 * Class name: BodyLocation
 *
 * Version: v1.0.0
 *
 * Date: November 1, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.model;

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
    private ArrayList<AllKindsOfPhotos> photos;

    /**
     * Init for class BodyLocation
     * @param bodyLocationName              name of bodyLocation
     * @param bodyLocationPhotoArrayList    an ArrayList of bodylocationPhoto
     */
    public BodyLocation(String bodyLocationName, ArrayList<AllKindsOfPhotos> bodyLocationPhotoArrayList) {
        this.bodyLocationName = bodyLocationName;
        this.photos = bodyLocationPhotoArrayList;
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
    public ArrayList<AllKindsOfPhotos> getBodyLocationPhotoArrayList() {
        return photos;
    }

    /**
     * setter for bodyLocationPhotoArrayList
     * @param bodyLocationPhotoArrayList        new bodyLocationPhotoArrayList
     */
    public void setBodyLocationPhotoArrayList(ArrayList<AllKindsOfPhotos> bodyLocationPhotoArrayList) {
        this.photos = bodyLocationPhotoArrayList;
    }

}
