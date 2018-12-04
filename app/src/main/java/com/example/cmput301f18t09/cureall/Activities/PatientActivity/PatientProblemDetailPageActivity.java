/**
 * Class name: PatientProblemDetailPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.PhotoGallary;
import com.example.cmput301f18t09.cureall.model.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientProblemDetailPageAdapter;
import com.example.cmput301f18t09.cureall.controller.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.controller.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.model.Record;
import com.example.cmput301f18t09.cureall.controller.RecordController.RecordController;
import com.example.cmput301f18t09.cureall.model.Sync;
import com.example.cmput301f18t09.cureall.model.UserState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * For this activity, user(patient) can view all details for a problem
 */
public class PatientProblemDetailPageActivity extends AppCompatActivity {
    private Button backButton, photoAnimationButton, addButton, viewProviderCommentButton;
    private EditText titleInput, dateInput, descriptionInput;
    private RecyclerView recyclerView;
    private PatientProblemDetailPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Problem problem;
    private Patient patient;
    private ArrayList<Problem> problems;
    private ArrayList<Record> records = new ArrayList<>();
    private Record record;
    private Integer position, problemPosition;
    final int REQUEST_RECORD_ADDING = 1;
    private ArrayList<Bitmap> AllPhotos = new ArrayList<>();
    ProgressDialog progress;
    String id;
    private boolean checker;
    private String onstopControl;
    ScheduledExecutorService service;
    boolean flag;
    private ArrayList<Record> editRecords;
    private String destination;
    /**
     * set listener for all buttons
     * set init value for elements used in this activity
     * (or give reference)
     * including buttons, textviews, problems, records, patients's info
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem_detail_page);
        titleInput = findViewById(R.id.titleInput);
        dateInput = findViewById(R.id.dateInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        backButton = findViewById(R.id.backButton);
        photoAnimationButton = (Button) findViewById(R.id.photoAnimationButton);
        addButton = (Button) findViewById(R.id.record_addButton);
        viewProviderCommentButton = (Button) findViewById(R.id.viewProviderCommentButton);

        Intent intent = getIntent();
        if (intent.getStringExtra("ComeFromPatientMainPage") != null && intent.getStringExtra("ComeFromPatientMainPage").equals("ComeFromPatientMainPage")){
            loadDataFromLocal("PatientMainPageData");
            problemPosition = intent.getIntExtra("ProblemPosition",0);
        }
        else if (intent.getStringExtra("ComeFromCommentViewPage") != null && intent.getStringExtra("ComeFromCommentViewPage").equals("ComeFromCommentViewPage")){
            loadDataFromLocal("saveToLocal");
            loadPosition();
        }
        else if (intent.getStringExtra("ComeFromRecordAddingPageSAVE") != null && intent.getStringExtra("ComeFromRecordAddingPageSAVE").equals("ComeFromRecordAddingPageSAVE")){
            loadDataFromLocal("saveToLocal");
            getDataFromRecordAddingPage();
            loadPosition();
        }
        else if (intent.getStringExtra("ComeFromRecordDetailPage") != null && intent.getStringExtra("ComeFromRecordDetailPage").equals("ComeFromRecordDetailPage")){
            loadDataFromLocal("saveToLocal");
            updatePhotosFromRecordDetail();
            loadPosition();
            //todo 7 why here is empty?
            records.set(position,record);

            UserState current = new UserState(PatientProblemDetailPageActivity.this);
            if (current.getState()){
                RecordController.UpdateRecord(record);
                ArrayList<Record> AllRecords = new ArrayList<>();
                AllRecords = RecordController.loadFromFile(PatientProblemDetailPageActivity.this,"records.txt",records,problem.getUsername());

                Iterator<Record> iter = AllRecords.iterator();

                while (iter.hasNext()) {
                    Record str = iter.next();

                    if (str.getProblemid().equals(problem.getId()) && problem.getState().equals("Online")) {
                        iter.remove();
                    }

                }
                for (Record r:records){
                    if (r.getProblemid().equals(problem.getId()) && problem.getState().equals("Online")){
                        r.setState("Online");
                        AllRecords.add(r);
                    }
                }
                RecordController.saveInFile(PatientProblemDetailPageActivity.this,"records.txt",AllRecords,problem.getUsername());
            }
            else{
                record.setState("EditOffline");
                RecordController.saveInFile(PatientProblemDetailPageActivity.this,"EditRecords.txt",records,problem.getUsername());
            }
        }
        else if (intent.getStringExtra("ComeFromRecordAddingPageSAVE") != null && intent.getStringExtra("ComeFromRecordAddingPageSAVE").equals("ComeFromRecordAddingPageSAVE")){
            loadDataFromLocal("RecordAddingData");

        }


        //final String username = incomingIntent.getStringExtra("username");
        id = patient.getPatientID();
        titleInput.setText(problem.getTitle());
        dateInput.setText(problem.getTime());
        descriptionInput.setText(problem.getDescription());
        //TODO check user state
        UserState current = new UserState(PatientProblemDetailPageActivity.this);
        if (current.getState()){
            checker = true;
            //RecordController.saveInFile(PatientProblemDetailPageActivity.this,"records.txt",records,patient.getUsername());
            Log.i("SYNC", "NOW: ONLINE!");
        }
        if (!current.getState()){
            checker = false;
            Log.i("SYNC","NOW: OFFLINE!");
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                UserState current = new UserState(PatientProblemDetailPageActivity.this);
                if (current.getState()){
                    checker =true;
                    Sync sync = new Sync(PatientProblemDetailPageActivity.this,patient.getUsername());

                    ArrayList<Record> temp = new ArrayList<>();
                    temp = records;
                    flag = false;
                    PatientController.SaveLocalTracker(PatientProblemDetailPageActivity.this,problem.getUsername());
                    editRecords = RecordController.loadFromFile(PatientProblemDetailPageActivity.this,"EditRecords.txt",editRecords,problem.getUsername());
                    for (Record r : records){
                        flag = true;
                        if (r.getState().equals("offline") && problem.getState().equals("Online")){
                            Log.i("SYNC","begin");
                            sync.SyncPushRecord(r,patient.getUsername(),problem,records);
                            sync.UpdateTracker(problem.getUsername());
                            r.setState("Online");
                        }
                        if(r.getState().equals("EditOffline") && problem.getState().equals("Online")){
                            r.setState("Online");
                            RecordController.UpdateRecord(r);
                            editRecords = new ArrayList<>();
                        }
                    }
                    
                    for (Record r: editRecords){
                        if (r.getState().equals("EditOffline") && problem.getState().equals("Online")){
                            r.setState("Online");
                            RecordController.UpdateRecord(r);
                            sync.UpdateTracker(problem.getUsername());
                        }
                    }
                    if (editRecords.size()>0){
                        records = editRecords;
                    }
                    RecordController.saveInFile(PatientProblemDetailPageActivity.this,"EditRecords.txt",new ArrayList<Record>(),problem.getUsername());

                    ArrayList<Record> AllRecords = new ArrayList<>();

                    AllRecords = RecordController.loadFromFile(PatientProblemDetailPageActivity.this,"records.txt",records,problem.getUsername());

                    Iterator<Record> iter = AllRecords.iterator();

                    while (iter.hasNext()) {
                        Record str = iter.next();

                        if (str.getProblemid().equals(problem.getId()) && problem.getState().equals("Online")) {
                            iter.remove();
                        }

                    }

                    for (Record r:records){
                        if (r.getProblemid().equals(problem.getId()) && problem.getState().equals("Online")){
                            r.setState("Online");
                            AllRecords.add(r);
                        }
                    }


                    RecordController.saveInFile(PatientProblemDetailPageActivity.this,"records.txt",AllRecords,problem.getUsername());
                    Log.i("SYNC", "start sync");
                    //todo 6 why we need to use temp?
                    //RecordController.saveInFile(PatientProblemDetailPageActivity.this,"records.txt",temp,problem.getUsername());
                    //records = temp;
                }
                if (!current.getState()){
                    checker = false;
                    Log.i("SYNC","NOW: OFFLINE!");
                }
            }
        };

        SyncCheck(runnable);


    }

    /**
     * set adapter listener
     */
    @Override
    protected void onStart() {
        super.onStart();
        recyclerView = findViewById(R.id.listOfRecords);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PatientProblemDetailPageAdapter(records);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new PatientProblemDetailPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //do nothing...
            }
            @Override
            public void onDetailClick(int position) {
                Intent intent = new Intent(PatientProblemDetailPageActivity.this,PatientRecordDetailPageActivity.class);
                record = records.get(position);
                saveDataToLocal(problems, patient, records,problem);
                passDataToRecordDetailPage(record);
                savePosition(position,problemPosition);
                intent.putExtra("ComeFromProblemDetail","ComeFromProblemDetail");
                destination = "recordDetail";
                service.shutdown();
                startActivity(intent);
            }
        });
        //set backButton listener
        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progress=ProgressDialog.show(PatientProblemDetailPageActivity.this,"Loading",
                        "Loading", true);
                Intent intent = new Intent(PatientProblemDetailPageActivity.this,PatientListOfProblemsPageActivity.class);

                EditProblem();
//TODO
                passDataToPatientMainpage(patient,problems);
                intent.putExtra("ComeFromProblemDetail","ComeFromProblemDetail");
                UserState current = new UserState(PatientProblemDetailPageActivity.this);
                if (!current.getState()){
                    ArrayList<Record> eds = new ArrayList<>();
                    eds = RecordController.loadFromFile(PatientProblemDetailPageActivity.this,"EditRecords.txt",records,problem.getUsername());
                    if (records.size() == eds.size()){
                        RecordController.saveInFile(PatientProblemDetailPageActivity.this,"records.txt",eds,problem.getUsername());
                    }System.out.println(records);
                }
//TODO


                service.shutdown();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startActivity(intent);
                    }
                }, 2000);
            }
        });

        //set addButton listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProblemDetailPageActivity.this,PatientRecordAddingPageActivity.class);

                passDataToAddingRecordPage(problem,problems,patient,records);
                saveDataToLocal(problems,patient,records,problem);
                savePosition(position,problemPosition);
                intent.putExtra("ComeFromProblemDetail","ComeFromProblemDetail");
                service.shutdown();

                startActivity(intent);
            }
        });

        //set viewProviderCommentButton listener
        viewProviderCommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ///String comment = problem.getDoctorcomment();
                //Toast.makeText(PatientProblemDetailPageActivity.this,comment,Toast.LENGTH_SHORT).show();
                Intent intent =new Intent(PatientProblemDetailPageActivity.this,PatientShowProviderCommentPageActivity.class);
                passDataToCommentViewPage(problem);
                saveDataToLocal(problems, patient, records,problem);
                savePosition(position,problemPosition);
                intent.putExtra("ComeFromProblemDetail","ComeFromProblemDetail");
                service.shutdown();


                startActivity(intent);
            }
        });
        photoAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //THIS loop here can also help us load pictures
                onstopControl = "animate";
                for (Record record: records){
                    ArrayList<AllKindsOfPhotos> tempPhotos = record.getRecordTrackingPhotoArrayList();
                    for (AllKindsOfPhotos photo: tempPhotos){
                        String temBitmapSting = photo.getPhotoLocation();
                        try {
                            byte [] encodeByte= Base64.decode(temBitmapSting,Base64.DEFAULT);
                            Bitmap tempbitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                            AllPhotos.add(tempbitmap);
                        } catch(Exception e) {
                            e.getMessage();
                        }
                    }
                }
                Intent intent =new Intent(PatientProblemDetailPageActivity.this,PhotoGallary.class);
                passDataToGallary(AllPhotos);
                saveDataToLocal(problems,patient,records,problem);
                service.shutdown();
                startActivity(intent);
            }
        });



    }

    /**
     * deal with the result for activity done
     * @param requestCode   (build in)
     * @param resultCode    (build in)
     * @param data          (build in)
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_RECORD_ADDING)
        {
            if(resultCode==RESULT_OK)
            {
                problem = (Problem)getIntent().getSerializableExtra("problem");
                records = (ArrayList<Record>)data.getSerializableExtra("records");
                mAdapter.notifyDataSetChanged();
            }
        }
    }

    /**
     * behaviour of activity stops
     */
    @Override
    protected void onStop() {
        super.onStop();
        service.shutdown();
        if (progress != null){
            progress.dismiss();
        }
        if (onstopControl == null){
            finish();
        }
    }
    /**data passing, loading and saving part
     *
     */

    public void getDataFromRecordAddingPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("RecordAddingData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("problem",null);
        String json2 = sharedPreferences2.getString("problems",null);
        String json4 = sharedPreferences2.getString("records",null);
        Type type = new TypeToken<Problem>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Problem>>(){}.getType();
        Type type4 = new TypeToken<ArrayList<Record>>(){}.getType();
        problem = gson.fromJson(json,type);
        problems = gson.fromJson(json2,type2);
        records = gson.fromJson(json4,type4);
    }

    /**
     * synchronize record photos
     */
    public void updatePhotosFromRecordDetail(){
        //TODO this function is necessary once we update the record to server and we pass that record to this page
        //TODO overwrite original data
        //TODO I dont know if we have to use a sync here. or using a local save?
        //TODO The records in this page has already been replaced. If you want to save to local. save the variables "records"
        SharedPreferences sharedPreferences2 = getSharedPreferences("updateRecordPhotos",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("record",null);
        Type type = new TypeToken<Record>(){}.getType();
        record = gson.fromJson(json,type);
    }

    /**
     *
     * @param patient current patient
     * @param problems arrayList of problems
     */
    public void passDataToPatientMainpage(Patient patient, ArrayList<Problem> problems){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(patient);/**save in gson format*/
        String json2 = gson.toJson(problems);
        editor2.putString("patientObject",json);
        editor2.putString("patientProblems",json2);
        editor2.apply();
    }

    /**
     *
     * @param problem current problem
     */
    public void passDataToCommentViewPage(Problem problem){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        editor2.putString("problem",json);
        editor2.apply();
    }

    /**
     *
     * @param problem current problem
     * @param problems patient's problems
     * @param patient current patient
     * @param records patient's all records
     */
    public void passDataToAddingRecordPage(Problem problem, ArrayList<Problem> problems, Patient patient, ArrayList<Record> records){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        String json2 = gson.toJson(problems);
        String json3 = gson.toJson(patient);
        String json4 = gson.toJson(records);
        editor2.putString("problem",json);
        editor2.putString("problems",json2);
        editor2.putString("patient",json3);
        editor2.putString("records",json4);
        editor2.apply();
    }

    /**
     *
     * @param record the specific record
     */
    public void passDataToRecordDetailPage(Record record){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(record);
        editor2.putString("record",json);
        editor2.apply();
    }

    /**
     *
     * @param allPhotos record photos
     */
    public void passDataToGallary(ArrayList<Bitmap> allPhotos){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(allPhotos);
        editor2.putString("allPhotos",json);
        editor2.apply();
    }


    /**
     * future save function can start from here
     */
    public void saveDataToLocal(ArrayList<Problem> problems, Patient patient, ArrayList<Record> records, Problem problem){
        SharedPreferences sharedPreferences2 = getSharedPreferences("saveToLocal",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problems);
        String json2 = gson.toJson(patient);
        String json3 = gson.toJson(records);
        String json4 = gson.toJson(problem);
        editor2.putString("problems",json);
        editor2.putString("patient",json2);
        editor2.putString("records",json3);
        editor2.putString("problem",json4);
        editor2.apply();
    }

    /**
     *
     * @param name file name
     */
    public void loadDataFromLocal(String name){
        SharedPreferences sharedPreferences2 = getSharedPreferences(name,MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("problems",null);
        String json2 = sharedPreferences2.getString("patient",null);
        String json3 = sharedPreferences2.getString("records",null);
        String json4 = sharedPreferences2.getString("problem",null);
        Type type = new TypeToken<ArrayList<Problem>>(){}.getType();
        Type type2 = new TypeToken<Patient>(){}.getType();
        Type type3 = new TypeToken<ArrayList<Record>>(){}.getType();
        Type type4 = new TypeToken<Problem>(){}.getType();
        problems = gson.fromJson(json,type);
        patient = gson.fromJson(json2,type2);
        records = gson.fromJson(json3,type3);
        problem = gson.fromJson(json4,type4);
    }
    //A use less function
    public void savePosition(Integer position, Integer problemPosition){
        SharedPreferences sharedPreferences2 = getSharedPreferences("Position",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(position);
        String json2 = gson.toJson(problemPosition);
        editor2.putString("position",json);
        editor2.putString("problemPosition",json2);
        editor2.apply();
    }

    /**
     * get position
     */
    public void loadPosition(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("Position",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("position",null);
        String json2 = sharedPreferences2.getString("problemPosition",null);
        Type type = new TypeToken<Integer>(){}.getType();
        Type type2 = new TypeToken<Integer>(){}.getType();
        position = gson.fromJson(json,type);
        problemPosition = gson.fromJson(json2,type2);
    }


    /**
     *
     * @param runnable (build in)
     */
    public void SyncCheck(Runnable runnable){
        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable,2,3, TimeUnit.SECONDS);
    }

    /**
     * edit problem
     */
    //TODO new funct
    public void EditProblem(){
        problem.setTitle(titleInput.getText().toString());
        problem.setDescription(descriptionInput.getText().toString());
        problem.setState("Edit");
        problems.set(problemPosition,problem);
        ProblemController.saveInFile(PatientProblemDetailPageActivity.this,"problems.txt",problems,problem.getUsername());

        Log.i("Save","end");
    }
}
