package com.example.cmput301f18t09.cureall;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;

import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.Record;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Patient;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

public class ProblemTest {
    @Test
    public  void testGetUsername(){

        Problem problem = new Problem("username","title","description","time","comment");
        String username = "one";

        assertEquals(username, problem.getUsername());
    }

    @Test
    public void testGetTitle(){

        Problem problem = new Problem("username","title","description","time","comment");

        String title = "title";

        assertEquals(title,problem.getTitle());

    }

    @Test
    public void testGetDescription(){

        Problem problem = new Problem("username","title","description","time","comment");
        String Description = problem.getDescription();
        String description = "description";
        assertEquals(description,Description);
    }

    @Test
    public void testGetTime(){
    /*    Date time1 = new Date();*/
        String time1 = "time";
        Problem problem = new Problem("username","title","description","time","comment");
        String time = problem.getTime();
        assertEquals(time,time1);
    }

    @Test
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
    public void testAddRecord(){
        Problem problem = new Problem("username","title","description","time","comment");
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
