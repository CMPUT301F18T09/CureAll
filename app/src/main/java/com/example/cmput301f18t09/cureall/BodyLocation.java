package com.example.cmput301f18t09.cureall;

import java.security.PublicKey;
import java.util.ArrayList;

public class BodyLocation {
    public String bodyLocationName;
    public AllKindsOfPhotos paperDollPhoto;
    public ArrayList<AllKindsOfPhotos> bodyLocationPhotoArrayList;

    //special fucntion
    public String getLocationFromPaperDollPhoto(){
        return "arm";
    }
    //only deal with one photo addition
    public void addBodyLocationPhoto(){ }
    //end

    public BodyLocation(String bodyLocationName, AllKindsOfPhotos paperDollPhoto, ArrayList<AllKindsOfPhotos> bodyLocationPhotoArrayList) {
        this.bodyLocationName = bodyLocationName;
        this.paperDollPhoto = paperDollPhoto;
        this.bodyLocationPhotoArrayList = bodyLocationPhotoArrayList;
    }

    public String getBodyLocationName() {
        return bodyLocationName;
    }

    public void setBodyLocationName(String bodyLocationName) {
        this.bodyLocationName = bodyLocationName;
    }

    public AllKindsOfPhotos getPaperDollPhoto() {
        return paperDollPhoto;
    }

    public void setPaperDollPhoto(AllKindsOfPhotos paperDollPhoto) {
        this.paperDollPhoto = paperDollPhoto;
    }

    public ArrayList<AllKindsOfPhotos> getBodyLocationPhotoArrayList() {
        return bodyLocationPhotoArrayList;
    }

    public void setBodyLocationPhotoArrayList(ArrayList<AllKindsOfPhotos> bodyLocationPhotoArrayList) {
        this.bodyLocationPhotoArrayList = bodyLocationPhotoArrayList;
    }

}
