package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;
import java.io.Serializable;

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

    public BodyLocation(String bodyLocationName, ArrayList<String> bodyLocationPhotoArrayList) {
        this.bodyLocationName = bodyLocationName;
        this.photoPaths = bodyLocationPhotoArrayList;
    }

    public String getBodyLocationName() {
        return bodyLocationName;
    }

    public void setBodyLocationName(String bodyLocationName) {
        this.bodyLocationName = bodyLocationName;
    }


    public ArrayList<String> getBodyLocationPhotoArrayList() {
        return photoPaths;
    }

    public void setBodyLocationPhotoArrayList(ArrayList<String> bodyLocationPhotoArrayList) {
        this.photoPaths = bodyLocationPhotoArrayList;
    }

}
