package com.example.cmput301f18t09.cureall.Activities.publicActitivy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.R;

import java.util.ArrayList;

public class ShowSearchResultActivity extends AppCompatActivity {

    // The using of this activity is to display searched result by using list view
    private ListView search_result_list;
    private String type;
    private ArrayList<Problem> problems;
    private ArrayList<String> records;
    private ArrayList<String> msg_list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_search_result);
        msg_list = new ArrayList<>();
        search_result_list = (ListView)findViewById(R.id.search_result_list);
        type = getIntent().getStringExtra("type");
        if(type.equals("problem"))
        {
            problems =(ArrayList<Problem>) getIntent().getSerializableExtra("problems");
            for(Problem each:problems)
            {
                msg_list.add(each.getTitle());
            }
            SearchAdapter arrayAdapter = new SearchAdapter(this,R.layout.search_each_result_in_listview, msg_list);
            search_result_list.setAdapter(arrayAdapter);
        }
        else {
            records = (ArrayList<String>)getIntent().getSerializableExtra("records");
            //for(Record each:records)
           // {
               // msg_list.add(each.getTitle());
           // }
            SearchAdapter arrayAdapter = new SearchAdapter(this,R.layout.search_each_result_in_listview, records);
            search_result_list.setAdapter(arrayAdapter);
        }
    }
}
