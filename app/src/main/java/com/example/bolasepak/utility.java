package com.example.bolasepak;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class utility {
    private static final String LOG_TAG = utility.class.getSimpleName();

    private static final String TEAM_NEXT_EVENT = "https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?id=133602";
    private static final String QUERY_NEXT_EVENT_PARAM = "id";

    private static final String TEAM_LAST_EVENT = "https://www.thesportsdb.com/api/v1/json/1/eventslast.php?id=133602";
    private static final String QUERY_LAST_EVENT_PARAM = "id";

    private static final String TEAM_BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?";
    private static final String QUERY_TEAM_PARAM = "id"; // Parameter for the search string

    static String getTeamNextEvent(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try{
            //Build up your query URI
            Uri builtUri = Uri.parse(TEAM_NEXT_EVENT).buildUpon()
                    .appendQueryParameter(QUERY_NEXT_EVENT_PARAM, queryString)
                    .build();

            URL requestURL = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //read response using an inputStream and a StringBuffer, then convert it to a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null){
                return null;
            }

            reader = new BufferedReader(new InputStreamReader((inputStream)));
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }

            if(buffer.length() == 0){
                //stream was empty
                return null;
            }
            bookJSONString = buffer.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(urlConnection!= null){
                urlConnection.disconnect();
            }

            if(reader != null){
                try {
                    reader.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            Log.d(LOG_TAG, bookJSONString);
            return bookJSONString;
        }
    }

    static String getTeamLastEvent(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try{
            //Build up your query URI
            Uri builtUri = Uri.parse(TEAM_LAST_EVENT).buildUpon()
                    .appendQueryParameter(QUERY_LAST_EVENT_PARAM, queryString)
                    .build();

            URL requestURL = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //read response using an inputStream and a StringBuffer, then convert it to a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null){
                return null;
            }

            reader = new BufferedReader(new InputStreamReader((inputStream)));
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }

            if(buffer.length() == 0){
                //stream was empty
                return null;
            }
            bookJSONString = buffer.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(urlConnection!= null){
                urlConnection.disconnect();
            }

            if(reader != null){
                try {
                    reader.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            Log.d(LOG_TAG, bookJSONString);
            return bookJSONString;
        }
    }

    static String getTeamInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try{
            //Build up your query URI
            Uri builtUri = Uri.parse(TEAM_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_TEAM_PARAM, queryString)
                    .build();

            URL requestURL = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //read response using an inputStream and a StringBuffer, then convert it to a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null){
                return null;
            }

            reader = new BufferedReader(new InputStreamReader((inputStream)));
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }

            if(buffer.length() == 0){
                //stream was empty
                return null;
            }
            bookJSONString = buffer.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if(urlConnection!= null){
                urlConnection.disconnect();
            }

            if(reader != null){
                try {
                    reader.close();
                } catch (Exception e){
                    e.printStackTrace();
                }
            }
            Log.d(LOG_TAG, bookJSONString);
            return bookJSONString;
        }
    }
}
