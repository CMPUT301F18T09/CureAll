package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ProblemTest {
    @Test
    public void testGetTitle(){
        Problem problem = new Problem("title","description",new Date(),"");
        String title = problem.getTitle();
        assertEquals(title,"title");
    }
    @Test
    public void testSetTitle(){
        Problem problem = new Problem("title","description",new Date(),"");
        problem.setTitle("newTitle");
        String title = problem.getTitle();
        assertEquals(title,"newTitle");
    }
    @Test
    public void testGetDescription(){
        Problem problem = new Problem("title","description",new Date(),"");
        String Description = problem.getDescription();
        assertEquals(Description,"description");
    }
    @Test
    public void testSetDescription(){
        Problem problem = new Problem("title","description",new Date(),"");
        problem.setDescription("newDescription");
        String description = problem.getDescription();
        assertEquals(description,"newDescription");
    }
    @Test
    public void testGetTime(){
        Date time1 = new Date();
        Problem problem = new Problem("title","description",time1,"");
        Date time = problem.getTime();
        assertEquals(time,time1);
    }
    @Test
    public void testSetTime(){
        Date time1 = new Date();
        Problem problem = new Problem("title","description",time1,"");
        Date time2 = new Date();
        problem.setTime(time2);
        Date time = problem.getTime();
        assertEquals(time,time2);
    }
    @Test
    public void testGetRecordArrayList(){
        Problem problem = new Problem("title","description",new Date(),"");
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
    public void testAddRecord(){
        Problem problem = new Problem("title","description",new Date(),"");
        Record record = new Record("record","comment",new Date());
        problem.addRecord(record);
        ArrayList<Record> records =  problem.getRecordArrayList();
        for (int i = 0 ; i < records.size(); i++){
            if (records.equals(records.get(i))) {
                assertTrue(true);
            }
        }
    }
}
