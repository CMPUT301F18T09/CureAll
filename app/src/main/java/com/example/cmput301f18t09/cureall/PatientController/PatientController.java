package com.example.cmput301f18t09.cureall.PatientController;

import android.content.Context;
import android.util.Log;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.Patient;
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
import java.util.Date;
import java.util.List;

public class PatientController {
    public static ArrayList<Patient> loadFromFile(Context context, String FILENAME, ArrayList<Patient> patientName, String username) {

        try {
            File rootDir = new File(context.getFilesDir(), username);
            FileInputStream fis = new FileInputStream(new File(rootDir, FILENAME));
            //FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            GsonBuilder gson = new GsonBuilder();
            //gson.registerTypeAdapter(Patient .class, new MoodAdapter());
            //Gson gson = new Gson();
            //Type listTweetTYpe = new TypeToken<ArrayList<Tweet>>();
            //!!!!the code below may need a subclass like joy, Arraylist<joy>
            Type patientType = new TypeToken<ArrayList<Patient>>() {
            }.getType();
            //Log.i("fangpei","success");
            patientName = gson.create().fromJson(reader, patientType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            patientName = new ArrayList<Patient>();

            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        for (Patient p : patientName) {
            Log.i("Patient", p.getUsername());
        }
        return patientName;
    }

    /*
     * method saveInfile store mood arraylist into file by serializing it
     * */

    public static void saveInFile(Context context, String FILENAME, ArrayList<Patient> patiens, String username) {
        try {
            File rootDir = new File(context.getFilesDir(), username);
            rootDir.mkdir();

            FileOutputStream fos = new FileOutputStream(new File(rootDir, FILENAME));//context.openFileOutput(FILENAME,0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();

            gson.toJson(patiens, writer);

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

    public static void SaveLocalTracker(Context context, String username) {
        try {
            File rootDir = new File(context.getFilesDir(), username);
            rootDir.mkdir();

            FileOutputStream fos = new FileOutputStream(new File(rootDir, "tracker.txt"));//context.openFileOutput(FILENAME,0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();
            Date date = new Date();
            gson.toJson(date, writer);

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

    public static Date GetLocalTracker(Context context, String username,Date date) {
        try {
            ArrayList<Date> dates = new ArrayList<>();

            File rootDir = new File(context.getFilesDir(), username);
            FileInputStream fis = new FileInputStream(new File(rootDir, "tracker.txt"));
            //FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            GsonBuilder gson = new GsonBuilder();
            //gson.registerTypeAdapter(Problem .class, new MoodAdapter());
            //Gson gson = new Gson();
            //Type listTweetTYpe = new TypeToken<ArrayList<Tweet>>();
            //!!!!the code below may need a subclass like joy, Arraylist<joy>
            Type dateType = new TypeToken<ArrayList<Date>>() {}.getType();
            //Log.i("fangpei","success");
            dates = gson.create().fromJson(reader, dateType);
            date = dates.get(0);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            date = new Date();
            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return date;
    }

    public static Date GetOnlineTracker (String username,Date date){
        Date tracker = new Date();
        ElasticSearchController.GetTrackerTask getTrackerTask = new ElasticSearchController.GetTrackerTask();
        //ElasticSearchParams params = new ElasticSearchParams(username, problemID);

        getTrackerTask.execute(username);
        try {
            //List<Date> foundtracker= getTrackerTask.get();
            tracker = getTrackerTask.get();


        } catch (Exception e) {
            Log.i("Error", "Failed to get the user from the async object");
        }
        date = tracker;
        return tracker;
    }

}

