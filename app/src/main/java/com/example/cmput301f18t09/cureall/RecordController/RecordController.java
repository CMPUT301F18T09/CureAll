package com.example.cmput301f18t09.cureall.RecordController;

import com.example.cmput301f18t09.cureall.Record;

import java.util.Date;

public class RecordController {

    public Record getNewRecord(String title, String comment, Date date ){
        Record record = new Record(title,comment,date);
        return record;
    }
    public void viewRecord(){

    }
}
