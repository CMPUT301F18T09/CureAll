/**
 * Class name: Patient
 *
 * Version: v1.0.0
 *
 * Date: November 1, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;

/**
 * Model class for recording CareProvider
 *
 * @author Ruiqin, Pi
 * @version 1.0.0
 */
public class Patient extends user{
    //private String username, password, email, phone;
    public String patientID;
    private ArrayList<Problem> problemArrayList;
    //private ArrayList<Record> recordArrayList;
    public String doctorID;

    /**
     * userAuthenticate
     * @return True
     */
    //special function
    private boolean userAuthenticate() {
        return true;
    }
    //end

    /**
     * Init for Patient
     * @param username  user name
     * @param password  corresponding password
     * @param email     corresponding email address
     * @param phone     corresponding phone number
     */
    public Patient(String username, String password, String email, String phone) {
        super(username, password,email, phone);
        this.problemArrayList = new ArrayList<>( );
    }

    /**
     * getter for DoctorID
     * @return current doctorID
     */
    public String getDoctorID(){return this.doctorID;}
    /**
     * setter for doctorID
     * @param ID       new doctorID
     */
    public void setDoctorID(String ID){this.doctorID = ID;}

    /**
     * getter for patientID
     * @return current patientID
     */
    public String getPatientID() {
        return this.patientID;
    }

    /**
     * setter for patientID
     * @param patientID new patientID
     */
    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    /**
     * getter for problemArrayList
     * @return current problemArrayList
     */
    public ArrayList<Problem> getProblemArrayList() {
        return problemArrayList;
    }

    /**
     * setter for problemArrayList
     * @param problemArrayList new problemArrayList
     */
    public void setProblemArrayList(ArrayList<Problem> problemArrayList) {
        this.problemArrayList = problemArrayList;
    }

/*
    public ArrayList<Record> getRecordArrayList() {
        return recordArrayList;
    }

    public void setRecordArrayList(ArrayList<Record> recordArrayList) {
        this.recordArrayList = recordArrayList;
    }
*/


    /**
     * add new problem to problem list
     * @param problem problem to be added in problem list
     */
    public void addProblem(Problem problem) {
        problemArrayList.add(problem);
    }

    /**
     * delete a problem from the problem list
     * @param problem   problem to be deleted
     */
    public void deleteProblem(Problem problem) {
        problemArrayList.remove(problem);
    }

    /**
     * To determine if a problem is exist
     * @param problem
     * @return Ture for exist, False for not exist
     */
    public boolean hasProblem(Problem problem){
        for (int i = 0 ; i < problemArrayList.size(); i++){
            if (problem.equals(problemArrayList.get(i))){
                return true;
            }
        }
        return false;
        //return tweets.contains(tweet);
    }


    /**
     * to overwirte an exist problem
     * @param problem       problem to be changed
     * @param newproblem    problem has been changed
     */
    public void editProblem(Problem problem, Problem newproblem){
        problemArrayList.remove(problem);
        problemArrayList.add(newproblem);
    }
}
