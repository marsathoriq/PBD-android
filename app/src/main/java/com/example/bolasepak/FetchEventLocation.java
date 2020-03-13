package com.example.bolasepak;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;

public class FetchEventLocation extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchEventLocation.class.getSimpleName();
    private TextView mWeather;
    private Date date, time;

    public FetchEventLocation(TextView mWeather, Date date, Date time){
        this.mWeather = mWeather;
        this.date = date;
        this.time = time;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getTeamInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray eventsArray = jsonObject.getJSONArray("teams");

            for (int i = 0; i < eventsArray.length(); i++){
                JSONObject team = eventsArray.getJSONObject(i);
                String location = null;

                try {
                    location = team.getString("strStadiumLocation");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(location != null) {
                    String tempLocation[] = location.split(",");
                    location = tempLocation[0];
                    Log.d(TAG, location);
                    new FetchWeather(mWeather, date, time).execute(location);
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
