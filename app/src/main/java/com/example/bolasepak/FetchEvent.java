package com.example.bolasepak;

import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FetchEvent extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchEvent.class.getSimpleName();
    private TextView mDate, mTime, mWeather, mHomeName, mAwayName, mHomeScore, mAwayScore, mHomeShots, mAwayShots, mHomeGoals, mAwayGoals, homeId, awayId;
    private ImageView mHomeImage, mAwayImage;

    public FetchEvent(TextView homeId, TextView awayId,
                      TextView mDate, TextView mTime, TextView mWeather,
                      TextView mHomeName, TextView mAwayName, TextView mHomeScore, TextView mAwayScore,
                      TextView mHomeShots, TextView mAwayShots, TextView mHomeGoals, TextView mAwayGoals,
                      ImageView mHomeImage, ImageView mAwayImage){
        this.homeId = homeId;
        this.awayId = awayId;

        this.mDate = mDate;
        this.mTime = mTime;
        this.mWeather = mWeather;

        this.mHomeName = mHomeName;
        this.mAwayName = mAwayName;
        this.mHomeScore = mHomeScore;
        this.mAwayScore = mAwayScore;
        this.mHomeShots = mHomeShots;
        this.mAwayShots = mAwayShots;
        this.mHomeGoals = mHomeGoals;
        this.mAwayGoals = mAwayGoals;

        this.mHomeImage = mHomeImage;
        this.mAwayImage = mAwayImage;
    }

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtils.getEventInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray eventsArray = jsonObject.getJSONArray("events");

            for (int i = 0; i < eventsArray.length(); i++){
                JSONObject event = eventsArray.getJSONObject(i);
                String homeName = null;
                String awayName = null;
                String homeId = null;
                String awayId = null;
                String homeScore = null;
                String awayScore = null;
                String homeShots = null;
                String awayShots = null;
                String homeGoals = null;
                String awayGoals = null;
                String date = null;
                String time = null;

                try {
                    date = event.getString("strDate");
                    time = event.getString("strTime");
                    homeName = event.getString("strHomeTeam");
                    awayName = event.getString("strAwayTeam");
                    homeId = event.getString("idHomeTeam");
                    awayId = event.getString("idAwayTeam");
                    homeScore = event.getString("intHomeScore");
                    awayScore = event.getString("intAwayScore");
                    homeShots = event.getString("intHomeShots");
                    awayShots = event.getString("intAwayShots");
                    homeGoals = event.getString("strHomeGoalDetails");
                    awayGoals = event.getString("strAwayGoalDetails");

                } catch (Exception e) {
                    e.printStackTrace();
                }
                this.homeId.setText(homeId);
                this.awayId.setText(awayId);

                new FetchImageTeam(mHomeImage).execute(homeId);
                new FetchImageTeam(mAwayImage).execute(awayId);

                Date tempDate = new SimpleDateFormat("dd/MM/yy").parse(date);
                Date tempTime = new SimpleDateFormat("HH:mm:ss").parse(time);

                date = new SimpleDateFormat("dd MMMM yyyy").format(tempDate);
                time = new SimpleDateFormat("HH:mm").format(tempTime);

                mDate.setText(date);
                mTime.setText(time);
                mHomeName.setText(homeName);
                mAwayName.setText(awayName);

                if(mHomeScore != null) {

                    mHomeScore.setText(homeScore);
                    mAwayScore.setText(awayScore);
                    mHomeShots.setText(homeShots);
                    mAwayShots.setText(awayShots);

                    homeGoals = homeGoals.replace(";", "\n");
                    awayGoals = awayGoals.replace(";", "\n");

                    mHomeGoals.setText(homeGoals);
                    mAwayGoals.setText(awayGoals);

                } else {
                    Date today = new Date();

                    long diff = (tempDate.getTime()+ tempTime.getTime()) - today.getTime();
                    long diffDays = diff / (24 * 60 * 60 * 1000);

                    if (diffDays > 4) {
                        mWeather.setText("Have not been predicted yet");
                    } else {
                        new FetchEventLocation(mWeather, tempDate, tempTime).execute(homeId);
                    }
                }

                return;
            }

            mHomeName.setText("No Result Found");
        } catch (Exception e) {
            mHomeName.setText("not_found");
            mAwayName.setText("too");
            e.printStackTrace();
        }
    }
}
