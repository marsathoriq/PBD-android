package com.example.bolasepak;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {
    private static final String LOG_TAG = NetworkUtils.class.getSimpleName();

    private static final String EVENT_BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/lookupevent.php?";
    private static final String QUERY_EVENT_PARAM = "id"; // Parameter for the search string

    private static final String TEAM_BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?";
    private static final String QUERY_TEAM_PARAM = "id"; // Parameter for the search string

    private static final String WEATHER_BASE_URL = "http://api.openweathermap.org/data/2.5/forecast?";
    private static final String QUERY_WEATHER_PARAM = "q"; // Parameter for the search string
    private static final String ID_WEATHER_PARAM = "appid"; // Parameter for the search string
    private static final String MY_APP_ID = "e3e8557df1779fb2613b38292ede7c2a"; // Parameter for the search string

    private static final String NEXT_EVENT_BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?";

    static String getEventInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try{
            //Build up your query URI
            Uri builtUri = Uri.parse(EVENT_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_EVENT_PARAM, queryString)
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

    static String getWeatherInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try{
            //Build up your query URI
            Uri builtUri = Uri.parse(WEATHER_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_WEATHER_PARAM, queryString)
                    .appendQueryParameter(ID_WEATHER_PARAM, MY_APP_ID)
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

    static String getNextEventInfo(String queryString) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try{
            //Build up your query URI
            Uri builtUri = Uri.parse(NEXT_EVENT_BASE_URL).buildUpon()
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
