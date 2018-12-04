/**
 * Class name: PatientListOfProblemsPageActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.SearchActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.ViewLocationOnMapActivity;
import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchController;
import com.example.cmput301f18t09.cureall.model.GeoLocation;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientProblemListPageAdapter;
import com.example.cmput301f18t09.cureall.PatientController.PatientController;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.model.Record;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;
import com.example.cmput301f18t09.cureall.model.Sync;
import com.example.cmput301f18t09.cureall.model.UserState;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * For this activity, user(patient) will view a list of problems
 */
public class PatientListOfProblemsPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button searchButton, problemAddingButton;
    private RecyclerView recyclerView;
    private PatientProblemListPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Problem> problems;
    private ProblemController problemController = new ProblemController();
    private ArrayList<Record> records;
    private RecordController recordController= new RecordController();
    private ArrayList<Integer> numberOfRecords = new ArrayList<>();
    String username;
    String user_email;
    String phone;
    String id;
    Patient patient;
    boolean checker;
    final int REQUEST_PROBLEM_ADDING = 1;
    ScheduledExecutorService service;
    ProgressDialog progress;
    ArrayList<Problem> deleteProblem;
    private String control = "default";

    //drawer..
    private DrawerLayout drawer;
    private Dialog MyDialog;
    private ImageView qr_code_imageView;
    /**
     * get basic info for patient, and set reference for buttons
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list_of_problems_page);
        searchButton =  findViewById(R.id.searchButton);
        problemAddingButton =  findViewById(R.id.problemAddingButton);
        /**GET DATA FROM DIFFERENT ACTIVITIES
         * evaluate first left then right!!!
         */
        Intent intent = getIntent();
        if (intent.getStringExtra("ComeFromLogin") != null && intent.getStringExtra("ComeFromLogin").equals("ComeFromLogin")){
            getDataFromLogin();
        }
        else if (intent.getStringExtra("ComeFromAddingPage") != null && intent.getStringExtra("ComeFromAddingPage").equals("ComeFromAddingPage")){
            getDataFromProblemAdding();
            loadPatientObjectFromLocal();
        }else if(intent.getStringExtra("ComeFromProblemDetail") != null && intent.getStringExtra("ComeFromProblemDetail").equals("ComeFromProblemDetail")){
            getDataFromProblemDetail();

            //will pass patient and problems back
        }

        username = patient.getUsername();
        user_email = patient.getEmail();
        phone = patient.getPhone();
        id = patient.getPatientID();
        ArrayList<Record> AllRecords = new ArrayList<>();
        //TODO load all records' geolocation and put them into a map
        //TODO OFFLINE CAN'T SEE THE NUM OF RECORDS!!!!!
       for (Problem problem : problems){
            //ArrayList<Record> tempRecords = recordController.GetRecordNum(username,problem.getId());
            //Integer number = tempRecords.size();
            AllRecords = RecordController.loadFromFile(PatientListOfProblemsPageActivity.this,"records.txt",AllRecords,username);
            Integer num = 0;
            for (Record r:AllRecords){
                if(r.getProblemid().equals(problem.getId())){
                    num = num+ 1;
                }
            }


            numberOfRecords.add(num);
        }
        //numberOfRecords.add(0);
        //start sync...
        UserState current = new UserState(PatientListOfProblemsPageActivity.this);
        if (current.getState()){
            checker = true;
            Log.i("SYNC", "NOW: ONLINE!");
        }
        if (!current.getState()){
            checker = false;
            Log.i("SYNC","NOW: OFFLINE!");
        }

    }

    /**
     * set listener for search button
     * user can active 3 search methods: by body location, by key words, by geolocation
     * set listener for adapter
     */
    @Override
    protected void onStart() {
        super.onStart();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Log.i("SYNC","run");
                UserState current = new UserState(PatientListOfProblemsPageActivity.this);

                if (current.getState()){

                    checker =true;
                    Log.i("SYNC","Now sync end");

                    Sync sync = new Sync(PatientListOfProblemsPageActivity.this,username);

                    for (Problem p : problems){
                        Log.i("SYNC","Now in PUSH loop");
                        if (p.getState().equals("Offline")){
                            Log.i("SYNC","push problem");
                            sync.SyncPushProblem(p,username,problems);

                            PatientController.SaveLocalTracker(PatientListOfProblemsPageActivity.this,username);
                            continue;
                        }
                        if (p.getState().equals("Edit")){
                            Log.i("SYNC","edit problem");
                            sync.SyncProblem(username,p);

                            PatientController.SaveLocalTracker(PatientListOfProblemsPageActivity.this,username);
                        }

                    }

                    deleteProblem = ProblemController.loadFromFile(PatientListOfProblemsPageActivity.this,"Deleteproblems.txt",deleteProblem,username);

                    for (Problem p1: deleteProblem){
                        Log.i("SYNC","Now DELETE in");
                        if (p1.getState().equals("DeleteOffline")){
                            Log.i("SYNC","delete problem");
                            sync.SyncDeleteProblem(p1,username,problems);

                            recordController.DelteRecords(p1.getId(),p1,username,PatientListOfProblemsPageActivity.this);

                            sync.UpdateTracker(username);

                            PatientController.SaveLocalTracker(PatientListOfProblemsPageActivity.this,username);
                            continue;
                        }
                    }
                    //todo 2 there is not update here to update edit problem
                    ArrayList<Problem> Allproblems = problemController.GetProblemNum(username);
                    ProblemController.saveInFile(PatientListOfProblemsPageActivity.this,"problems.txt",Allproblems,username);

                    Allproblems = new ArrayList<>();
                    ProblemController.saveInFile(PatientListOfProblemsPageActivity.this,"Deleteproblems.txt",Allproblems,username);


                }

                else{
                    checker = false;
                    Log.i("SYNC","NOW: OFFLINE!");
                }
            }
        };
        SyncCheck(runnable);

        recyclerView = findViewById(R.id.listOfProblems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PatientProblemListPageAdapter(problems,numberOfRecords);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //get navigation contents and set them by yourselfs..
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        //test content
        TextView name = header.findViewById(R.id.nav_name);
        TextView email = header.findViewById(R.id.nav_email);
        TextView phoneNumber = header.findViewById(R.id.nav_phone);
        ImageView headScript = header.findViewById(R.id.nav_headScript);
        name.setText("ID: "+username);
        email.setText("Email:"+user_email);
        phoneNumber.setText("Phone: "+ phone);


        /**
         * click to edit email address
         */
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder custom = new android.app.AlertDialog.Builder(PatientListOfProblemsPageActivity.this);;
                custom.setCancelable(true);
                custom.setNegativeButton("Change Email", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog.Builder msg_editor = new AlertDialog.Builder(PatientListOfProblemsPageActivity.this);
                        final EditText user_input = new EditText(PatientListOfProblemsPageActivity.this);
                        msg_editor.setView(user_input);
                        msg_editor.setCancelable(true);
                        msg_editor.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final String msg = user_input.getText().toString();
                                patient.setEmail(msg);
                                Toast.makeText(PatientListOfProblemsPageActivity.this,"new email: "+msg,Toast.LENGTH_SHORT).show();
                                patient.setPatientID(null);
                                ElasticSearchController.ChangeInfoTask changeInfoTask = new ElasticSearchController.ChangeInfoTask();
                                changeInfoTask.execute(patient);
                                email.setText("Email: "+msg);
                            }
                        });
                        msg_editor.show();
                    }
                });
                custom.show();
            }
        });

        /**
         * click to edit phone number
         */
        phoneNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.app.AlertDialog.Builder custom = new android.app.AlertDialog.Builder(PatientListOfProblemsPageActivity.this);;
                custom.setCancelable(true);
                custom.setNegativeButton("Change Phone", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int i) {
                        AlertDialog.Builder msg_editor = new AlertDialog.Builder(PatientListOfProblemsPageActivity.this);
                        final EditText user_input = new EditText(PatientListOfProblemsPageActivity.this);
                        msg_editor.setView(user_input);
                        msg_editor.setCancelable(true);
                        msg_editor.setPositiveButton("Finish", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                final String msg = user_input.getText().toString();
                                patient.setPhone(msg);
                                Toast.makeText(PatientListOfProblemsPageActivity.this,"new phone: "+msg,Toast.LENGTH_SHORT).show();
                                ElasticSearchController.ChangeInfoTask changeInfoTask = new ElasticSearchController.ChangeInfoTask();
                                changeInfoTask.execute(patient);
                                phoneNumber.setText("Phone: "+msg);
                            }
                        });
                        msg_editor.show();
                    }
                });
                custom.show();
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {                AlertDialog.Builder custom = new AlertDialog.Builder(PatientListOfProblemsPageActivity.this);
                custom.setCancelable(true);

                custom.setPositiveButton("Problem", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ;                   Intent intent = new Intent(PatientListOfProblemsPageActivity.this,SearchActivity.class);
                        intent.putExtra("search method","problem");
                        startActivity(intent);

                    }
                });
                custom.setNegativeButton("Record", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(PatientListOfProblemsPageActivity.this,SearchActivity.class);
                        intent.putExtra("search method","record");
                        startActivity(intent);


                    }
                });
                custom.show();
            }
        });

        mAdapter.setOnItemClickListener(new PatientProblemListPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {}
            @Override
            public void onDetailClick(int position){
                Intent intent = new Intent(PatientListOfProblemsPageActivity.this,PatientProblemDetailPageActivity.class);
                Problem problem = problems.get(position);

                //UserState currentState = new UserState(PatientListOfProblemsPageActivity.this);
                ArrayList<Record> Allrecords = new ArrayList<>();
                records = new ArrayList<>();
                Allrecords = RecordController.loadFromFile(PatientListOfProblemsPageActivity.this,"records.txt",Allrecords,username);
                Log.i("State","the current problemid is"+ problem.getId());
                for (Record r : Allrecords){
                    if (r.getProblemid().equals(problem.getId())){
                        records.add(r);
                    }}
                /*if (currentState.getState()) {
                    records = recordController.GetRecordNum(username, problem.getId());
                }
                else{
                    ArrayList<Record> Allrecords = new ArrayList<>();
                    records = new ArrayList<>();
                    Allrecords = RecordController.loadFromFile(PatientListOfProblemsPageActivity.this,"records.txt",Allrecords,username);
                    Log.i("State","the current problemid is"+ problem.getId());
                    for (Record r : Allrecords){
                        if (r.getProblemid().equals(problem.getId())){
                            records.add(r);
                        }
                    }
                }*/
                service.shutdown();
                passDataToProblemDetail(problem,records,problems,patient);
                intent.putExtra("ComeFromPatientMainPage", "ComeFromPatientMainPage");
                // problem position
                intent.putExtra("ProblemPosition",position);
                startActivity(intent);
            }
            @Override
            public void onDeleteClick(int position) {
                //add local storage funct.
                Problem p = problems.get(position);
                Problem temp = p;
                String id = p.getId();

                PatientController.SaveLocalTracker(PatientListOfProblemsPageActivity.this,username);




                ProblemController.DelteProblem(problems,position,username,PatientListOfProblemsPageActivity.this);

                mAdapter.notifyDataSetChanged();

                UserState userState = new UserState(PatientListOfProblemsPageActivity.this);
                if (!userState.getState()){
                    Log.i("Delete","OnDeleteClick");
                    temp.setState("DeleteOffline");
                    ArrayList<Problem> tempproblems = problems;
                    tempproblems.add(temp);
                    deleteProblem = ProblemController.loadFromFile(PatientListOfProblemsPageActivity.this,"Deleteproblems.txt",deleteProblem,username);
                    if (deleteProblem.size()==0){
                        ProblemController.saveInFile(PatientListOfProblemsPageActivity.this,"Deleteproblems.txt",tempproblems,username);
                    }
                    else{
                        deleteProblem.remove(problems.get(position));
                        deleteProblem.add(temp);
                        ProblemController.saveInFile(PatientListOfProblemsPageActivity.this,"Deleteproblems.txt",deleteProblem,username);
                    }
                    //ProblemController.saveInFile(PatientListOfProblemsPageActivity.this,"Deleteproblems.txt",tempproblems,username);
                    tempproblems.remove(temp);

                    deleteProblem = ProblemController.loadFromFile(PatientListOfProblemsPageActivity.this,"Deleteproblems.txt",deleteProblem,username);
                    PatientController.SaveLocalTracker(PatientListOfProblemsPageActivity.this,username);

                }
                else{
                    //TODO delete correspond records

                    recordController.DelteRecords(id,p,username,PatientListOfProblemsPageActivity.this);
                    Sync sync = new Sync (PatientListOfProblemsPageActivity.this,username);
                    sync.UpdateTracker(username);

                }
                //PatientController.SaveLocalTracker(PatientListOfProblemsPageActivity.this,username);
            }
        });



        //for end
        problemAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientListOfProblemsPageActivity.this,PatientProblemAddingPageActivity.class);
                service.shutdown();
                intent.putExtra("ComeFromPatientMainPage", "ComeFromPatientMainPage");
                passDataToProblemAdding(username,problems);
                savePatientObjectToLocal(patient);
                startActivity(intent);
            }
        });


    }


    /**
     * a customized dialog
     */
    public void MyCustomAlertDialog(){
        MyDialog = new Dialog(PatientListOfProblemsPageActivity.this);
        MyDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        MyDialog.setContentView(R.layout.qr_code_dialog);
        MyDialog.setTitle("My Custom Dialog");
        qr_code_imageView = (ImageView)MyDialog.findViewById((R.id.qr_code));

        //qr_code_imageView.set
        MyDialog.show();
    }
    //allow you click on navigation menus
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sync:
                Toast.makeText(this,"sync info with internet",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mapOfRecords:
                //TODO ADD A ACTIVITY TO VIEW A all geolocation on map
                UserState current = new UserState(PatientListOfProblemsPageActivity.this);
                if (current.getState()){
                    Toast.makeText(this,"Here is a map of all records",Toast.LENGTH_SHORT).show();
                    ArrayList<Record> AllRecords = new ArrayList<>();
                    ArrayList<Record> realAllRecords = new ArrayList<>();
                    for (Problem problem : problems){
                        //ArrayList<Record> tempRecords = recordController.GetRecordNum(username,problem.getId());
                        //Integer number = tempRecords.size();
                        AllRecords = RecordController.loadFromFile(PatientListOfProblemsPageActivity.this,"records.txt",AllRecords,username);
                        Integer num = 0;
                        for (Record r:AllRecords){
                            if(r.getProblemid().equals(problem.getId())){
                                realAllRecords.add(r);
                            }
                        }
                    }

                    Intent map = new Intent(PatientListOfProblemsPageActivity.this, ViewLocationOnMapActivity.class);
                    Bundle geoLocation = new Bundle();
                    ArrayList<GeoLocation> geoLocationArrayList = new ArrayList<>();
                    for (Record r : realAllRecords){
                        if (r.getGeoLocation() != null){
                            geoLocationArrayList.add(r.getGeoLocation());
                        }
                    }
                    geoLocation.putSerializable("GeolocationList",geoLocationArrayList);
                    control = "geo";
                    map.putExtras(geoLocation);
                    startActivity(map);


                }
                if (!current.getState()) {
                    Toast.makeText(this,"Here is a map of all records",Toast.LENGTH_SHORT).show();
                }

                break;
            /**
             * QR scanner
             */
            case R.id.qr_code:
                Toast.makeText(this,"Scan QR code",Toast.LENGTH_SHORT).show();
                String text = username.trim();
                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                BitMatrix bitMatrix = null;
                try {
                    bitMatrix = multiFormatWriter.encode(text,BarcodeFormat.QR_CODE,300,300);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                MyCustomAlertDialog();
                qr_code_imageView.setImageBitmap(bitmap);
                break;
        }
        return true;
    }

    //if user press back option
    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    /**
     * behaviour of activity stops
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (progress != null){
            progress.dismiss();
        }
        service.shutdown();
        if(control.equals("geo")){

        }else{
            finish();
        }

    }

    /**data passing, loading and saving part
     *
     */
    public void getDataFromLogin(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("LoginData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("patientObject",null);
        String json2 = sharedPreferences2.getString("patientProblems",null);
        Type type = new TypeToken<Patient>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Problem>>(){}.getType();
        patient = gson.fromJson(json,type);
        problems = gson.fromJson(json2,type2);

    }

    /**
     * get data from ProblemAdding
     */

    public void getDataFromProblemAdding(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("problemAddingData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("username",null);
        String json2 = sharedPreferences2.getString("patientProblems",null);
        Type type = new TypeToken<String>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Problem>>(){}.getType();
        username = gson.fromJson(json,type);
        //may don't need it
        problems = gson.fromJson(json2,type2);
        //mAdapter.notifyDataSetChanged();
    }

    /**
     * get data from ProblemDetail
     */
    public void getDataFromProblemDetail(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("patientObject",null);
        String json2 = sharedPreferences2.getString("patientProblems",null);
        Type type = new TypeToken<Patient>(){}.getType();
        Type type2 = new TypeToken<ArrayList<Problem>>(){}.getType();
        patient = gson.fromJson(json,type);
        problems = gson.fromJson(json2,type2);
    }

    /**
     *
     * @param username username
     * @param problems patient's problems
     */
    public void passDataToProblemAdding(String username, ArrayList<Problem> problems){
        SharedPreferences sharedPreferences2 = getSharedPreferences("PatientMainPageData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(username);/**save in gson format*/
        String json2 = gson.toJson(problems);
        editor2.putString("username",json);
        editor2.putString("patientProblems",json2);
        editor2.apply();
    }

    /**
     *
     * @param problem current problem
     * @param records problem's records
     * @param problems patient's problems
     * @param patient current user
     */
    public void passDataToProblemDetail(Problem problem, ArrayList<Record> records, ArrayList<Problem> problems, Patient patient){
        SharedPreferences sharedPreferences2 = getSharedPreferences("PatientMainPageData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        String json2 = gson.toJson(records);
        String json3 = gson.toJson(problems);
        String json4 = gson.toJson(patient);
        editor2.putString("problem",json);
        editor2.putString("records",json2);
        editor2.putString("problems",json3);
        editor2.putString("patient",json4);
        editor2.apply();
    }
    /**
     * future save function can start from here
     */
    public void savePatientObjectToLocal(Patient patient){
        SharedPreferences sharedPreferences2 = getSharedPreferences("saveToLocal",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(patient);
        editor2.putString("patient",json);
        editor2.apply();
    }

    /**
     * load patient file form local
     */
    public void loadPatientObjectFromLocal(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("saveToLocal",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("patient",null);
        Type type = new TypeToken<Patient>(){}.getType();
        patient = gson.fromJson(json,type);
    }

    /**
     *
     * @param runnable (build in)
     */
    public void SyncCheck(Runnable runnable){

        service = Executors.newSingleThreadScheduledExecutor();
        service.scheduleAtFixedRate(runnable,2,2, TimeUnit.SECONDS);
    }

}
