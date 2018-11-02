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


public class ElasticsearchProblemController {
    private static JestDroidClient client;


    public static class AddProblemsTask extends AsyncTask<Problem, Void, Void> {

        @Override
        protected Void doInBackground(Problem... problems) {
            verifySettings();

            for (Problem problem : problems) {
                Index index = new Index.Builder(problem).index("cmput301f18t09").type("problem").build();

                try {
                    DocumentResult result = client.execute(index);
                    if (result.isSucceeded()){

                    }else{
                        Log.e("Error","Elastic search was not able to add the problem");
                    }
                }
                catch (Exception e) {
                    Log.i("Error", "The application failed to build and send the problem");
                }

            }
            return null;
        }
    }

    public static class GetProblemTask extends AsyncTask<String, Void, ArrayList<Problem>> {
        @Override
        protected ArrayList<Problem> doInBackground(String... search_parameters) {
            verifySettings();

            ArrayList<Problem> problems = new ArrayList<Problem>();

            Search search = new Search.Builder(search_parameters[0])
                    .addIndex("cmput301f18t09")
                    .addType("problem")
                    .build();


            try {
                SearchResult result = client.execute(search);
                if(result.isSucceeded()){
                    List<Problem> foundProblems =
                            result.getSourceAsObjectList(Problem.class);
                    problems.addAll(foundProblems);
                }else{
                    Log.e("Error","The search query failed to find any problems that matched.");
                }
            }
            catch (Exception e) {
                Log.i("Error", "Something went wrong when we tried to communicate with the elasticsearch server!");
            }

            return problems;
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