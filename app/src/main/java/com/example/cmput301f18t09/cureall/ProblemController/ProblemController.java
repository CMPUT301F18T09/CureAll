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

import android.util.Log;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Problem;

import java.util.ArrayList;
import java.util.List;

public class ProblemController {
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

    public static void DelteProblem(ArrayList<Problem> problems, int position, String username)
    {
        Problem problem = problems.get(position);
        //Log.i("Unknown bug", );
        ElasticSearchParams params = new ElasticSearchParams("",problem,problem.getId());
        ElasticSearchController.DeleteProblemTask deleteProblemTask = new ElasticSearchController.DeleteProblemTask();
        deleteProblemTask.execute(params);
        problems.remove(position);
    }
    //TODO we need a fucntion that are able a problem
    //TODO here is the function i may need to use and its parameters
    public static void UpdateProblem(ArrayList<Problem> problems, int position, Problem problem){
        Problem oldProblem = problems.get(position);
        Problem newProblem = problem;

    }
}
