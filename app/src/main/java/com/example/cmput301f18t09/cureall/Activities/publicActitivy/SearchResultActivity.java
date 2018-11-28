/**
 * Class name: SearchResultActivity
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * this activity is used to show user's search result
 * This activity is not finished and not applied into app(project 4)
 * The prototype of this activity and base code has been created.
 */
public class SearchResultActivity extends AppCompatActivity {

    private ImageButton back;
    private ListView results;
    private String keyword;
    private ProblemController problemController = new ProblemController();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        back = findViewById(R.id.back_btn2);
        results = findViewById(R.id.result_list);

        keyword=getIntent().getExtras().getString("key");
        List<String> keywordList = new ArrayList<String>(Arrays.asList(keyword.split(" ")));

        ArrayList<Problem> problemArrayList;
        ArrayList<Problem> resultArraylist = new ArrayList<>();
        problemArrayList = problemController.GetProblemNum(null);

        for (int i=0; problemArrayList.get(i)!=null;i++) {
            ArrayList<Record> recordArrayList;
            recordArrayList=problemArrayList.get(i).getRecordArrayList();
            String ptitle=problemArrayList.get(i).title;
            String description=problemArrayList.get(i).description;
            for (int i1=0; keywordList.get(i1)!=null;i1++) {
                if (ptitle.contains(keyword) || description.contains(keyword)) {
                    resultArraylist.add(problemArrayList.get(i));
                } else {
                    for (int j = 0; recordArrayList.get(j) != null; j++) {
                        String title = recordArrayList.get(j).title;
                        String comment = recordArrayList.get(j).comment;
                        if (title.contains(keyword) || comment.contains(keyword)) {
                            resultArraylist.add(problemArrayList.get(i));
                            break;
                        }
                    }
                }
            }
        }
        ArrayAdapter<Problem> adapter = new ArrayAdapter<Problem>(this,R.layout.search_list,resultArraylist);
        results.setAdapter(adapter);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SearchResultActivity.this,SearchActivity.class));
            }
        });
    }
}
