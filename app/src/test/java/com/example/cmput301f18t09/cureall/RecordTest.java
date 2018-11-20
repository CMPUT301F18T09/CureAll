/**..*/
package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import static junit.framework.TestCase.assertEquals;

public class RecordTest {
    @Test
    //test if successfully got title
    public void testGetTitle(){
        Record record = new Record("title","comment",new Date());
        String title = record.getTitle();
        assertEquals(title,"title");
    }
    @Test
    //test if successfully add title
    public void testSetTitle(){
        Record record = new Record("title","comment",new Date());
        record.setTitle("newTitle");
        String title = record.getTitle();
        assertEquals(title,"newTitle");
    }
    @Test
    //test if successfully got comment
    public void testGetComment(){
        Record record = new Record("title","comment",new Date());
        String comment = record.getComment();
        assertEquals(comment,"comment");
    }
    @Test
    //test if successfully added comment
    public void testSetComment(){
        Record record = new Record("title","comment",new Date());
        record.setComment("newComment");
        String comment = record.getComment();
        assertEquals(comment,"newComment");
    }
    @Test
    //test if successfully got time
    public void testGetTime(){
        Date time1 = new Date();
        Record record = new Record("title","comment",time1);
        Date time = record.getTime();
        assertEquals(time,time1);
    }
    @Test
    //test if successfully added time
    public void testSetTime(){
        Date time1 = new Date();
        Record record = new Record("title","comment",time1);
        Date time2 = new Date();
        record.setTime(time2);
        Date time = record.getTime();
        assertEquals(time,time2);
    }
    @Test
    //test if successfully added bodyLocation
    public void testAddBodyLocation(){
        ArrayList<String> photoPaths = new ArrayList<String>( );
        BodyLocation bodyLocation = new BodyLocation( "head",photoPaths );
        Record record = new Record( "","",new Date(  ) );
        record.setBodyLocation(bodyLocation);
        assertEquals(record.getBodyLocation(),bodyLocation);
    }
}
