package com.example.shabi;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;


public  class RecordTrackingPhotoTest {
    @Test

    public void SetRecordTrackingPhoto(){
        String photoLocation = "photoSize";
        double photoSize = "354654651316516546516.1641654654654654";
        double photoWidth = "123456789101112131415.1114664646465465465465";
        double photoLenth = "65465423132165465498465.13216546546498498465415132";

        SRT test =new SRT(photoLocation,photoSize,photoWidth,photoLenth);

        assertEquals(SRT.getPhotoLocation(),photoLocation);
        assertEquals(SRT.getphotoSize(),photoSize);
        assertEquals(SRT.getphotoWidth(),photoWidth);
        assertEquals(SRT.getphotoLenth(),photoLenth);


    }





}
