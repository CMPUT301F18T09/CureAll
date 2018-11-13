package com.example.cmput301f18t09.cureall.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

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
        addPatientButton = (ImageButton) findViewById(R.id.addPatientButton);
        searchProblemRecordButton =(ImageButton) findViewById(R.id.searchProblemRecordButton);
        setContentView(R.layout.activity_provider_main_page);

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

    }
}
