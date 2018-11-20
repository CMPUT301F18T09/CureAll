package com.example.cmput301f18t09.cureall.Activities.ProviderActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.SearchActivity;
import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProviderAdapter.ProblemDetailPageAdapter;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * This page is used to present problems' details
 * With a list of records presented in recycleview
 * Provider can click each records in recycle view to view their details
 */
public class ProviderProblemDetailPageActivity extends AppCompatActivity {
    private ImageButton backButton, addButton,searchButton;
    private TextView titleView, dateView, descriptionView,problem_detail;
    private RecyclerView recyclerView;
    private ProblemDetailPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<Record> exampleRecordList;
    private Patient patient;
    private Problem problem;
    @Override
    /**
     * Create the necessary elements in this page view
     * Some functions can be modified into onStart part, this will be fixed in next project
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_provider_problem_detail_page);
        problem_detail = findViewById(R.id.problem_detail);
        backButton = (ImageButton) findViewById(R.id.backButton);
        addButton = (ImageButton) findViewById(R.id.addButton);
        titleView = (TextView) findViewById(R.id.titleView);
        dateView = (TextView) findViewById(R.id.dateView);
        descriptionView =(TextView) findViewById(R.id.descriptionView);
        searchButton = findViewById(R.id.backButton);

        Intent incomingIntent = getIntent();
        patient = (Patient) getIntent().getSerializableExtra("patient");
        problem = (Problem) getIntent().getSerializableExtra("problem");

        /**
         * The search function has not been finshed yet.
         * It will be finished in next project
         * Here is just the prototype of codes
         */
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
        /**
         * provider can click this button to the page
         * that add comment for each problem.
         */
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Log.i("Patient",patient.getEmail());
                Intent intent = new Intent(ProviderProblemDetailPageActivity.this,ProviderCommentPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem",problem);
                bundle.putSerializable("patient",patient);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        /**
         * Elasticsearch here is used to get information from server
         * and present them into the recycleview
         * It also set the basic information of each problem onto the view, such as problem title, time and description
         */
        //test samples...
        exampleRecordList = new ArrayList<>();
        ElasticSearchController.GetRecordTask getRecordTask = new ElasticSearchController.GetRecordTask();
        ElasticSearchParams params = new ElasticSearchParams(patient.getUsername(),problem.getId());
        getRecordTask.execute(params);

        try {
            List<Record> foundPatient= getRecordTask.get();
            exampleRecordList.addAll(foundPatient);


        } catch (Exception e) {
            Log.i("Error", "Failed to get the user from the async object");
        }

        Log.i("Read","read end");
        Log.i("Read",Integer.toString(exampleRecordList.size()));

        titleView.setText(problem.getTitle());
        dateView.setText(problem.getTime());
        descriptionView.setText(problem.getDescription());
        //test ends...

        recyclerView = findViewById(R.id.listOfProblems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new ProblemDetailPageAdapter(exampleRecordList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    /**
     * Here, when provider can see the ui page
     * It allows the provider to click each record in recycleview, for its details in another page
     */
    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.setOnItemClickListener(new ProblemDetailPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //dont implement
            }
            @Override
            public void onDetailClick(int position){
                Record record= exampleRecordList.get(position);
                //Log.i("Patient",patient.getEmail());
                Intent intent = new Intent(ProviderProblemDetailPageActivity.this,ProviderRecordDetailPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record",record);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

    }


}
