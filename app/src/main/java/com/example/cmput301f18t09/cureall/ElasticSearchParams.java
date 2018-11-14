package com.example.cmput301f18t09.cureall;

public class ElasticSearchParams {
    Patient patient;
    String username;
    String DoctorComment;
    Record record;
    Problem problem;
    Integer num;

    public ElasticSearchParams(String username,Problem problem){
        this.username = username;
        this.problem = problem;
    }

    public ElasticSearchParams(Integer num,Problem problem){
        this.num = num;
        this.problem = problem;
    }
}
