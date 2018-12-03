package com.example.cmput301f18t09.cureall.model;

import android.content.Context;
import android.util.Log;

import com.example.cmput301f18t09.cureall.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;

import java.io.File;
import java.util.ArrayList;

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
        File rootDir1 = new File(rootDir,"userinfo.txt");
        File rootDir2 = new File(rootDir,"tracker.txt");
        File rootDir3 = new File(rootDir,"problems.txt");
        File rootDir4 = new File(rootDir,"records.txt");
        boolean exists1 = rootDir1.exists();
        boolean exists2 = rootDir2.exists();
        boolean exists3 = rootDir3.exists();
        boolean exists4 = rootDir4.exists();
        if (exists && exists1 && exists2 && exists3 && exists4){
            return true;
        }
        else{
            return false;
        }

        //return  exists;
    }

    public void SyncUSer(Patient patient){
      //  patient.setLastLoginTime();
      //  patient.setLastPushTime();
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

        ArrayList<Problem> AllProblems = new ArrayList<>();
        AllProblems = ProblemController.loadFromFile(context,"problems.txt",AllProblems,username);
        RecordController rController = new RecordController();
        for (Problem p : AllProblems){
              ArrayList<Record> temp = new ArrayList<>();
              temp = rController.GetRecordNum(username,p.getId());
              records.addAll(temp);
        }

        RecordController.saveInFile(context,"records.txt",records,username);
        //ElasticSearchController.SyncAllRecordTask syncRecordTask = new ElasticSearchController.SyncAllRecordTask();
        //syncRecordTask.execute(username);

        // {
         //   List<Record> foundrecord= syncRecordTask.get();
        //    records.addAll(foundrecord);
        //} catch (Exception e) {
        //    Log.i("Error", "Failed to get the user from the async object");
        //}

        Log.i("Read","read end");
    }

    public void SyncDeleteProblem(Problem problem, String username, ArrayList<Problem> problems){
        ElasticSearchParams params = new ElasticSearchParams("",problem,problem.getId());
        ElasticSearchController.DeleteProblemTask deleteProblemTask = new ElasticSearchController.DeleteProblemTask();
        deleteProblemTask.execute(params);
        UpdateTracker(username);
    }


    public void SyncPushProblem(Problem problem, String username, ArrayList<Problem> problems){
        String checker = problem.getId();
        problem.setId(null);

        Problem temp = problem;
        ElasticSearchParams param = new ElasticSearchParams(0, problem, username);
        problem.setState("Online");

        ElasticSearchController.AddProblemTask addproblemTask = new ElasticSearchController.AddProblemTask();
        addproblemTask.execute(problem);
        try{
            temp = addproblemTask.get();
        }catch(Exception e){
            Log.i("Problem","Something wrong happend at saveProblem function in PatientProblemAddingPage");
        }
        Log.i("ID",temp.getId());



        ArrayList<Record> records = new ArrayList<>();
        records = RecordController.loadFromFile(context,"records.txt",records,username);

        for (Record r: records){
            if(r.getProblemid().equals(checker) && r.getState().equals("offline")){
                SyncPushRecord(r,username,problem,records);
                r.setState("Online");
            }
        }

        UpdateTracker(username);
        Log.i("SYNC","push end");
    }

    public void SyncPushRecord(Record record, String username, Problem problem, ArrayList<Record> records){

        record.setID(null);

        record.setProblemid(problem.getId());

        Record temp = record;
        record.setState("Online");

        ElasticSearchParams param = new ElasticSearchParams(username,record,problem.getId(),"add");
        ElasticSearchController.AddRecordTask addRecordTask = new ElasticSearchController.AddRecordTask();

        addRecordTask.execute(param);
        try{
            temp = addRecordTask.get();
            Log.i("ID",temp.getID());
        }catch(Exception e){
            Log.i("Problem","Something wrong happend at saveProblem function in PatientProblemAddingPage");
        }

        UpdateTracker(username);



    }

    public void SyncProblem(String username,Problem problem){


        problem.setState("Online");

        ElasticSearchController.ChangeProblemTask changeProblemTask = new ElasticSearchController.ChangeProblemTask();
        changeProblemTask.execute(problem);


        UpdateTracker(username);
    }

    public void UpdateTracker(String username){
        ElasticSearchController.OnlineTask onlineTask = new ElasticSearchController.OnlineTask();
        onlineTask.execute(username);
    }

}
