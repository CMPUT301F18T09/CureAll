/**
 * Class name: RecordController
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.RecordController;

import android.content.Context;
import android.util.Log;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Record;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class RecordController {

    public ArrayList<Record> GetRecordNum(String username, String problemID){
        ArrayList<Record> records = new ArrayList<Record>();
        ElasticSearchController.GetRecordTask getRecordTask = new ElasticSearchController.GetRecordTask();
        ElasticSearchParams params = new ElasticSearchParams(username, problemID);

        getRecordTask.execute(params);
        try {
            List<Record> foundPatient= getRecordTask.get();
            records.addAll(foundPatient);


        } catch (Exception e) {
            Log.i("Error", "Failed to get the user from the async object");
        }

        return records;
    }

    public static void UpdateRecord (Record record)
    {
        String username = record.getUsername();
        String problemID = record.getProblemid();
        // delete record
        String delete = "delete";
        ElasticSearchParams params = new ElasticSearchParams("",record,record.getID(),delete);
        ElasticSearchController.DeleteRecordTask deleteRecordTask = new ElasticSearchController.DeleteRecordTask();
        deleteRecordTask.execute(params);
        //Log.i("Unknown bug", );
        // add new record
        String add = "add";
        ElasticSearchParams param = new ElasticSearchParams(username,record,problemID,add);
        ElasticSearchController.AddRecordTask addRecordTask = new ElasticSearchController.AddRecordTask();
        addRecordTask.execute(param);

    }
    public static ArrayList<Record> loadFromFile(Context context, String FILENAME, ArrayList<Record> records, String username) {
//now we can save to the file
        try {
            File rootDir = new File(context.getFilesDir(),username);
            FileInputStream fis = new FileInputStream(new File(rootDir,FILENAME));
            //FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            GsonBuilder gson = new GsonBuilder();
            //gson.registerTypeAdapter(Problem .class, new MoodAdapter());
            //Gson gson = new Gson();
            //Type listTweetTYpe = new TypeToken<ArrayList<Tweet>>();
            //!!!!the code below may need a subclass like joy, Arraylist<joy>
            Type recordType = new TypeToken<ArrayList<Record>>(){}.getType();
            //Log.i("fangpei","success");
            records = gson.create().fromJson(reader, recordType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            records = new ArrayList<Record>();

            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return records;
    }

    /*
     * method saveInfile store mood arraylist into file by serializing it
     * */

    public static void saveInFile(Context context, String FILENAME, ArrayList<Record> records, String username) {
        try {
            File rootDir = new File(context.getFilesDir(),username);

            FileOutputStream fos = new FileOutputStream(new File(rootDir,FILENAME));//context.openFileOutput(FILENAME,0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();


            gson.toJson(records, writer);

            writer.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


}


