package com.example.cmput301f18t09.cureall;

public class AllKindsOfPhotos {
    private String photoLocation, photoType;
    private Double photoSize, photoWidth, photoLength;

    public AllKindsOfPhotos(String photoLocation, String photoType, Double photoSize, Double photoWidth, Double photoLength) {
        this.photoLocation = photoLocation;
        this.photoType = photoType;
        this.photoSize = photoSize;
        this.photoWidth = photoWidth;
        this.photoLength = photoLength;
    }

    public String getPhotoType() {
        return photoType;
    }

    public void setPhotoType(String photoType) {
        this.photoType = photoType;
    }

    public String getPhotoLocation() {
        return photoLocation;
    }

    public void setPhotoLocation(String photoLocation) {
        this.photoLocation = photoLocation;
    }

    public Double getPhotoSize() {
        return photoSize;
    }

    public void setPhotoSize(Double photoSize) {
        this.photoSize = photoSize;
    }

    public Double getPhotoWidth() {
        return photoWidth;
    }

    public void setPhotoWidth(Double photoWidth) {
        this.photoWidth = photoWidth;
    }

    public Double getPhotoLength() {
        return photoLength;
    }

    public void setPhotoLength(Double photoLength) {
        this.photoLength = photoLength;
    }

}
