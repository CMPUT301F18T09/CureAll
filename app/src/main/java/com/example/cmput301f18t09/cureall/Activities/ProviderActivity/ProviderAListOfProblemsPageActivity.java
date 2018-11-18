package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageButton;

import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProviderAdapter.ProblemListPageAdapter;
import com.example.cmput301f18t09.cureall.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ProviderAListOfProblemsPageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ImageButton backButton;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_alist_of_problems);
        backButton = (ImageButton) findViewById(R.id.backButton);

        //test samples...
        ArrayList<Problem> exampleProblemList = new ArrayList<>();
        exampleProblemList.add(new Problem("one", "feels horrible", "no comment",df.format(new Date()),"no comment" ));
        exampleProblemList.add(new Problem("two", "feels horrible", "no comment",df.format(new Date()),"no comment" ));

        recyclerView = findViewById(R.id.listOfProblems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProblemListPageAdapter(exampleProblemList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
}
