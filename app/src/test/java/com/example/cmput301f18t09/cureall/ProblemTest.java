package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.Date;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ProblemTest {

    public void testGetTitle(){
        Problem problem = new Problem("title","description",new Date());
        String title = problem.getTitle();
        assertEquals(title,"title");
    }

    public void testSetTitle(){
        Problem problem = new Problem("title","description",new Date());
        problem.setTitle("newTitle");
        String title = problem.getTitle();
        assertEquals(title,"newTitle");
    }

    public void testGetDescription(){
        Problem problem = new Problem("title","description",new Date());
        String Description = problem.getDescription();
        assertEquals(Description,"description");
    }

    public void testSetDescription(){
        Problem problem = new Problem("title","description",new Date());
        problem.setDescription("newDescription");
        String description = problem.getDescription();
        assertEquals(description,"newDescription");
    }

    public void testGetTime(){
        Date time1 = new Date();
        Problem problem = new Problem("title","description",time1);
        Date time = problem.getTime();
        assertEquals(time,time1);
    }

    public void testSetTime(){
        Date time1 = new Date();
        Problem problem = new Problem("title","description",time1);
        Date time2 = new Date();
        problem.setTime(time2);
        Date time = problem.getTime();
        assertEquals(time,time2);
    }

    public void testAddRecord(){
        Problem problem = new Problem("title","description",new Date());
        Record record = new Record("record","comment",new Date());
        problem.addRecord(record);
        assertTrue(problem.hasRecord(record));
    }
}
