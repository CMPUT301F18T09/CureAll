package com.example.cmput301f18t09.cureall.GeneralElasticsearch;

import android.os.AsyncTask;
import android.util.Log;

import com.example.cmput301f18t09.cureall.model.CareProvider;
import com.example.cmput301f18t09.cureall.model.Patient;
import com.example.cmput301f18t09.cureall.model.Problem;
import com.example.cmput301f18t09.cureall.model.Record;
import com.searchly.jestdroid.DroidClientConfig;
import com.searchly.jestdroid.JestClientFactory;
import com.searchly.jestdroid.JestDroidClient;

import java.util.ArrayList;
import java.util.Date;
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
    public static class GetPatientListTask extends AsyncTask<String, Void, ArrayList<Patient>> {
        @Override
        protected ArrayList<Patient> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Patient> tweets = new ArrayList<Patient>();

            // TODO Build the query

            String query = "{ \"size\":100,\"query\" : { \"match\" : { \"doctorID\" : \""+search_parameters[0]+"\"}}}";
            //String query = ;

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
                        IDs.add(id);
                        //Log.i("Read",id);

                    }

                    Integer a = 0;
                    List<Patient> foundPatients = result.getSourceAsObjectList(Patient.class);
                    for (Patient p : foundPatients){
                        p.setPatientID(IDs.get(a));
                        a++;
                        Log.i("Read",p.getUsername());
                        tweets.add(p);
                    }
                    //tweets.addAll(foundPatients);

                } else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return tweets;
        }
    }

    public static class SearchGeoTask extends AsyncTask<String, Void, ArrayList<Record>> {
        @Override
        protected ArrayList<Record> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Record> records = new ArrayList<Record>();

            // TODO Build the query



            String query ="{\"size\":100,\n" +
                    "\"query\": { \n" +
                    "\"filtered\" : { \n" +
                    "\"query\" : { \n" +
                    "\"match_all\" : {} \n" +
                    "}, \n" +
                    "\"filter\" : { \n" +
                    "\"geo_distance\" : { \n" +
                    "\"distance\" : \"500m\", \n" +
                    "\"geoLocation.Location\": { \n" +
                    "\"lat\": 40.12,\n" +
                    "\"lon\": -71.34\n" +
                    "}\n" +
                    "}\n" +
                    "}\n" +
                    "}\n" +
                    "}\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t09test")
                    .addType("records")
                    .build();

            try {

                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {

                    Log.i("Read","Read success");

                    List<Record> foundrecords = result.getSourceAsObjectList(Record.class);
                    records.addAll(foundrecords);
                    for(Record p:records){
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

    public static class AddRecordTask extends AsyncTask<ElasticSearchParams, Void, Record> {

        @Override
        protected Record doInBackground(ElasticSearchParams... params) {
            verifySettings();

            Record record = params[0].record;
            String username = params[0].username;
            String problemid = params[0].problemid;

            Index index = new Index.Builder(record).index("cmput301f18t09test").type("records").build();
            try {
                // where is the client?
                DocumentResult result = client.execute(index);
                //client.execute(index);
                if (result.isSucceeded()) {
                    record.setID(result.getId());
                    Log.i("Problem","Record save success!");
                } else {
                    Log.i("Error", "Elasticsearch was not able to add the patient");
                }

            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the patient");
            }

            Log.i("map","end");
            return record;
        }
    }


    public static class SearchKeywordsTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Problem> records = new ArrayList<Problem>();

            // TODO Build the query


            String query = "{\"query\":{\n"+
                    "\"match all\":{\n"+
                    "\"title\":\"blue\"\n"+
                    "\"description:\":\"I lost\""+
                    "}\n" +
                    "}\n"+
                    "}";


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

    public static class GetRecordTask extends AsyncTask<ElasticSearchParams, Void, ArrayList<Record>> {
        @Override
        protected ArrayList<Record> doInBackground(ElasticSearchParams... search_parameters) {
            verifySettings();

            ArrayList<Record> users = new ArrayList<Record>();

            // TODO Build the query
            String username = search_parameters[0].username;
            String problemid = search_parameters[0].problemid;

            //String query = "{ \"query\" : { \"match\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{\"size\":100,\n" +
                    "\"query\": { \n" +
                    "\"bool\":{\n" +
                    "\"must\": [\n" +
                    "{\"match\":{ \"username\": \""+username+"\"}},\n" +
                    "{\"match\":{\"problemid\":\""+problemid+"\"}}\n" +
                    "]\n" +
                    "}\n" +
                    "}\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t09test")
                    .addType("records")
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
                    List<Record> foundPatients = result.getSourceAsObjectList(Record.class);
                    for (Record p : foundPatients) {
                        p.setID(IDs.get(a));
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

    public static class GetAllRecordTask extends AsyncTask<String, Void, ArrayList<Record>> {
        @Override
        protected ArrayList<Record> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Record> users = new ArrayList<Record>();

            // TODO Build the query
       /*     String username = search_parameters[0].username;
            String problemid = search_parameters[0].problemid;*/

            //String query = "{ \"query\" : { \"match\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{  \"size\":100,\"query\" : {\n" +
                    "        \"match_all\" : {} \n" +
                    "    }\n" +
                    "}";

            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t09test")
                    .addType("records")
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
                    List<Record> foundPatients = result.getSourceAsObjectList(Record.class);
                    for (Record p : foundPatients) {
                        p.setID(IDs.get(a));
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

    public static class GetProblemTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Problem> users = new ArrayList<Problem>();

            // TODO Build the query

            //String query = "{ \"query\" : { \"match\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{  \"size\":100,\"query\" : {\n" +
                    "        \"match\" : { \"username\" : \"" + search_parameters[0] + "\" }\n" +
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
                        //Log.i("Read",id);

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

    // try to get all problems for searching
    public static class GetAllProblemTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
            protected ArrayList<Problem> doInBackground(String... search_parameters) {
                verifySettings();

            ArrayList<Problem> users = new ArrayList<Problem>();

            // TODO Build the query

            //String query = "{ \"query\" : { \"match\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{  \"size\":100,\"query\" : {\n" +
                    "        \"match_all\" : {} \n" +
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
                        //Log.i("Read",id);

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

    public static class AddProblemTask extends AsyncTask<Problem, Void, Problem> {

        @Override
        protected Problem doInBackground(Problem... params) {
            verifySettings();
            //Integer num = params[0].num;
            Problem problem = params[0];

            Index index = new Index.Builder(problem).index("cmput301f18t09test").type("problem").build();
            try {
                // where is the client?
                DocumentResult result = client.execute(index);

                if (result.isSucceeded()) {
                    problem.setId(result.getId());
                    Log.i("Problem","Problem save success!"+problem.getId());
                } else {
                    Log.i("Error", "Elasticsearch was not able to add the patient");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the patient");
            }




            return problem;
        }
    }




    public static class ChangeInfoTask extends AsyncTask<Patient, Void, Void> {

        @Override
        protected Void doInBackground(Patient... users) {
            verifySettings();

            for (Patient user : users) {
                //TODO 1 BUG IS HERE FOUNDED
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

    public static class DeleteRecordTask extends AsyncTask<ElasticSearchParams, Void, Void> {

        @Override
        protected Void doInBackground(ElasticSearchParams... params) {
            verifySettings();
            Problem problem = params[0].problem;
            String id = params[0].id;
            //id = "AWdoXJLQ5fBYrXv4dPeR";
            //Index index = new Index.Builder(problem).index("cmput301f18t09test").type("problem").id(id).build();
            try {
                // where is the client?
                DocumentResult result = client.execute(new Delete.Builder(id)
                        .index("cmput301f18t09test")
                        .type("records")
                        .build());
                //DocumentResult result = client.execute(index);

                if (result.isSucceeded()) {
                    //user.setPatientID(result.getId());
                } else {
                    Log.i("Error", "Elasticsearch was not able to delete the record");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the record");
            }

            return null;
        }
    }

    public static class DeleteProblemTask extends AsyncTask<ElasticSearchParams, Void, Void> {

        @Override
        protected Void doInBackground(ElasticSearchParams... params) {
            verifySettings();
            Problem problem = params[0].problem;
            String id = params[0].id;
            //Index index = new Index.Builder(problem).index("cmput301f18t09test").type("problem").id(id).build();
            try {
                // where is the client?
                DocumentResult result = client.execute(new Delete.Builder(id)
                        .index("cmput301f18t09test")
                        .type("problem")
                        .build());
                //DocumentResult result = client.execute(index);

                if (result.isSucceeded()) {
                    //user.setPatientID(result.getId());
                } else {
                    Log.i("Error", "Elasticsearch was not able to delete the problem");
                }
            } catch (Exception e) {
                Log.i("Error", "The application failed to build and send the problem");
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

    // TODO we need a function which gets patient from elastic search
    public static class GetPatientTask extends AsyncTask<String, Void, ArrayList<Patient>> {
        @Override
        protected ArrayList<Patient> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Patient> tweets = new ArrayList<Patient>();

            // TODO Build the query

            //String query = "{ \"query\" : { \"term\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{  \"query\" : {\n" +
                    "        \"match\" : { \"username\" : \"" + search_parameters[0] + "\" }\n" +
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
            String query = "{ \"size\":100, \"query\" : {\n" +
                    "        \"match\" : { \"username\" : \"" + search_parameters[0] + "\" }\n" +
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


    public static class SyncPartProblemTask extends AsyncTask<ElasticSearchParams, Void, ArrayList<Problem>> {

        @Override
        protected ArrayList<Problem> doInBackground(ElasticSearchParams... params) {
            verifySettings();
            ArrayList<Problem> problems = new ArrayList<>();
            String username = params[0].username;
            Date pulltime = params[0].lastPulltime;
            String query =  "{\"size\":100,\n" +
                    "\"query\": { \n" +
                    "\"range\":{\n" +
                    "\"EditTime\": {\n" +
                    "\"gte\": "+pulltime+"}\n" +
                    "}\n" +
                    "}\n" +
                    "}";
            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t09test")
                    .addType("problems")
                    .build();
            try {
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {
                    ArrayList<String> IDs = new ArrayList<String>();
                    Log.i("Read","Read success");
                    List<SearchResult.Hit<Map,Void>> hits= result.getHits(Map.class);
                    for (SearchResult.Hit hit : hits){
                        Map source = (Map) hit.source;
                        String id = (String)source.get(JestResult.ES_METADATA_ID);
                        IDs.add(id);
                        Log.i("Read",id);
                    }

                    Integer a = 0;
                    List<Problem> foundPatients = result.getSourceAsObjectList(Problem.class);
                    for (Problem p : foundPatients) {
                        p.setId(IDs.get(a));
                        a++;
                        problems.add(p);
                    }

                } else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            return problems;
        }
    }
    public static class OnlineTask extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... users) {
            verifySettings();


            String id = users[0]+"2000001";
            Date date = new Date();
            ElasticSearchParams tracker = new ElasticSearchParams(users[0],date);
            Index index = new Index.Builder(tracker).index("cmput301f18t09test").type("Tracker").id(id).build();

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


            return null;
        }
    }

    public static class GetTrackerTask extends AsyncTask<String, Void, Date> {
        @Override
        protected Date doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<ElasticSearchParams> tracker = new ArrayList<>();


            // TODO Build the query

            //String query = "{ \"query\" : { \"term\" : { \"message\" : \""+ search_parameters[0] + "\"}}}";
            String query = "{ \"size\":1, \"query\" : {\n" +
                    "        \"match\" : { \"username\" : \"" + search_parameters[0] + "\" }\n" +
                    "    }\n" +
                    "}";


            Search search = new Search.Builder(query)
                    .addIndex("cmput301f18t09test")
                    .addType("Tracker")
                    .build();

            try {
                // TODO get the results of the query
                SearchResult result = client.execute(search);
                if (result.isSucceeded()) {

                    Log.i("Read","Read success");

                    List<ElasticSearchParams> foundusers = result.getSourceAsObjectList(ElasticSearchParams.class);
                    tracker.addAll(foundusers);

                    //Log.i("Read",Integer.toString(users.size()));
                   // Log.i("Read", tracker.get(0).GetTime());
                } else {
                    Log.i("Error", "The search query failed to find any tweets that matched");
                }
            } catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }
            try{
                return tracker.get(0).GetTime();
            }catch (Exception e){
                return new Date();
            }

        }
    }

    public static class ChangeProblemTask extends AsyncTask<Problem, Void, Void> {

        @Override
        protected Void doInBackground(Problem... users) {
            verifySettings();

            for (Problem user : users) {
                //String id = user.getUsername()+"1000001";
                String id = user.getId();
                Index index = new Index.Builder(user).index("cmput301f18t09test").type("problem").id(id).build();

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
