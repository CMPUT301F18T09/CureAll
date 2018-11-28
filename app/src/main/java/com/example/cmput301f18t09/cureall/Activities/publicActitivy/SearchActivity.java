/**
 * Class name: SearchActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Activities.PatientActivity.PatientProblemDetailPageActivity;
import com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderProblemDetailPageActivity;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;

/**
 * This activity deals with searching user case
 * This activity is not finished,and not applied into the app (project 4).
 * But the prototype base code of this activity has been created.
 */
public class SearchActivity extends AppCompatActivity {
    private ImageButton back,startSearching;
    private TextView keywords;
    private String keyword;
    private ProblemController problemController = new ProblemController();
    private ListView testListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        back = findViewById(R.id.back_btn1);
        keywords = findViewById(R.id.input_search);
        startSearching = findViewById(R.id.GO);
        keyword = keywords.getText().toString();
        testListView = findViewById(R.id.testListView);

        /*ArrayList<Problem> problemArrayList;
        problemArrayList = problemController.GetProblemNum(null);
        ArrayAdapter<Problem> adapter = new ArrayAdapter<Problem>(this,R.layout.search_list,problemArrayList);
        testListView.setAdapter(adapter);*/

        String[] test = {"aasfasfa","basfasf","cafasfas","dfsafsfas"};
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.test, test);
        testListView.setAdapter(adapter1);

        //go search
        startSearching.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchActivity.this,SearchResultActivity.class).putExtra("key",keyword));
            }
        });
        //go back
        back.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                String From = getIntent().getStringExtra("From");
                if (From.equals("patient")) {
                    startActivity(new Intent(SearchActivity.this, PatientProblemDetailPageActivity.class));
                }
                else if (From.equals("provider")) {
                    startActivity(new Intent(SearchActivity.this, ProviderProblemDetailPageActivity.class));
                }
            }
        });
    }


}




