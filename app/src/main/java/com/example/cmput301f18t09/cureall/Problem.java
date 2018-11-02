package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;
import java.util.Date;

public class Problem {
    public String title, description;
    public Date time;
    private ArrayList<Record> recordArrayList;
    private ArrayList<String> comments;

    //special fucntion
    //since getter and setter only deal with arraylist of records
    //we create a function to deal with single record
    public void addRecord(Record record){}
    //end

    public Problem(String title, String description, Date time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }
    public ArrayList<Record> getRecordArrayList() {
        return recordArrayList;
    }

    public ArrayList<String> getCommentArraylist() {
        return comments;
    }

    public void setRecordArrayList(ArrayList<Record> recordArrayList) {
        this.recordArrayList = recordArrayList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }



}
