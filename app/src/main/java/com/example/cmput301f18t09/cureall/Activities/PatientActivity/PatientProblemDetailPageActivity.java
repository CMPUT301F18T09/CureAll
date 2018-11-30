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
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.PhotoGallary;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.SearchActivity;
import com.example.cmput301f18t09.cureall.AllKindsOfPhotos;
import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientProblemDetailPageAdapter;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    private String onstopControl;
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
        addButton = (Button) findViewById(R.id.addButton);
        viewProviderCommentButton = (Button) findViewById(R.id.viewProviderCommentButton);

        Intent intent = getIntent();
        if (intent.getStringExtra("ComeFromPatientMainPage") != null && intent.getStringExtra("ComeFromPatientMainPage").equals("ComeFromPatientMainPage")){
            loadDataFromLocal("PatientMainPageData");
            problemPosition = intent.getIntExtra("ProblemPosition",0);
        }
        else if (intent.getStringExtra("ComeFromCommentViewPage") != null && intent.getStringExtra("ComeFromCommentViewPage").equals("ComeFromCommentViewPage")){
            loadDataFromLocal("saveToLocal");
            getDataFromCommentViewPage();
        }
        else if (intent.getStringExtra("ComeFromRecordAddingPage") != null && intent.getStringExtra("ComeFromRecordAddingPage").equals("ComeFromRecordAddingPage")){
            loadDataFromLocal("saveToLocal");
            getDataFromRecordAddingPage();
        }
        else if (intent.getStringExtra("ComeFromRecordDetailPage") != null && intent.getStringExtra("ComeFromRecordDetailPage").equals("ComeFromRecordDetailPage")){
            loadDataFromLocal("saveToLocal");
            updatePhotosFromRecordDetail();
            //TODO GET THE POSITION OF WHAT WE CLICK ON
            //REPLACE THE RECORD WITH THE NEW ONE
            loadPosition();
            //TODO replace data for locally usage
            records.set(position,record);
            problem.setRecordArrayList(records);

        }
        else if (intent.getStringExtra("ComeFromRecordAddingPageSAVE") != null && intent.getStringExtra("ComeFromRecordAddingPageSAVE").equals("ComeFromRecordAddingPageSAVE")){
            loadDataFromLocal("RecordAddingData");
        }


        //final String username = incomingIntent.getStringExtra("username");
        id = patient.getPatientID();
        //set the content on view
        titleInput.setText(problem.getTitle());
        dateInput.setText(problem.getTime());
        descriptionInput.setText(problem.getDescription());
    }

    /**
     * set adapter listener
     */
    @Override
    protected void onStart() {
        super.onStart();
        recyclerView = findViewById(R.id.listOfProblems);
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
                problem.setTitle(titleInput.getText().toString());
                passDataToPatientMainpage(patient,problems);
                intent.putExtra("ComeFromProblemDetail","ComeFromProblemDetail");
                startActivity(intent);
            }
        });

        //set addButton listener
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProblemDetailPageActivity.this,PatientRecordAddingPageActivity.class);

                passDataToAddingRecordPage(problem,problems,patient,records);
                saveDataToLocal(problems,patient,records,problem);
                intent.putExtra("ComeFromProblemDetail","ComeFromProblemDetail");
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
                intent.putExtra("ComeFromProblemDetail","ComeFromProblemDetail");
                startActivity(intent);
            }
        });
        photoAnimationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO THIS loop here can also help us load pictures
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

    @Override
    protected void onStop() {
        super.onStop();
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
    public void getDataFromCommentViewPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("PatientMainPageData",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("problem",null);
        Type type = new TypeToken<Problem>(){}.getType();
        problem = gson.fromJson(json,type);
    }
    public void getDataFromRecordAddingPage(){
        SharedPreferences sharedPreferences2 = getSharedPreferences("PatientMainPageData",MODE_PRIVATE);
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
    public void updatePhotosFromRecordDetail(){
        //TODO this function may need to update record. we may delete photos
        SharedPreferences sharedPreferences2 = getSharedPreferences("updateRecordPhotos",MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPreferences2.getString("record",null);
        Type type = new TypeToken<Record>(){}.getType();
        record = gson.fromJson(json,type);
    }

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
    public void passDataToCommentViewPage(Problem problem){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(problem);/**save in gson format*/
        editor2.putString("problem",json);
        editor2.apply();
    }
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
    public void passDataToRecordDetailPage(Record record){
        SharedPreferences sharedPreferences2 = getSharedPreferences("ProblemDetailData",MODE_PRIVATE);
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        Gson gson = new Gson();
        String json = gson.toJson(record);
        editor2.putString("record",json);
        editor2.apply();
    }
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
    //TODO IF WE HAVE UPDATE RECORD CONTROLLER, WE DON'T NEED THESE
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
}

