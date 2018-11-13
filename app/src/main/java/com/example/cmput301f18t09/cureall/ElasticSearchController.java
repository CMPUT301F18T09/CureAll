package com.example.cmput301f18t09.cureall;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;

import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

public class ElasticSearchController {
    private static JestDroidClient client;

    // TODO we need a function which adds patient to elastic search
    public static class AddPatientTask extends AsyncTask<Patient, Void, Void> {

        @Override
        protected Void doInBackground(Patient... users) {
            verifySettings();

            for (Patient user : users) {
                Index index = new Index.Builder(user).index("cmput301f18t09test").type("patient").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if (result.isSucceeded()) {
                        user.setPatientID(result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the patient");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the patient");
                }

            }
            return null;
        }
    }

    // TODO we need a function which gets patient from elastic search
    public static class GetPatientTask extends AsyncTask<String, Void, ArrayList<Patient>> {
        @Override
        protected ArrayList<Patient> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Patient> tweets = new ArrayList<Patient>();

            // TODO Build the query

            //String query = "{ \"query\" : { \"term\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{  \"query\" : {\n" +
                    "        \"term\" : { \"username\" : \"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t09test")
                    .addType("patient")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    Log.i("Read","Read success");
                    List<Patient> foundTweets = result.getSourceAsObjectList(Patient.class);
                    tweets.addAll(foundTweets);
                    Log.i("Read",Integer.toString(tweets.size()));
                    Log.i("Read",tweets.get(0).getPassword());
                } else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tweets;
        }
    }

    // TODO we need a function which adds patient to elastic search
    public static class AddDoctorTask extends AsyncTask<CareProvider, Void, Void> {

        @Override
        protected Void doInBackground(CareProvider... users) {
            verifySettings();

            for (CareProvider user : users) {
                Index index = new Index.Builder(user).index("cmput301f18t09test").type("CareProvider").build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if (result.isSucceeded()) {
                        user.setDoctorID(result.getId());
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the Care Provider");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the Care Provider");
                }

            }
            return null;
        }
    }

    // TODO we need a function which gets patient from elastic search
    public static class GetDoctorTask extends AsyncTask<String, Void, ArrayList<CareProvider>> {
        @Override
        protected ArrayList<CareProvider> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<CareProvider> users = new ArrayList<CareProvider>();

            // TODO Build the query

            //String query = "{ \"query\" : { \"term\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{  \"query\" : {\n" +
                    "        \"term\" : { \"username\" : \"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t09test")
                    .addType("CareProvider")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    Log.i("Read","Read success");
                    List<CareProvider> foundusers = result.getSourceAsObjectList(CareProvider.class);
                    users.addAll(foundusers);
                    //Log.i("Read",Integer.toString(tweets.size()));
                    //Log.i("Read",tweets.get(0).getPassword());
                } else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return users;
        }
    }

    public static void verifySettings() {
        if (client == null) {
            DroidClientConfig.Builder builder = new DroidClientConfig.Builder("http://cmput301.softwareprocess.es:8080");
            DroidClientConfig config = builder.build();

            JestClientFactory factory = new JestClientFactory();
            factory.setDroidClientConfig(config);
            client = (JestDroidClient) factory.getObject();
        }
    }
}