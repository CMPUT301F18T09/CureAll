package com.example.cmput301f18t09.cureall;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;

public class RecordTest {

    public void testGetTitle(){
        Record record = new Record("title","comment",new Date());
        String title = record.getTitle();
        assertEquals(title,"title");
    }

    public void testSetTitle(){
        Record record = new Record("title","comment",new Date());
        record.setTitle("newTitle");
        String title = record.getTitle();
        assertEquals(title,"newTitle");
    }

    public void testGetComment(){
        Record record = new Record("title","comment",new Date());
        String comment = record.getComment();
        assertEquals(comment,"comment");
    }

    public void testSetComment(){
        Record record = new Record("title","comment",new Date());
        record.setComment("newComment");
        String comment = record.getComment();
        assertEquals(comment,"newComment");
    }

    public void testGetTime(){
        Date time1 = new Date();
        Record record = new Record("title","comment",time1);
        Date time = record.getTime();
        assertEquals(time,time1);
    }

    public void testSetTime(){
        Date time1 = new Date();
        Record record = new Record("title","comment",time1);
        Date time2 = new Date();
        record.setTime(time2);
        Date time = record.getTime();
        assertEquals(time,time2);
    }
}
