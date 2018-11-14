package com.example.cmput301f18t09.cureall;

import android.content.Intent;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.net.PortUnreachableException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PatientMainPage extends AppCompatActivity {

    Button Info_save;
    TextView Username;
    EditText Email;
    EditText Phone;

    EditText Title;
    EditText descrip;
    Button prob_save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_main_page);

        Username = (TextView) findViewById(R.id.change_username);
        Email = (EditText) findViewById(R.id.change_email);
        Phone = (EditText) findViewById(R.id.change_phone);
        Info_save = (Button) findViewById(R.id.info_save);
        Title = (EditText) findViewById(R.id.Title);
        descrip = (EditText) findViewById(R.id.description);
        prob_save = (Button) findViewById(R.id.save_prob);
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent incomingIntent = getIntent();
        //final ArrayList<Patient> username = incomingIntent.getStringExtra("username");
        final String username = incomingIntent.getStringExtra("username");
        final String email = incomingIntent.getStringExtra("email");
        final String phone = incomingIntent.getStringExtra("phone");
        final String id = incomingIntent.getStringExtra("id");
        final String pw = incomingIntent.getStringExtra("password");

        Username.setText(username);                                                //init the year/month/day/hour/minute/second
        Email.setText(email);
        Phone.setText(phone);


        //change.setOnClickListener(new View.OnClickListener() {

      //      public void onClick(View v) {
        addProblem(username);
       // updateinfo(username,email,phone,id,pw);
        //    }
        //});
    }
    public void addProblem(final String username){
        prob_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String username = Username.getText().toString();                                                       //get the input of year/month/day/hour/minute/
                String prob_title = Title.getText().toString();
                String prob_desp = descrip.getText().toString();
                String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

                //Problem problem = new Problem(username,prob_title,prob_desp,currentDateTimeString,null);
                saveProblem(username,prob_title,prob_desp,currentDateTimeString);

            }
        });
    }
    public Integer GetProblemNum(String username){
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

        return problems.size();
    }

    public void saveProblem(String username, String prob_title,String prob_desp,String date){
        Integer num = GetProblemNum(username);

        Log.i("Read",Integer.toString(num));
        Problem problem = new Problem(username,prob_title,prob_desp,date,null);

        ElasticSearchParams param = new ElasticSearchParams(num,problem);

        ElasticSearchController.AddProblemTask addproblemTask = new ElasticSearchController.AddProblemTask();
        addproblemTask.execute(param);
    }

    public void info_change(String username,String email, String phone, String id,String pw){
        Patient p = new Patient(username,pw,email,phone);
        //p.setPatientID(id);
        ElasticSearchController.ChangeInfoTask changeuserTask = new ElasticSearchController.ChangeInfoTask();
        changeuserTask.execute(p);

        Log.i("InfoChanged","info changed");
        Intent intent = new Intent(PatientMainPage.this,MainActivity.class);
        startActivity(intent);
    }
    public void updateinfo(final String username, String email, String phone,final String id,final String pw){
        Info_save.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //String username = Username.getText().toString();                                                       //get the input of year/month/day/hour/minute/
                String changed_email = Email.getText().toString();
                String changed_phone = Phone.getText().toString();
                info_change(username,changed_email,changed_phone,id,pw);

            }
        });
    }
}
