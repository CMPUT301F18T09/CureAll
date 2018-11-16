package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.publicActitivy.SearchActivity;
import com.example.cmput301f18t09.cureall.ProviderAdapter.ProblemDetailPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;
import java.util.Date;

public class ProviderProblemDetailPageActivity extends AppCompatActivity {
    private ImageButton backButton, addButton,searchButton;
    private TextView titleView, dateView, descriptionView;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_problem_detail_page);
        backButton = (ImageButton) findViewById(R.id.searchButton);
        addButton = (ImageButton) findViewById(R.id.addButton);
        titleView = (TextView) findViewById(R.id.titleView);
        dateView = (TextView) findViewById(R.id.dateView);
        descriptionView =(TextView) findViewById(R.id.descriptionView);
        searchButton = findViewById(R.id.searchButton);

        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] searchOptions = new String[] {"search by body-location","search by keywords","search by geo-location"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ProviderProblemDetailPageActivity.this);
                builder.setSingleChoiceItems(searchOptions,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i==0 || i==1) {
                            startActivity(new Intent(ProviderProblemDetailPageActivity.this,SearchActivity.class).putExtra("From","provider"));
                        }
                        else {}
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

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
        mAdapter = new ProblemDetailPageAdapter(exampleRecordList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }


}
