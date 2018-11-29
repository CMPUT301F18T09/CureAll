/**
 * Class name: CareProvider
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
public class CareProvider extends user {
    public String DoctorID;
    public String patientName;

    private ArrayList<Patient> patientArrayList;
    //special fucntion

    /**
     * getter for param patientArrayList
     * @return patientArrayList
     */
    public ArrayList<Patient> getPatientArrayList() {
        return patientArrayList;
    }

    /**
     * getter for a single patient
     */
    public String getPaitent(){return patientName; }

    /**
     * function for adding comment for a problem
     * @param problem       the problem to be commented
     * @param comment       the comment to be added
     */
    public void addComment(Problem problem,String comment){
        problem.setDoctorcomment(comment);
    }

    /**
     * function to add a patient to a care provider
     * @param patientName      patient name
     */
    public void addPatientByName(Patient patient,String patientName){
        patient.setPatientID(patientName) ;
    }

    /**
     * getter for param DoctorID
     * @return DoctorID
     */
    public String getDoctorID() {
        return DoctorID;
    }

    /**
     * setter for param DoctorID
     * @param DoctorID new DoctorID
     */
    public void setDoctorID(String DoctorID) {
        this.DoctorID = DoctorID;
    }

    //end

    /**
     * Init for class CareProvider
     * @param username      Careprovider name (account)
     * @param password      corresponding password
     * @param email         corresponding email
     * @param phone         corresponding phone
     */
    public CareProvider(String username, String password, String email, String phone) {
        super(username,email, phone);
    }


    /**
     * setter for patientArrayList
     * @param patientArrayList new patientArrayList
     */
    public void setPatientArrayList(ArrayList<Patient> patientArrayList) {
        this.patientArrayList = patientArrayList;
    }


}
