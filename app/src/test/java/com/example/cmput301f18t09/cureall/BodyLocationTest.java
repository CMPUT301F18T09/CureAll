/**..*/
package com.example.cmput301f18t09.cureall;

import com.example.cmput301f18t09.cureall.model.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.model.BodyLocation;

import org.junit.Test;
import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
/**
 * Model class for recording CareProvider
 *
 * @author Xianhang,Li
 * @version 1.0.0
 */
public  class BodyLocationTest {


    @Test

    public void testAddBodyLocationPhoto() {
        String bodyLocationName = "arm";
        String photoLocation = "www.putianyiyuan.com";
        String photoType = "1";
        double photoSize = 354654651316516546516.1641654654654654;
        double photoWidth = 123456789101112131415.1114664646465465465465;
        double photoLength = 65465423132165465498465.13216546546498498465415132;
        /**
         * Init for function testAddBodylocationPhoto
         * @param bodyLocationName  name of bodylocation
         * @param photoLocation     location of photo
         * @param photoType         type of photo
         * @param photoSize         size of photo
         * @param photoWidth        width of photo
         * @param photoLength       length of photo
         */
        ArrayList<AllKindsOfPhotos> testPhoto = new ArrayList<>();
        testPhoto.add(new AllKindsOfPhotos(photoLocation,photoType,photoSize,photoWidth,photoLength));
        //add bodylocationPhotos for testing
        ArrayList<String> bodyLocationPhotoArrayList = new ArrayList<String>(  );
        bodyLocationPhotoArrayList.add(photoLocation);
        BodyLocation body = new BodyLocation(bodyLocationName,  bodyLocationPhotoArrayList);
        //check if validate
        assertEquals(body.getBodyLocationPhotoArrayList().get(0),testPhoto.get(0).getPhotoLocation());


    }

}
