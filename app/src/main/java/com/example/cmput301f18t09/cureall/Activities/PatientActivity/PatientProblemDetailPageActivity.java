package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.PatientAdapter.PatientProblemDetailPageAdapter;
import com.example.cmput301f18t09.cureall.ProviderAdapter.ProblemDetailPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;
import java.util.Date;

public class PatientProblemDetailPageActivity extends AppCompatActivity {
    private ImageButton searchButton, photoAnimationButton, addButton, viewProviderCommentButton;
    private EditText titleInput, dateInput, descriptionInput;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem_detail_page);
        titleInput = findViewById(R.id.titleInput);
        dateInput = findViewById(R.id.dateInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        searchButton = findViewById(R.id.searchButton);
        photoAnimationButton = findViewById(R.id.photoAnimationButton);
        addButton = findViewById(R.id.addButton);
        viewProviderCommentButton = findViewById(R.id.viewProviderCommentButton);



        //test samples...
        ArrayList<Record> exampleRecordList = new ArrayList<>();
        exampleRecordList.add(new Record("i am dying", "ehhhhhhhhhhhh", new Date()));
        exampleRecordList.add(new Record("i am dying", "ehhhhhhhhhhhh", new Date()));
        exampleRecordList.add(new Record("i am dying", "ehhhhhhhhhhhh", new Date()));
        exampleRecordList.add(new Record("i am dying", "ehhhhhhhhhhhh", new Date()));
        exampleRecordList.add(new Record("i am dying", "ehhhhhhhhhhhh", new Date()));
        exampleRecordList.add(new Record("i am dying", "ehhhhhhhhhhhh", new Date()));
        exampleRecordList.add(new Record("i am dying", "ehhhhhhhhhhhh", new Date()));
        exampleRecordList.add(new Record("die!!!!!!!!!!!!!!!!!!!!", "ehhhhhhhhhhhh", new Date()));
        //test ends...

        recyclerView = findViewById(R.id.listOfProblems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PatientProblemDetailPageAdapter(exampleRecordList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }
}
