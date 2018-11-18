package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.ProviderAdapter.ProviderMainPageAdapter;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

public class ProviderMainPageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ImageButton addPatientButton, searchProblemRecordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_main_page);
        addPatientButton = (ImageButton) findViewById(R.id.addPatientButton);
        searchProblemRecordButton =(ImageButton) findViewById(R.id.searchProblemRecordButton);

        //test samples...
        ArrayList<Patient> examplePatientList = new ArrayList<>();
        examplePatientList.add(new Patient("caonimage", "123456","123@qq.com","7805432233"));
        examplePatientList.add(new Patient("nimabide", "123456","123@qq.com","7805432233"));
        examplePatientList.add(new Patient("caonimage", "123456","123@qq.com","7805432233"));
        examplePatientList.add(new Patient("caonimage", "123456","123@qq.com","7805432233"));
        examplePatientList.add(new Patient("caonimage", "123456","123@qq.com","7805432233"));
        examplePatientList.add(new Patient("caonimage", "123456","123@qq.com","7805432233"));
        examplePatientList.add(new Patient("caonimage", "123456","123@qq.com","7805432233"));
        examplePatientList.add(new Patient("caonimage", "123456","123@qq.com","7805432233"));
        examplePatientList.add(new Patient("caonimage", "123456","123@qq.com","7805432233"));
        examplePatientList.add(new Patient("shabilanzi", "123456","123@qq.com","7805432233"));
        //test ends...

        recyclerView = findViewById(R.id.listOfPatients);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProviderMainPageAdapter(examplePatientList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        searchProblemRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProviderMainPageActivity.this,"Leo you should implement this button for searching..", Toast.LENGTH_SHORT).show();
            }
        });
        addPatientButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(ProviderMainPageActivity.this,"Add patient button..", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
