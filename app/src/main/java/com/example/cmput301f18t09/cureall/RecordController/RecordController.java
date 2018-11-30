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

import android.util.Log;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Record;

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
}


