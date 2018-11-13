package com.example.cmput301f18t09.cureall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NewAccount extends AppCompatActivity {

    private EditText username;                                                                          // the EditText which will get the y/m/d/h/m/s separate
    private EditText emial;
    private EditText phone;
    private EditText password;
    private EditText rePassword;
    private Button sign;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_account);

        username = (EditText) findViewById(R.id.username);
        emial = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        password = (EditText) findViewById(R.id.password);
        rePassword = (EditText) findViewById(R.id.rePassword);
        sign = (Button) findViewById(R.id.signup);

        /*username.setText("connor");                                                //init the year/month/day/hour/minute/second
        emial.setText("connor@g23.ca");
        phone.setText("123-123-1234");
        password.setText("cpass");
        rePassword.setText("cpass");
*/
    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent incomingIntent = getIntent();
        String Role = incomingIntent.getStringExtra("Role");
        Sign(Role);
    }

    public void Sign(final String Role){
        Log.i("Role",Role);

        /*final String Username = username.getText().toString();                                                       //get the input of year/month/day/hour/minute/
        final String Emial = emial.getText().toString();
        final String Phone = phone.getText().toString();
        final String Password = password.getText().toString();
        final String RePassword = rePassword.getText().toString();*/



        sign.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                String Username = username.getText().toString();                                                       //get the input of year/month/day/hour/minute/
                String Emial = emial.getText().toString();
                String Phone = phone.getText().toString();
                String Password = password.getText().toString();
                String RePassword = rePassword.getText().toString();
                if (Password.equals(RePassword)){
                    if (Role.equals("Patient")){
                        Log.i("Patient","choose sign up as a patient");
                        Patient user = new Patient(Username,Password,Emial,Phone);
                        //NormalTweet newtweent = new NormalTweet("Well, Will.");
                        setResult(RESULT_OK);

                        //saveInFile(); // TODO replace this with elastic search
                        ElasticSearchController.AddPatientTask addUserTask = new ElasticSearchController.AddPatientTask();
                        addUserTask.execute(user);
                    }
                    else{

                        Log.i("CareProvider","choose sign up as a CareProvider");
                        CareProvider CUSER = new CareProvider(Username,Password,Emial,Phone);
                        setResult(RESULT_OK);

                        //saveInFile(); // TODO replace this with elastic search
                        ElasticSearchController.AddDoctorTask addUserTask = new ElasticSearchController.AddDoctorTask();
                        addUserTask.execute(CUSER);
                    }




                }

                Intent intent = new Intent(NewAccount.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
