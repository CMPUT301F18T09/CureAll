package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.PatientAdapter.PatientProblemListPageAdapter;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class PatientListOfProblemsPageActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    private Button searchButton, problemAddingButton;
    private RecyclerView recyclerView;
    private PatientProblemListPageAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    View.OnClickListener buttomListener;
    private SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    ArrayList<Problem> problems;

    //drawer..
    private DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_list_of_problems_page);
        searchButton = (Button) findViewById(R.id.backButton);
        problemAddingButton = (Button) findViewById(R.id.problemAddingButton);
        //ArrayList<Problem> problems = new ArrayList<>();
        Intent incomingIntent = getIntent();
        final String username = incomingIntent.getStringExtra("username");
        final String user_email = incomingIntent.getStringExtra("email");
        final String phone = incomingIntent.getStringExtra("phone");
        final String id = incomingIntent.getStringExtra("id");
        final String pw = incomingIntent.getStringExtra("password");
        problems = GetProblemNum(username);


        recyclerView = findViewById(R.id.listOfProblems);
        recyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mAdapter = new PatientProblemListPageAdapter(problems);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);


        mAdapter.setOnItemClickListener(new PatientProblemListPageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }
            @Override
            public void onDetailClick(int position){
                Intent intent = new Intent(PatientListOfProblemsPageActivity.this,PatientProblemDetailPageActivity.class);
                Problem problem = problems.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("problem", problem);
                intent.putExtras(bundle);
//                intent.putExtra("problem",problem.getId());
                startActivity(intent);
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
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(PatientListOfProblemsPageActivity.this,"Leo, you should implement this button...",Toast.LENGTH_SHORT).show();
            }
        });
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

    public ArrayList<Problem> GetProblemNum(String username){
        ArrayList<Problem> problems = new ArrayList<Problem>();
        ElasticSearchController.GetProblemTask getproblemTask = new ElasticSearchController.GetProblemTask();
        getproblemTask.execute(username);

        try {
            List<Problem> foundPatient= getproblemTask.get();
            problems.addAll(foundPatient);


        } catch (Exception e) {
            Log.i("Error", "Failed to get the user from the async object");
        }

        Log.i("Read","read end");

        return problems;
    }
}
