package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;
import java.io.Serializable;

public class Problem implements Serializable{
    public String title, description,username;
    public String time;
    private ArrayList<Record> recordArrayList;
    private String doctorcomment;
    private String Id;

    //special fucntion
    //since getter and setter only deal with arraylist of records
    //we create a function to deal with single record
    public void addRecord(Record record){

    }
    //end

    public Problem(String username,String title, String description, String time,String comment) {
        this.username = username;
        this.title = title;
        this.description = description;
        this.time = time;
        this.doctorcomment = comment;
    }
    public ArrayList<Record> getRecordArrayList() {
        return recordArrayList;
    }

    public void setRecordArrayList(ArrayList<Record> recordArrayList) {
        this.recordArrayList = recordArrayList;
    }
    public String getId(){return this.Id;}
/*    public ElasticSearchParams getParamID(){
        new ElasticSearchParams()
    }*/
    public void setId(String id){this.Id = id;}
    public String getUsername(){return this.username;}
    public String getDoctorcomment(){return this.doctorcomment;}
    public void setDoctorcomment(String comment){this.doctorcomment = comment;}
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
