package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;

public class ElasticSearchParams {
    Patient patient;
    String username;
    String DoctorComment;
    Record record;
    Problem problem;
    Integer num;
    String id;
    ArrayList<String> patients;

    public ElasticSearchParams(String username, Problem problem, String id){
        this.username = username;
        this.problem = problem;
        this.id = id;

    }

    public ElasticSearchParams(Integer num, Problem problem, String Patientid){
        this.num = num;
        this.problem = problem;
        this.id = id;
    }

    public ElasticSearchParams(String username, ArrayList<String> patients){
        this.username = username;
        this.patients = patients;
    }


}