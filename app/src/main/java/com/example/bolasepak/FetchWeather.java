package com.example.bolasepak;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FetchWeather extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchWeather.class.getSimpleName();
    private TextView mWeather;
    private Date date, time;

    public FetchWeather(TextView mWeather, Date date, Date time){
        this.mWeather = mWeather;
        this.date = date;
        this.time = time;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getWeatherInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray weathersArray = jsonObject.getJSONArray("list");
            Date today = new Date();

            long diff = (date.getTime()+ time.getTime()) - today.getTime();

            long diffHours = diff / (60 * 60 * 1000) % 24;
            long diffDays = diff / (24 * 60 * 60 * 1000);
            int index = (int) diffDays * 9 + ( (int) diffHours / 3) + 1;
            JSONObject data = weathersArray.getJSONObject(index);
            String weather = null;

            try {
                weather = data.getJSONArray("weather").getJSONObject(0).getString("description");
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(weather != null) {
                Log.d(TAG, weather);
                mWeather.setText(weather);
                return;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

