package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;

public class Patient extends user{
    //private String username, password, email, phone;
    public Integer patientID;
    private ArrayList<Problem> problemArrayList;
    private ArrayList<Record> recordArrayList;

    //special function
    private boolean userAuthenticate() {
        return true;
    }
    //end

    public Patient(String username, String password, String email, String phone) {
        super(username, password,email, phone);
    }

    public Integer getPatientID() {
        return patientID;
    }

    public void setPatientID(Integer patientID) {
        this.patientID = patientID;
    }

    public ArrayList<Problem> getProblemArrayList() {
        return problemArrayList;
    }

    public void setProblemArrayList(ArrayList<Problem> problemArrayList) {
        this.problemArrayList = problemArrayList;
    }

    public ArrayList<Record> getRecordArrayList() {
        return recordArrayList;
    }

    public void setRecordArrayList(ArrayList<Record> recordArrayList) {
        this.recordArrayList = recordArrayList;
    }




    public void addProblem(Problem problem) {
        problemArrayList.add(problem);
    }

    public void deleteProblem(Problem problem) {
        problemArrayList.remove(problem);
    }

    public boolean hasProblem(Problem problem){
       for (int i = 0 ; i < problemArrayList.size(); i++){
                if (problem.equals(problemArrayList.get(i))){
                    return true;
                }
            }
            return false;
            //return tweets.contains(tweet);
    }


    public void editProblem(Problem problem, Problem newproblem){
        problemArrayList.remove(problem);
        problemArrayList.add(problem);
    }
}
