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
}
