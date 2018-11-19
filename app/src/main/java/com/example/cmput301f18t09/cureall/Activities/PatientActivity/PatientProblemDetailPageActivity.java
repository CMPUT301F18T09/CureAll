package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
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
    private Button backButton, photoAnimationButton, addButton, viewProviderCommentButton;
    private EditText titleInput, dateInput, descriptionInput;
    private RecyclerView recyclerView;
    private PatientProblemDetailPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private Problem problem;
    private ArrayList<Problem> problems;
    ArrayList<Record> records = new ArrayList<>();
    final int REQUEST_RECORD_ADDING = 1;

    String username;
    String user_email;
    String phone;
    String id;
    String pw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem_detail_page);
        titleInput = findViewById(R.id.titleInput);
        dateInput = findViewById(R.id.dateInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        backButton = findViewById(R.id.backButton);
        photoAnimationButton = (Button) findViewById(R.id.photoAnimationButton);
        addButton = (Button) findViewById(R.id.addButton);
        viewProviderCommentButton = (Button) findViewById(R.id.viewProviderCommentButton);

        Intent incomingIntent = getIntent();
        //final String username = incomingIntent.getStringExtra("username");
        username = incomingIntent.getStringExtra("username");
        user_email = incomingIntent.getStringExtra("email");
        phone = incomingIntent.getStringExtra("phone");
        id = incomingIntent.getStringExtra("id");
        pw = incomingIntent.getStringExtra("password");

        problem = (Problem)getIntent().getSerializableExtra("problem");
        records = (ArrayList<Record>)getIntent().getSerializableExtra("records");
        problems = (ArrayList<Problem>)getIntent().getSerializableExtra("problems");


        backButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(PatientProblemDetailPageActivity.this,PatientListOfProblemsPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem", problem);
                bundle.putSerializable("problems",problems);
                intent.putExtras(bundle);
                intent.putExtra("username", username);
                intent.putExtra("email",user_email);
                intent.putExtra("phone",phone);
                intent.putExtra("id",id);
                intent.putExtra("password",pw);
                startActivity(intent);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientProblemDetailPageActivity.this,PatientRecordAddingPageActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem",problem);
                bundle.putSerializable("records", records);
                intent.putExtras(bundle);
                startActivityForResult(intent,REQUEST_RECORD_ADDING);
            }
        });





    }

    @Override
    protected void onStart() {
        super.onStart();
        recyclerView = findViewById(R.id.listOfProblems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PatientProblemDetailPageAdapter(records);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_RECORD_ADDING)
        {
            if(resultCode==RESULT_OK)
            {
                problem = (Problem)getIntent().getSerializableExtra("problem");
                records = (ArrayList<Record>)data.getSerializableExtra("records");
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}

