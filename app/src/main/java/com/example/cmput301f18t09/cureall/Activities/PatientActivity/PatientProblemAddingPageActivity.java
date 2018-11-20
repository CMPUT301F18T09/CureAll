package com.example.cmput301f18t09.cureall.Activities.PatientActivity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.R;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class PatientProblemAddingPageActivity extends AppCompatActivity {
    private TextView maxLength30, maxLength300;
    private ImageView writeSymbol;
    private Button backButton, saveButton, timeSelectButton;
    private EditText titleInput, descriptionInput;
    private String username;
    private ArrayList<Problem> problems = new ArrayList<Problem>();
    private ProblemController problemController = new ProblemController();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_problem_adding_page);
        initializedAllElements();

        saveButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String username = Username.getText().toString();                                                       //get the input of year/month/day/hour/minute/
                String prob_title = titleInput.getText().toString();
                String prob_desp = descriptionInput.getText().toString();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                //Problem problem = new Problem(username,prob_title,prob_desp,currentDateTimeString,null);
                saveProblem(username,prob_title,prob_desp,currentDateTimeString);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        // Actions to do after 10 seconds
                        problems = problemController.GetProblemNum(username);
                        Intent intent = new Intent();
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                        Bundle bundle = new Bundle();
                        bundle.putSerializable("problems",problems);
                        intent.putExtras(bundle);
                        setResult(RESULT_OK, intent);
                        finish();
                    }
                }, 1000);


            }
        });

    }

    public void initializedAllElements(){
        maxLength30 = (TextView) findViewById(R.id.maxLength30);
        maxLength300 = findViewById(R.id.maxLength300);
        writeSymbol = findViewById(R.id.writeSymbol);
        backButton = (Button) findViewById(R.id.backButton);
        saveButton = (Button) findViewById(R.id.saveButton);
        timeSelectButton = (Button) findViewById(R.id.timeSelectButton);
        titleInput = findViewById(R.id.titleInput);
        descriptionInput = findViewById(R.id.descriptionInput);
        Intent incomingIntent = getIntent();
        username = incomingIntent.getStringExtra("username");
        problems = (ArrayList<Problem>)incomingIntent.getSerializableExtra("problems");

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

    public void saveProblem(String username, String prob_title,String prob_desp,String date){
        ArrayList<Problem> problems = GetProblemNum(username);

        Problem problem = new Problem(username,prob_title,prob_desp,date,null);

        ElasticSearchParams param = new ElasticSearchParams(problems.size(),problem,username);

        ElasticSearchController.AddProblemTask addproblemTask = new ElasticSearchController.AddProblemTask();
        addproblemTask.execute(param);
    }

}
