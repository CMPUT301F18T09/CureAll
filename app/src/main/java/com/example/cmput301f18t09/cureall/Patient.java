package com.example.cureall.cureall;

import java.util.ArrayList;

public class Patient {
    private String username, password, email, phone;
    public Integer patientID;
    private ArrayList<Problem> problemArrayList;
    private ArrayList<Record> recordArrayList;

    //special function
    private void addProblem(){}
    private void deleteProblem(){}
    private void editProblem(){}
    private boolean userAuthenticate() {
        return true;
    }
    //end

    public Patient(String username, String password, String email, String phone, Integer patientID) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.patientID = patientID;
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


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
