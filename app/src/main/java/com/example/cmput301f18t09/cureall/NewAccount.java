package com.example.cmput301f18t09.cureall;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

        username.setText("connor");                                                //init the year/month/day/hour/minute/second
        emial.setText("connor@g23.ca");
        phone.setText("123-123-1234");
        password.setText("cpass");
        rePassword.setText("cpass");

    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent incomingIntent = getIntent();
        String Role = incomingIntent.getStringExtra("Role");
        Sign(Role);
    }

    public void Sign(final String Role){


        final String Username = "connor";                                                       //get the input of year/month/day/hour/minute/
        final String Emial = "connor@g23.ca";
        final String Phone = "123-123-1234";
        final String Password = "cpass";
        final String RePassword = "cpass";

        sign.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                //if (true){
                    //if (true){
                Patient user = new Patient(Username,Password,Emial,Phone);
                NormalTweet newtweent = new NormalTweet("Well, Will.");
                setResult(RESULT_OK);

                //saveInFile(); // TODO replace this with elastic search
                ElasticSearchController.AddTweetsTask addUserTask = new ElasticSearchController.AddTweetsTask();
                addUserTask.execute(newtweent);
                    //}
                    //else{
                    //    CareProvider user = new CareProvider(Username,Password,Emial,Phone);

                    //}



               // }

                Intent intent = new Intent(NewAccount.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }


}
