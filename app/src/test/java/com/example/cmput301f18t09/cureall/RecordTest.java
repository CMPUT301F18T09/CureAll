package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import com.example.cmput301f18t09.cureall.Record;
import com.example.cmput301f18t09.cureall.BodyLocation;
import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import static junit.framework.TestCase.assertEquals;

public class RecordTest {
    @Test
    public void testGetTitle(){
        Record record = new Record("title","comment",new Date());
        String title = record.getTitle();
        assertEquals(title,"title");
    }
    @Test
    public void testSetTitle(){
        Record record = new Record("title","comment",new Date());
        record.setTitle("newTitle");
        String title = record.getTitle();
        assertEquals(title,"newTitle");
    }
    @Test
    public void testGetComment(){
        Record record = new Record("title","comment",new Date());
        String comment = record.getComment();
        assertEquals(comment,"comment");
    }
    @Test
    public void testSetComment(){
        Record record = new Record("title","comment",new Date());
        record.setComment("newComment");
        String comment = record.getComment();
        assertEquals(comment,"newComment");
    }
/*    @Test
    public void testGetTime(){
        Date time1 = new Date();
        Record record = new Record("title","comment",time1);
        Date time = record.getTime();
        assertEquals(time,time1);
    }
    @Test
    public void testSetTime(){
        Date time1 = new Date();
        Record record = new Record("title","comment",time1);
        Date time2 = new Date();
        record.setTime(time2);
        Date time = record.getTime();
        assertEquals(time,time2);
    }*/
    @Test
    public void testAddBodyLocation(){
        String body = "Arm";
        ArrayList<AllKindsOfPhotos> photos = new ArrayList<AllKindsOfPhotos>();
        BodyLocation bodylocation = new BodyLocation(BodyLocation.getBodyLocationName(), photos);
        Record record = new Record("", "", new Date());
        record.setBodyLocation(bodylocation);
        assertEquals(record.getBodyLocation(),bodylocation);
    }
}
