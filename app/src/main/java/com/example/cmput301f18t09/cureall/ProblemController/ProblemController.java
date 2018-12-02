/**
 * Class name: ProblemController
 *
 * Version: v1.0.0
 *
 * Date: November 14, 2018
 *
 * Copyright (c) 2018. Team09, F18 CMPUT301, All rights reserved.
 */
package com.example.cmput301f18t09.cureall.ProblemController;

import android.content.Context;
import android.util.Log;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.UserState;
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

public class ProblemController {
    public static ArrayList<Problem> GetProblemNum(String username){
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

    public static void DelteProblem(ArrayList<Problem> problems, int position, String username,Context context)
    {
        Problem problem = problems.get(position);
        //Log.i("Unknown bug", );\
        UserState currentState = new UserState(context);
        if (currentState.getState()){
            ElasticSearchParams params = new ElasticSearchParams("",problem,problem.getId());
            ElasticSearchController.DeleteProblemTask deleteProblemTask = new ElasticSearchController.DeleteProblemTask();
            deleteProblemTask.execute(params);

        }
        problems.remove(position);
        ProblemController.saveInFile(context,"problems.txt",problems,username);


    }


    public static void DelteProblem(Problem problem)
    {
            ElasticSearchParams params = new ElasticSearchParams("",problem,problem.getId());
            ElasticSearchController.DeleteProblemTask deleteProblemTask = new ElasticSearchController.DeleteProblemTask();
            deleteProblemTask.execute(params);
    }

    public static void AddProblem(Problem problem,String username){
            ArrayList<Problem> problems = ProblemController.GetProblemNum(username);
            ElasticSearchParams param = new ElasticSearchParams(problems.size(), problem, username);

            ElasticSearchController.AddProblemTask addproblemTask = new ElasticSearchController.AddProblemTask();
            addproblemTask.execute(problem);
    }

    public static ArrayList<Problem> loadFromFile(Context context, String FILENAME, ArrayList<Problem> problems, String username) {

        try {
            File rootDir = new File(context.getFilesDir(),username);
            FileInputStream fis = new FileInputStream(new File(rootDir,FILENAME));
            //FileInputStream fis = context.openFileInput(FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);

            GsonBuilder gson = new GsonBuilder();
            //gson.registerTypeAdapter(Problem .class, new MoodAdapter());
            //Gson gson = new Gson()
            //Type listTweetTYpe = new TypeToken<ArrayList<Tweet>>();
            //!!!!the code below may need a subclass like joy, Arraylist<joy>
            Type problemType = new TypeToken<ArrayList<Problem>>(){}.getType();
            //Log.i("fangpei","success");
            problems = gson.create().fromJson(reader, problemType);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            problems = new ArrayList<Problem>();

            e.printStackTrace();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return problems;
    }

    /*
     * method saveInfile store mood arraylist into file by serializing it
     * */

    public static void saveInFile(Context context, String FILENAME, ArrayList<Problem> problems, String username) {
        try {
            File rootDir = new File(context.getFilesDir(),username);

            FileOutputStream fos = new FileOutputStream(new File(rootDir,FILENAME));//context.openFileOutput(FILENAME,0);
            OutputStreamWriter osw = new OutputStreamWriter(fos);
            BufferedWriter writer = new BufferedWriter(osw);
            Gson gson = new Gson();

            gson.toJson(problems, writer);

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
