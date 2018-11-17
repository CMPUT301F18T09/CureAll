package com.example.cmput301f18t09.cureall;

import android.os.AsyncTask;
import android.util.Log;

import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.searchbox.client.JestResult;
import io.searchbox.core.Delete;
import io.searchbox.core.DocumentResult;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;

//import org.elasticsearch.action.update.UpdateRequest;


public class ElasticSearchController {
    private static JestDroidClient client;


    public static class SearchKeywordsTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Problem> records = new ArrayList<Problem>();

            // TODO Build the query


            String query = "{\"query\":{\n"+
                                "\"match all\":{\n"+
                                    "\"title\":\"blue attachment\"\n"+
                                "}\n" +
                                "}\n"+
                            "}";


            //String query = "{ \"query\" : { \"term\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            /*String query = "{  \"query\" : {\n" +
                    "        \"term\" : { \"title\" : \"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";*/

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t09test")
                    .addType("problem")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {

                    Log.i("Read","Read success");

                    List<Problem> foundrecords = result.getSourceAsObjectList(Problem.class);
                    records.addAll(foundrecords);
                    for(Problem p:records){
                        Log.i("Problem",p.getTitle());
                    }

                } else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return records;
        }
    }

    public static class GetPatientListTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<String> users = new ArrayList<String>();

            // TODO Build the query

            //String query = "{ \"query\" : { \"match\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{  \"query\" : {\n" +
                    "        \"term\" : { \"username\" : \"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t09test")
                    .addType("problem")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    ArrayList<String> IDs = new ArrayList<String>();
                    Log.i("Read","Read success");

                    List<String> foundPatients = result.getSourceAsObjectList(String.class);
                    users.addAll(foundPatients);


                } else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return users;
        }

    }

    public static class PatientListTask extends AsyncTask<ElasticSearchParams, Void, Void> {

        @Override
        protected Void doInBackground(ElasticSearchParams... params) {
            verifySettings();
            String username = params[0].username;
            ArrayList<String> patients= params[0].patients;

            Integer num = params[0].num;
            //for (String patient : patients) {
            String source = "{\"patient\":\""+patients.get(0)+"\",\n"+
                            "\"doctor:\":\""+username+"\" }";

            if (num < 1000000){
                String Num = String.format("%06d",num);
                String id = username+ "2" + Num;
                Index index = new Index.Builder(source).index("cmput301f18t09test").type("patientList").id(id).build();
                try {

                    //Index index = new Index.Builder(source).index("cmput301f18t09test").type("PatientList").id(id).build();
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()) {
                        //user.setPatientID(result.getId());
                        Log.i("PatientList", "save success!");
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the patient");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed add the PatientList");
                }
            }




            return null;
        }
    }

    public static class GetProblemTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Problem> users = new ArrayList<Problem>();

            // TODO Build the query

            //String query = "{ \"query\" : { \"match\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{  \"query\" : {\n" +
                    "        \"term\" : { \"username\" : \"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t09test")
                    .addType("problem")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    ArrayList<String> IDs = new ArrayList<String>();
                    Log.i("Read","Read success");
                    List<SearchResult.Hit<Map,Void>> hits= result.getHits(Map.class);
                    for (SearchResult.Hit hit : hits){
                        Map source = (Map) hit.source;
                        String id = (String)source.get(JestResult.ES_METADATA_ID);
                        //Patient p = (Patient)source.get(Patient.class);
                        //Log.i("Read",p.getUsername());
                        IDs.add(id);
                        Log.i("Read",id);

                    }

                    Integer a = 0;
                    List<Problem> foundPatients = result.getSourceAsObjectList(Problem.class);
                    for (Problem p : foundPatients) {
                        p.setId(IDs.get(a));
                        a++;
                        users.add(p);
                    }

                } else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
          return users;
        }
    }

    public static class AddProblemTask extends AsyncTask<ElasticSearchParams, Void, Void> {

        @Override
        protected Void doInBackground(ElasticSearchParams... params) {
            verifySettings();
            Integer num = params[0].num;
            Problem problem = params[0].problem;

            if (num < 1000000){
                String Num = String.format("%06d",num);
                String id =  problem.getUsername()+ "2" + Num;
                Index index = new Index.Builder(problem).index("cmput301f18t09test").type("problem").id(id).build();
                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if (result.isSucceeded()) {
                        //user.setPatientID(result.getId());
                        Log.i("Problem","Problem save success!");
                    } else {
                        Log.i("Error", "Elasticsearch was not able to add the patient");
                    }
                } catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the patient");
                }
            }


            else{
                String id = params[0].username + "2" + Integer.toString(num);
                Index index = new Index.Builder(problem).index("cmput301f18t09test").type("problem").id(id).build();
                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if (result.isSucceeded()) {
                        //user.setPatientID(result.getId());
                        Log.i("Problem","Problem save success!");
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




    public static class ChangeInfoTask extends AsyncTask<Patient, Void, Void> {

        @Override
        protected Void doInBackground(Patient... users) {
            verifySettings();

            for (Patient user : users) {
                String id = user.getUsername()+"1000001";
                Index index = new Index.Builder(user).index("cmput301f18t09test").type("patient").id(id).build();

                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if (result.isSucceeded()) {
                        //user.setPatientID(result.getId());
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

    public static class EditProblemTask extends AsyncTask<ElasticSearchParams, Void, Void> {

        @Override
        protected Void doInBackground(ElasticSearchParams... params) {
            verifySettings();
            Problem problem = params[0].problem;
            String id = params[0].id;
            Index index = new Index.Builder(problem).index("cmput301f18t09test").type("problem").id(id).build();
            try {
                // where is the client?
                client.execute(new Delete.Builder(id)
                        .index("cmput301f18t09test")
                        .type("problem")
                        .build());
                DocumentResult result = client.execute(index);

                if (result.isSucceeded()) {
                //user.setPatientID(result.getId());
                } else {
                    Log.i("Error", "Elasticsearch was not able to add the patient");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the patient");
            }

            return null;
        }
    }

    // TODO we need a function which adds patient to elastic search
    public static class AddPatientTask extends AsyncTask<Patient, Void, Void> {

        @Override
        protected Void doInBackground(Patient... users) {
            verifySettings();
            Log.i("Chen", "Elasticsearch was not able to add the patient");
            for (Patient user : users) {
                String id = user.getUsername()+"1000001";
                Index index = new Index.Builder(user).index("cmput301f18t09test").type("patient").id(id).build();
                Log.i("Chen", "Elasticsearch was not able to add the patient");
                try {
                    // where is the client?
                    DocumentResult result = client.execute(index);

                    if (result.isSucceeded()) {
                        //user.setPatientID(result.getId());
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
            String query = "{   \"query\" : {\n" +
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
                    ArrayList<String> IDs = new ArrayList<String>();
                    Log.i("Read","Read success");
                    List<SearchResult.Hit<Map,Void>> hits= result.getHits(Map.class);
                    for (SearchResult.Hit hit : hits){
                        Map source = (Map) hit.source;
                        String id = (String)source.get(JestResult.ES_METADATA_ID);
                        //Patient p = (Patient)source.get(Patient.class);
                        //Log.i("Read",p.getUsername());
                        IDs.add(id);
                        Log.i("Read",id);

                    }

                    Integer a = 0;
                    List<Patient> foundPatients = result.getSourceAsObjectList(Patient.class);
                    for (Patient p : foundPatients){
                        p.setPatientID(IDs.get(a));
                        a++;
                        tweets.add(p);
                    }
                    //tweets.addAll(foundTweets);

                } else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }


            for (Patient p : tweets){
                Log.i("Read",p.getUsername());
                Log.i("Read",p.getPatientID());
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
                    Log.i("Read",Integer.toString(users.size()));
                    Log.i("Read",users.get(0).getPassword());
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