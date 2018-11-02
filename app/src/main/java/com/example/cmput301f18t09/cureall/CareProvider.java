package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;

public class CareProvider {

    private String username, password, email, phone;
    private ArrayList<Patient> patientArrayList;
    //special fucntion
    public ArrayList<Patient> getPatientArrayList() {
        return patientArrayList;
    }
    private Patient getPaitent(){
        Patient patient = new Patient("1","2","3","4",1);
        return patient;
    }
    private void addComment(Patient Problem){ }
    private void addPatientByName(){ }
    //end

    public CareProvider(String username, String password, String email, String phone) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
    }



    public void setPatientArrayList(ArrayList<Patient> patientArrayList) {
        this.patientArrayList = patientArrayList;
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

    private boolean userAuthenticate() {
        return false;
    }

}
