package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;

public class CareProvider extends user {

    private ArrayList<Patient> patientArrayList;
    //special fucntion
    public ArrayList<Patient> getPatientArrayList() {
        return patientArrayList;
    }

    private void getPaitent(){

    }
    public void addComment(Problem problem,String comment){
        problem.setDoctorcomment(comment);
    }
    public void addPatientByName(String username){

    }


    //end

    public CareProvider(String username, String password, String email, String phone) {
        super(username, password,email, phone);
    }



    public void setPatientArrayList(ArrayList<Patient> patientArrayList) {
        this.patientArrayList = patientArrayList;
    }


}