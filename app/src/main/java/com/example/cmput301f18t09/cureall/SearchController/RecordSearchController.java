package com.example.cmput301f18t09.cureall.SearchController;

import com.example.cmput301f18t09.cureall.GeneralElasticsearch.ElasticSearchController;
import com.example.cmput301f18t09.cureall.model.GeoLocation;
import com.example.cmput301f18t09.cureall.model.Record;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.example.cmput301f18t09.cureall.Activities.ProviderActivity.ProviderMainPageActivity.stringContainsItemFromList;
import static com.example.cmput301f18t09.cureall.SearchController.ProblemSearchController.stringContainsItemFromList;

public class RecordSearchController {

    public static ArrayList<Record> KeywordSearch(String[] keywords)
    {
        //boolean contain_keywords = false;
        // Get all problems
        ElasticSearchController.GetAllRecordTask getAllRecordTask = new ElasticSearchController.GetAllRecordTask();
        getAllRecordTask.execute();
        ArrayList<Record> records = new ArrayList<>();
        ArrayList<Record> records_contain_keywords = new ArrayList<>();
        try {
            records = getAllRecordTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Record each:records)
        {
            String title = each.getTitle();
            if (stringContainsItemFromList(title,keywords))
            {
                records_contain_keywords.add(each);
            }
        }
        return records_contain_keywords;

    }

    public static ArrayList<Record> BodySearch(String body_part)
    {
        //boolean contain_keywords = false;
        // Get all problems
        String title;
        ElasticSearchController.GetAllRecordTask getAllRecordTask = new ElasticSearchController.GetAllRecordTask();
        getAllRecordTask.execute();
        ArrayList<Record> records = new ArrayList<>();
        ArrayList<Record> record_contain_body = new ArrayList<>();
        try {
            records = getAllRecordTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Record each:records)
        {
            if(each.bodyLocation!=null) {
                title = each.getBodyLocation().getBodyLocationName();
                if (title.contains(body_part)) {
                    record_contain_body.add(each);
                }
            }
        }
        return record_contain_body;

    }

    public static ArrayList<Record> GeoSearch(double lat,double log)
    {
        //boolean contain_keywords = false;
        // Get all problems
        GeoLocation geoLocation;
        ElasticSearchController.GetAllRecordTask getAllRecordTask = new ElasticSearchController.GetAllRecordTask();
        getAllRecordTask.execute();
        ArrayList<Record> records = new ArrayList<>();
        ArrayList<Record> record_contain_body = new ArrayList<>();
        try {
            records = getAllRecordTask.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        for(Record each:records)
        {
            if(each.getGeoLocation()!=null) {
                 geoLocation= each.getGeoLocation();
                if(RangeChecker(geoLocation.getLatitude(),lat) && RangeChecker(geoLocation.getLongitude(),log)) {
                    record_contain_body.add(each);
                }
            }
        }
        return record_contain_body;

    }

    public static boolean RangeChecker (double num1, double num2)
    {
        if(num1>=(num2-0.01) && num1<=(num2+0.01))
        {
            return true;
        }
        return false;
    }
}
