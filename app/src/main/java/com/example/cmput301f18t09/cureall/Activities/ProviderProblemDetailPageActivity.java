package com.example.cmput301f18t09.cureall.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.ProviderAdapter.ProblemDetailPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;
import java.util.Date;

public class ProviderProblemDetailPageActivity extends AppCompatActivity {
    private ImageButton backButton, addButton;
    private TextView titleView, dateView, descriptionView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backButton = (ImageButton) findViewById(R.id.backButton);
        addButton = (ImageButton) findViewById(R.id.addButton);
        titleView = (TextView) findViewById(R.id.titleView);
        dateView = (TextView) findViewById(R.id.dateView);
        descriptionView =(TextView) findViewById(R.id.descriptionView);

        setContentView(R.layout.activity_provider_problem_detail_page);

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

        recyclerView = findViewById(R.id.listOfRecords);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProblemDetailPageAdapter(exampleRecordList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


}
