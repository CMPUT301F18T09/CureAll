/**
 * Class name: ElasticSearchParams
 *
 * Version: v1.0.0
 *
 * Date: November 1, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.GeneralElasticsearch;

import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.model.Record;

import java.util.ArrayList;
import java.util.Date;

/**
 * Model class for recording CareProvider
 *
 * @author Ruiqin, Pi
 * @version 1.0.0
 */
public class ElasticSearchParams {
    Patient patient;
    String username;
    String DoctorComment;
    Record record;
    Problem problem;
    Integer num;
    String id;
    ArrayList<String> patients;
    String problemid;
    Date lastPulltime;

    /**
     * one Init for class ElasticSearchParams
     * @param username  the username used in elastic search
     * @param problem   searched problem
     * @param id        id
     */
    public ElasticSearchParams(String username, Problem problem, String id){
        this.username = username;
        this.problem = problem;
        this.id = id;

    }

    /**
     * one init for class ElasticSearchParams
     * @param num
     * @param problem
     * @param Patientid
     */
    public ElasticSearchParams(Integer num, Problem problem, String Patientid){
        this.num = num;
        this.problem = problem;
        this.id = id;
    }

    /**
     * one init for class ElasticSearchParams
     * @param username
     * @param patients
     */
    public ElasticSearchParams(String username, ArrayList<String> patients){
        this.username = username;
        this.patients = patients;
    }

    /**
     * one init for class ElasticSearchParams
     * @param username
     * @param record
     */
    // delete record
    public ElasticSearchParams(String username, Record record, String ID, String identify){
        this.username = username;
        this.record = record;
        if (identify.equals("delete")){
            this.id = ID;
        }else{
            this.problemid = ID;
        }

    }
    // add record
    /*public ElasticSearchParams(String username, Record record, String problemid){
        this.username = username;
        this.record = record;

    }*/

    /**
     * one init for class ElasticSearchParams
     * @param username
     * @param problemid
     */
    public ElasticSearchParams(String username, String problemid){
        this.username = username;
        this.problemid = problemid;
    }
    public ElasticSearchParams(String username, Date lastPulltime){
        this.username = username;
        this.lastPulltime = lastPulltime;
    }
    public Date GetTime(){
        return lastPulltime;
    }
/*  may use later
    public ElasticSearchParams(String problemid)
    {
        this.problemid = problemid;
    }*/


}
