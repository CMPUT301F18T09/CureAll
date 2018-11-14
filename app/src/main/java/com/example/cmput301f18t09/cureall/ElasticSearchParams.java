package com.example.cmput301f18t09.cureall;

public class ElasticSearchParams {
    Patient patient;
    String username;
    String DoctorComment;
    Record record;
    Problem problem;
    Integer num;
    String id;

    public ElasticSearchParams(String username,Problem problem,String id){
        this.username = username;
        this.problem = problem;
        this.id = id;
    }

    public ElasticSearchParams(Integer num,Problem problem){
        this.num = num;
        this.problem = problem;
    }


}
