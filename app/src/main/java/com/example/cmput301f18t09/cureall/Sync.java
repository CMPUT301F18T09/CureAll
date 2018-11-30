package com.example.cmput301f18t09.cureall;

import android.content.Context;
import android.util.Log;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemAddingPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientSignUpActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.UserLoginActivity;
import com.example.cmput301f18t09.cureall.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Sync {
    private String username;
    private Context context;

    public Sync (Context context, String username){
        this.username = username;
        this.context = context;
    }
    public boolean Check (){//TODO test whether this device has a record of this user or not
        File rootDir = new File(context.getFilesDir(),username);
        boolean exists = rootDir.exists();

        return  exists;
    }

    public void SyncUSer(Patient patient){
        patient.setLastLoginTime();
        patient.setLastPushTime();
        ArrayList<Patient> patients = new ArrayList<>();
        //TODO make a new dir belongs to this user
        patients.add(patient);
        PatientController.saveInFile(context,"userinfo.txt",patients,patient.getUsername());
    }

    public void SyncAllProblem(ArrayList<Problem> problems, String username) {
        ProblemController.saveInFile(context, "problems.txt", problems, username);
    }

    public void SyncAllRecord(String username){
        ArrayList<Record> records = new ArrayList<Record>();
        ElasticSearchController.SyncAllRecordTask syncRecordTask = new ElasticSearchController.SyncAllRecordTask();
        syncRecordTask.execute(username);

        try {
            List<Record> foundrecord= syncRecordTask.get();
            records.addAll(foundrecord);
        } catch (Exception e) {
            Log.i("Error", "Failed to get the user from the async object");
        }

        Log.i("Read","read end");
    }

    public void SyncPartProblem(Patient patient) {
        ArrayList<Problem> problems = new ArrayList<Problem>();
        ElasticSearchParams params = new ElasticSearchParams(patient.getUsername(), patient.getLastLoginTime());

        ElasticSearchController.SyncPartProblemTask syncPartProblemTask = new ElasticSearchController.SyncPartProblemTask();
        syncPartProblemTask.execute(params);
        try{
            List<Problem> foundProblems = syncPartProblemTask.get();
            problems.addAll(foundProblems);
        }catch (Exception e){
            Log.i("Error","Failed to get the part problem for sync");
        }

        ArrayList<Problem> localproblems = new ArrayList<>();
        localproblems = ProblemController.loadFromFile(context, "problems.txt", problems, username);

        HashSet<Problem> map = new HashSet<>();
        for (Problem p : localproblems){
            map.add(p);
        }
        for (Problem i: problems){
            if (!map.contains(i)){
                map.add(i);
            }
        }


    }
}
