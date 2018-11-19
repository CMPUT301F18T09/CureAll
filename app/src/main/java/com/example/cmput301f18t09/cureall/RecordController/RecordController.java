package com.example.cmput301f18t09.cureall.RecordController;

import android.util.Log;

import com.example.cmput301f18t09.cureall.ElasticSearchController;
import com.example.cmput301f18t09.cureall.ElasticSearchParams;
import com.example.cmput301f18t09.cureall.Problem;
import com.example.cmput301f18t09.cureall.Record;

import java.util.ArrayList;
import java.util.Date;
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
}


