package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.Patient;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientProblemListPageAdapter;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;
import com.example.cmput301f18t09.cureall.Record;
import com.example.cmput301f18t09.cureall.RecordController.RecordController;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PatientListOfProblemsPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button searchButton, problemAddingButton;
    private RecyclerView recyclerView;
    private PatientProblemListPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    View.OnClickListener buttomListener;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList<Problem> problems;
    private ProblemController problemController = new ProblemController();
    private ArrayList<Record> records;
    private RecordController recordController= new RecordController();
    String username;
    String user_email;
    String phone;
    String id;
    String pw;
    Patient patient;
    final int REQUEST_PROBLEM_ADDING = 1;


    //drawer..
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list_of_problems_page);
        searchButton = (Button) findViewById(R.id.searchButton);
        problemAddingButton = (Button) findViewById(R.id.problemAddingButton);
        //ArrayList<Problem> problems = new ArrayList<>();
        patient = (Patient)getIntent().getSerializableExtra("patient");
        problems = (ArrayList<Problem>)getIntent().getSerializableExtra("problems");
        username = patient.getUsername();
        user_email = patient.getEmail();
        phone = patient.getPhone();
        id = patient.getPatientID();
        pw = patient.getPassword();




    }

    @Override
    protected void onStart() {
        super.onStart();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder custom = new AlertDialog.Builder(PatientListOfProblemsPageActivity.this);
                custom.setCancelable(true);
                custom.setPositiveButton("Search by Body Location", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                custom.setNegativeButton("Search by Key words", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                custom.setNeutralButton("Search by Geo-location", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                custom.show();
            }
        });
       // problems = problemController.GetProblemNum(username);


        recyclerView = findViewById(R.id.listOfProblems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PatientProblemListPageAdapter(problems);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
        //Log.i("111","111");

        mAdapter.setOnItemClickListener(new PatientProblemListPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            @Override
            public void onDetailClick(int position){

                Intent intent = new Intent(PatientListOfProblemsPageActivity.this,PatientProblemDetailPageActivity.class);
                Problem problem = problems.get(position);
                records = recordController.GetRecordNum(username,problem.getId());
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem", problem);
                bundle.putSerializable("records", records);
                bundle.putSerializable("problems",problems);
                bundle.putSerializable("patient", patient);
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onDeleteClick(int position) {
                Problem problem = problems.get(position);
                ProblemController.DelteProblem(problems,position,username);
                mAdapter.notifyDataSetChanged();
            }
        });


        //drawers &toggles & toolbar...
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,
                R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        //get navigation contents and set them by yourselfs..
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);
        //测试内容
        TextView name = header.findViewById(R.id.nav_name);
        TextView email = header.findViewById(R.id.nav_email);
        TextView phoneNumber = header.findViewById(R.id.nav_phone);
        ImageView headScript = header.findViewById(R.id.nav_headScript);
        name.setText(username);
        email.setText(user_email);
        phoneNumber.setText(phone);







        //结束
        problemAddingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientListOfProblemsPageActivity.this,PatientProblemAddingPageActivity.class);
                intent.putExtra("username", username);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problems",problems);
                intent.putExtras(bundle);

                startActivityForResult(intent,REQUEST_PROBLEM_ADDING);
                //mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_PROBLEM_ADDING)
        {
         if(resultCode==RESULT_OK)
         {
             //problems = problemController.GetProblemNum(username);
             //problems = problemController.GetProblemNum(username);
             //Intent intent = getIntent();
             problems = (ArrayList<Problem>)data.getSerializableExtra("problems");
             mAdapter.notifyDataSetChanged();
         }
        }
    }



    //allow you click on navigation menus
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.sync:
                Toast.makeText(this,"sync info with internet",Toast.LENGTH_SHORT).show();
                break;
            case R.id.mapOfRecords:
                Toast.makeText(this,"Here is a map of all records",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed(){
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }


}
