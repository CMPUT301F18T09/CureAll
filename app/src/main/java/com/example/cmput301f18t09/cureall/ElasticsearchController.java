package com.example.cmput301f18t09.cureall;

import java.util.ArrayList;

public class ElasticsearchController {

    public static ElasticsearchController client(){return null;};

    public Patient getUsername(String username){
        Patient patient = new Patient("username","password","email","phone");
        return patient;
    }

    public CareProvider getusername(String username){
        CareProvider care = new CareProvider("username","password","email","phone");
        return care;
    }

    public String addProblem(String username,Problem problem){return null;}

    public String addRecord(String username, Record record){return null;}

    public ArrayList<Problem> getProblemArrayList(String username){return null;}

    public ArrayList<Record> getRecordArrayList(String username, Problem problem){return null;}

    public ArrayList<String> getLocation(GeoLocation location){return null;}

    //public ArrayList<String> search(String keyWord) {return null;}
}

