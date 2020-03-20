package com.example.bolasepak;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtilsParam {
    private static final String LOG_TAG = NetworkUtilsParam.class.getSimpleName();
    private static final String EVENT_BASE_URL = "http://134.209.97.218:5050/api/v1/json/1/eventspastleague.php?";
    private static final String QUERY_EVENT_PARAM = "id"; // Parameter for the search string

    private static final String TEAM_BASE_URL = "http://134.209.97.218:5050/api/v1/json/1/lookupteam.php?";
    private static final String QUERY_TEAM_PARAM = "id"; // Parameter for the search string

    static String getData(String queryString, String base_url, String query) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {
            //Build up your query URI
            Uri builtUri = Uri.parse(base_url).buildUpon()
                    .appendQueryParameter(query, queryString)
                    .build();

            URL requestURL = new URL(builtUri.toString());

            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            //read response using an inputStream and a StringBuffer, then convert it to a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }

            reader = new BufferedReader(new InputStreamReader((inputStream)));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                //stream was empty
                return null;
            }
            bookJSONString = buffer.toString();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            Log.d(LOG_TAG, bookJSONString);
            return bookJSONString;
        }
    }
}
