/**..*/
package com.example.cmput301f18t09.cureall;

import com.example.cmput301f18t09.cureall.model.AllKindsOfPhotos;

import org.junit.Test;
import static org.junit.Assert.*;


public  class TracePhotoTest {
    @Test

    public void testSetRecordTrackingPhoto(){
        String photoLocation = "/local/301/abc.png";
        double photoSize = 354654651316516546516.1641654654654654;
        double photoWidth = 123456789101112131415.1114664646465465465465;
        double photoLength = 65465423132165465498465.13216546546498498465415132;
        /**
        * Init for function testSetRecordTrackingPhoto
        * @param photoLocation     location of photo
        * @param photoSize         size of photo
         * @param photoWidth        width of photo
        * @param photoLength       length of photo
        */
        AllKindsOfPhotos test =new AllKindsOfPhotos(photoLocation,"1",photoSize,photoWidth,photoLength);
        //check if everything match
        assertTrue("photo check two photos are same", test.getPhotoLocation().equals(photoLocation));
        assertTrue("photo size equal",test.getPhotoSize().equals(photoSize));
        assertTrue("Photo Width equal",test.getPhotoWidth().equals(photoWidth));
        assertTrue("Photo length equl",test.getPhotoLength().equals(photoLength));
    }

}
