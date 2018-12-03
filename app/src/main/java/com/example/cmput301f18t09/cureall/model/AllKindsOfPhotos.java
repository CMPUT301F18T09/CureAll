/**
 * Class name: AllKindsOfPhotos
 *
 * Version: v1.0.0
 *
 * Date: November 1, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.model;

//?PhotoLocation=/Location/c/abd.jpg&PhotoType=jpg&photoSize=12342&photoWidth=123.22&photoLength=126.88
import android.graphics.Bitmap;

import java.io.Serializable;
/**
 * Model class for recording all kinds of photos
 *
 * @author Ruiqin, Pi
 * @version 1.0.0
 */

public class AllKindsOfPhotos implements Serializable{
    private String photoLocation, photoType;
    private Double photoSize, photoWidth, photoLength;
    private Bitmap pic;

    /**
     * Init for class AllKindsOfPhotos
     * @param photoLocation     location of photo
     * @param photoType         type of photo
     * @param photoSize         size of photo
     * @param photoWidth        width of photo
     * @param photoLength       length of photo
     */
    public AllKindsOfPhotos(Bitmap pic,String photoLocation, String photoType, Double photoSize, Double photoWidth, Double photoLength) {
        this.photoLocation = photoLocation;
        this.photoType = photoType;
        this.photoSize = photoSize;
        this.photoWidth = photoWidth;
        this.photoLength = photoLength;
        this.pic = pic;
    }
    public AllKindsOfPhotos(){}

    /**
     * getter for param photoType
     * @return photoType
     */
    public String getPhotoType() {
        return photoType;
    }

    /**
     * setter for param photoType
     * @param photoType      new photoType
     */
    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    /**
     * getter for param photoLocation
     * @return photoLocation
     */
    public String getPhotoLocation() {
        return photoLocation;
    }

    /**
     * setter for param photoLocation
     * @param photoLocation     new photoLocation
     */
    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    /**
     * getter for param photoSize
     * @return photoSize
     */
    public Double getPhotoSize() {
        return photoSize;
    }

    /**
     * setter for param photoSize
     * @param photoSize     new photoSize
     */
    public void setPhotoSize(Double photoSize) {
        this.photoSize = photoSize;
    }

    /**
     * getter for param photoWidth
     * @return photoWidth
     */
    public Double getPhotoWidth() {
        return photoWidth;
    }

    /**
     * setter for param photoWidth
     * @param photoWidth        new photoWidth
     */
    public void setPhotoWidth(Double photoWidth) {
        this.photoWidth = photoWidth;
    }

    /**
     * getter for param photoLength
     * @return photoLength
     */
    public Double getPhotoLength() {
        return photoLength;
    }

    /**
     * setter for param photoLength
     * @param photoLength       new photoLength
     */
    public void setPhotoLength(Double photoLength) {
        this.photoLength = photoLength;
    }

    public Bitmap getPic() {
        return pic;
    }
}
