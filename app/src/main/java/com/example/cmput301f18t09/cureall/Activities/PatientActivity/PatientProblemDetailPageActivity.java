package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.cmput301f18t09.cureall.Activities.publicActitivy.SearchActivity;
import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientProblemDetailPageAdapter;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientProblemDetailPageActivity extends AppCompatActivity {
    private Button searchButton, photoAnimationButton, addButton, viewProviderCommentButton;
    private EditText titleInput, dateInput, descriptionInput;
    private RecyclerView recyclerView;
    private PatientProblemDetailPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Problem problem;
    ArrayList<Record> records = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem_detail_page);
        titleInput = findViewById(R.id.titleInput);
        dateInput = findViewById(R.id.dateInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        searchButton = findViewById(R.id.backButton);
        photoAnimationButton = (Button) findViewById(R.id.photoAnimationButton);
        addButton = (Button) findViewById(R.id.addButton);
        viewProviderCommentButton = (Button) findViewById(R.id.viewProviderCommentButton);


        problem = (Problem)getIntent().getSerializableExtra("problem");
//        Intent intentx = getIntent();
//        String problemId = intentx.getStringExtra("problem");
        String id = problem.getId();


        searchButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String[] searchOptions = new String[] {"search by body-location","search by keywords","search by geo-location"};
                AlertDialog.Builder builder = new AlertDialog.Builder(PatientProblemDetailPageActivity.this);
                builder.setSingleChoiceItems(searchOptions,-1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        if (i==0 || i==1) {
                            startActivity(new Intent(PatientProblemDetailPageActivity.this,SearchActivity.class).putExtra("From","patient"));
                        }
                        else {}
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProblemDetailPageActivity.this,PatientRecordAddingPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem",problem);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //test samples...

        ElasticSearchController.GetRecordTask getRecordTask = new ElasticSearchController.GetRecordTask();
        ElasticSearchParams params = new ElasticSearchParams(problem.getUsername(),problem.getId());
        getRecordTask.execute(params);
        try {
            List<Record> foundPatient= getRecordTask.get();
            records.addAll(foundPatient);


        } catch (Exception e) {
            Log.i("Error", "Failed to get the user from the async object");
        }

        recyclerView = findViewById(R.id.listOfProblems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PatientProblemDetailPageAdapter(records);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAdapter.setOnItemClickListener(new PatientProblemDetailPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                //do nothing...
            }

            @Override
            public void onDetailClick(int position) {
                Intent intent = new Intent(PatientProblemDetailPageActivity.this,PatientRecordDetailPageActivity.class);
                Record record = records.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("record", record);
                intent.putExtras(bundle);
//                intent.putExtra("problem",problem.getId());
                startActivity(intent);
            }
        });
    }
}
