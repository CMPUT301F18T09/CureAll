package com.example.cmput301f18t09.cureall;

import android.content.Context;
import android.util.Log;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemAddingPageActivity;
import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientSignUpActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.UserLoginActivity;
import com.example.cmput301f18t09.cureall.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;

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


    public void SyncPushProblem(Problem problem,String username,ArrayList<Problem> problems){
        problem.setId(null);

        Problem temp = problem;
        ElasticSearchParams param = new ElasticSearchParams(0, problem, username);

        ElasticSearchController.AddProblemTask addproblemTask = new ElasticSearchController.AddProblemTask();
        addproblemTask.execute(problem);
        try{
            temp = addproblemTask.get();
        }catch(Exception e){
            Log.i("Problem","Something wrong happend at saveProblem function in PatientProblemAddingPage");
        }
        Log.i("ID",temp.getId());

        Log.i("SYNC",temp.getId());

        problems.remove(problem);
        problems.add(temp);
        ProblemController.saveInFile(context,"problems.txt",problems,username);

    }

    public void SyncPushRecord(Record record,String username,Problem problem,ArrayList<Record> records){
        record.setID(null);

        Record temp = record;
        ElasticSearchParams param = new ElasticSearchParams(username,record,problem.getId());
        ElasticSearchController.AddRecordTask addRecordTask = new ElasticSearchController.AddRecordTask();

        addRecordTask.execute(param);
        try{
            temp = addRecordTask.get();
        }catch(Exception e){
            Log.i("Problem","Something wrong happend at saveProblem function in PatientProblemAddingPage");
        }
        Log.i("ID",temp.getID());


        records.remove(record);
        records.add(record);
        RecordController.saveInFile(context,"problems.txt",records,username);

    }

    public void UpdateTracker(String username){
        ElasticSearchController.OnlineTask onlineTask = new ElasticSearchController.OnlineTask();
        onlineTask.execute(username);
    }
}
