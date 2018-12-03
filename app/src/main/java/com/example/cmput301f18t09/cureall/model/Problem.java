/**
 * Class name: Problem
 *
 * Version: v1.0.0
 *
 * Date: November 1, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.model;

import java.util.ArrayList;
import java.io.Serializable;
/**
 * Model class for recording CareProvider
 *
 * @author Ruiqin, Pi
 * @version 1.0.0
 */
public class Problem implements Serializable{
    public String title, description,username;
    public String time;
    private ArrayList<Record> recordArrayList;
    private String doctorcomment;
    private String Id;
    private String State;

    /**
     * add a record to problem
     * @param record    record to be added
     */
    //special fucntion
    //since getter and setter only deal with arraylist of records
    //we create a function to deal with single record
    public void addRecord(Record record){
        this.recordArrayList.add(record);
    }
    //end

    /**
     * Init for a problem
     * @param username      username
     * @param title         title for new problem
     * @param description   description for new problem
     * @param time          problem creating time
     * @param comment       comment for new problem
     */
    public Problem(String username,String title, String description, String time,String comment) {
        this.username = username;
        //this.title = title;
        this.setTitle(title);
        //this.description = description;
        this.setDescription(description);
        this.time = time;
        //this.doctorcomment = comment;
        this.setDoctorcomment(comment);
        this.Id = null;
    }

    /**
     * getter for recordArrayList
     * @return current recordArrayList
     */
    public ArrayList<Record> getRecordArrayList() {
        return recordArrayList;
    }

    /**
     * setter for recordArrayList
     * @param recordArrayList new recordArrayList
     */
    public void setRecordArrayList(ArrayList<Record> recordArrayList) {
        this.recordArrayList = recordArrayList;
    }

    /**
     * getter for Id
     * @return Id
     */
    public String getId(){return this.Id;}

    /**
     * setter for id
     * @param id new id
     */
    public void setId(String id){this.Id = id;}

    /**
     * getter for username
     * @return current username
     */
    public String getUsername(){return this.username;}

    /**
     * getter for doctorcomment
     * @return current doctorcomment
     */
    public String getDoctorcomment(){
        if (this.doctorcomment == null){
            return this.doctorcomment;
        }else{
            return Encryption.decrypt(this.doctorcomment);
        }
    }

    /**
     * setter for doctorcomment
     * @param comment new doctorcomment
     */
    public void setDoctorcomment(String comment){
        if (comment == null){
            this.doctorcomment = comment;
        }else{
            this.doctorcomment = Encryption.encrypt(comment);
        }
    }

    /**
     * getter for title
     * @return current title
     */
    public String getTitle() {
        if (title == null){
            return title;
        }else{
            return Encryption.decrypt(title);
        }
    }

    /**
     * setter for title
     * @param title new title
     */
    public void setTitle(String title) {
        if (title == null){
            this.title = title;
        }else{
            this.title = Encryption.encrypt(title);
        }
    }

    /**
     * getter for description
     * @return current description
     */
    public String getDescription() {
        if (description == null){
            return description;
        }else{
            return Encryption.decrypt(description);
        }
    }

    /**
     * setter for description
     * @param description new description
     */
    public void setDescription(String description) {
        if (description == null){
            this.description = description;
        }else{
            this.description = Encryption.encrypt(description);
        }
    }

    /**
     * getter for time
     * @return time
     */
    public String getTime() {
        return time;
    }

    /**
     * setter for time (update time)
     * @param time new time
     */
    public void setTime(String time) {
        this.time = time;
    }
    public void setState(String State){this.State = State;}
    public String getState() {return this.State;}
}
