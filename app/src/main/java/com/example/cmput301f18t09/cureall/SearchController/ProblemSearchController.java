package com.example.cmput301f18t09.cureall.SearchController;


import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ProblemController.ProblemController;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.model.Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ExecutionException;

public class ProblemSearchController {
    public static ArrayList<Problem> KeywordSearch(String[] keywords)
    {
        //boolean contain_keywords = false;
        // Get all problems
        ElasticSearchController.GetAllProblemTask getAllProblemTask = new ElasticSearchController.GetAllProblemTask();
        getAllProblemTask.execute();
        ArrayList<Problem> problems = new ArrayList<>();
        ArrayList<Problem> problems_contain_keywords = new ArrayList<>();
        try {
            problems = getAllProblemTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Problem each:problems)
        {
           String title = each.getTitle();
           if (stringContainsItemFromList(title,keywords))
           {
               problems_contain_keywords.add(each);
           }
        }
        return problems_contain_keywords;

    }

    public static ArrayList<Problem> BodySearch (String body)
    {
        //Problem problem;

        ProblemController problemController = new ProblemController();
        // used to check
        ArrayList<String> problemIDs = new ArrayList<>();
        // list of all problems
        ArrayList<Problem> problems = new ArrayList<>();
        // list of needed problems
        ArrayList<Problem> problems_contain_keywords = new ArrayList<>();
        // list of result records
        ArrayList<Record> records = new ArrayList<>();
        // get list of needed records
        records = RecordSearchController.BodySearch(body);
        ElasticSearchController.GetAllProblemTask getAllProblemTask = new ElasticSearchController.GetAllProblemTask();
        getAllProblemTask.execute();
        try {
            // get all problems
            problems = getAllProblemTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Record each:records)
        {
            problemIDs.add(each.getProblemid());
        }

        for(Problem problem:problems)
        {
            // problemids need to include it and it cannot in problem contains
            if(problemIDs.contains(problem.getId())&&(!(problems_contain_keywords.contains(problem.getId()))) )
            {
                problems_contain_keywords.add(problem);
            }
        }



        return problems_contain_keywords;
    }

    public static ArrayList<Problem> GeoSearch (double lat,double log)
    {
        //Problem problem;

        ProblemController problemController = new ProblemController();
        // used to check
        ArrayList<String> problemIDs = new ArrayList<>();
        // list of all problems
        ArrayList<Problem> problems = new ArrayList<>();
        // list of needed problems
        ArrayList<Problem> problems_contain_keywords = new ArrayList<>();
        // list of result records
        ArrayList<Record> records = new ArrayList<>();
        // get list of needed records
        records = RecordSearchController.GeoSearch(lat,log);
        ElasticSearchController.GetAllProblemTask getAllProblemTask = new ElasticSearchController.GetAllProblemTask();
        getAllProblemTask.execute();
        try {
            // get all problems
            problems = getAllProblemTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for (Record each:records)
        {
            problemIDs.add(each.getProblemid());
        }

        for(Problem problem:problems)
        {
            // problemids need to include it and it cannot in problem contains
            if(problemIDs.contains(problem.getId())&&(!(problems_contain_keywords.contains(problem.getId()))) )
            {
                problems_contain_keywords.add(problem);
            }
        }



        return problems_contain_keywords;
    }


    public static boolean stringContainsItemFromList(String inputStr, String[] items) {
        return Arrays.stream(items).parallel().anyMatch(inputStr::contains);
    }



}
