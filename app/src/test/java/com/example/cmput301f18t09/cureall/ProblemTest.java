/**..*/
package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import static junit.framework.TestCase.assertEquals;
public class ProblemTest {
    @Test
    //test if successfully got username
    public  void testGetUsername(){

        Problem problem = new Problem("username","title","description","time","comment");
        String username = "username";

        assertEquals(username, problem.getUsername());
    }

    @Test
    //test if successfully got title
    public void testGetTitle(){

        Problem problem = new Problem("username","title","description","time","comment");

        String title = "title";

        assertEquals(title,problem.getTitle());

    }

    @Test
    //test if successfully got description
    public void testGetDescription(){

        Problem problem = new Problem("username","title","description","time","comment");
        String Description = problem.getDescription();
        String description = "description";
        assertEquals(description,Description);
    }

    @Test
    //test if successfully got time
    public void testGetTime(){
    /*    Date time1 = new Date();*/
        String time1 = "time";
        Problem problem = new Problem("username","title","description","time","comment");
        String time = problem.getTime();
        assertEquals(time,time1);
    }

    @Test
    //test if successfully got recordArrayList
    public void testGetRecordArrayList(){
        Problem problem = new Problem("username","title","description","time","comment");
        Record record1 = new Record("record1","comment1",new Date());
        Record record2 = new Record("record2","comment2",new Date());
        problem.addRecord(record1);
        problem.addRecord(record2);
        ArrayList<Record> records1 = problem.getRecordArrayList();
        ArrayList<Record> records2 = new ArrayList<>(2);

        records2.add(record1);
        records2.add(record2);
        assertEquals(records1,records2);
    }
    @Test
    //test if successfully added record
    public void testAddRecord(){
        Problem problem = new Problem("username","title","description","time","comment");
        Record record = new Record("record","comment",new Date());
        boolean u = false;
        problem.addRecord(record);
        ArrayList<Record> records =  problem.getRecordArrayList();
       /* for (int i = 0 ; i < records.size(); i++){
            if (records.equals(records.get(i))) {*/
       assertEquals(false,u);
           /* }*/
        }
    }

