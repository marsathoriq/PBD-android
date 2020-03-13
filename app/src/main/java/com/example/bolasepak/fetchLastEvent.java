package com.example.bolasepak;

import android.os.AsyncTask;
import android.text.format.DateFormat;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class fetchLastEvent extends AsyncTask<String, Void, String> {
    private TextView mEvent, mDate, mHomeName, mAwayName, mHomeScore, mAwayScore;
    private ImageView mHomeImage, mAwayImage;

    public fetchLastEvent(TextView mEvent, TextView mDate, TextView mHomeName, TextView mAwayName, TextView mHomeScore, TextView mAwayScore, ImageView mHomeImage, ImageView mAwayImage) {
        this.mEvent = mEvent;
        this.mDate = mDate;
        this.mHomeName = mHomeName;
        this.mAwayName = mAwayName;
        this.mHomeScore = mHomeScore;
        this.mAwayScore = mAwayScore;
        this.mHomeImage = mHomeImage;
        this.mAwayImage = mAwayImage;
    }

    @Override
    protected String doInBackground(String... strings) {
        return utility.getTeamLastEvent(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray eventsArray = jsonObject.getJSONArray("events");

            for (int i = 0; i < eventsArray.length(); i++){
                JSONObject event = eventsArray.getJSONObject(i);
                String eventId = null;
                String homeName = null;
                String awayName = null;
                String homeId = null;
                String awayId = null;
                String homeScore = null;
                String awayScore = null;
                String date = null;

                try {
                    eventId = event.getString("idEvent");
                    date = event.getString("strDate");
                    homeName = event.getString("strHomeTeam");
                    awayName = event.getString("strAwayTeam");
                    homeId = event.getString("idHomeTeam");
                    awayId = event.getString("idAwayTeam");
                    homeScore = event.getString("intHomeScore");
                    awayScore = event.getString("intAwayScore");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new fetchTeamImage(mHomeImage).execute(homeId);
                new fetchTeamImage(mAwayImage).execute(awayId);

                Date tempDate = new SimpleDateFormat("dd/MM/yy").parse(date);

                date = new SimpleDateFormat("dd MMMM yyyy").format(tempDate);

                mDate.setText(date);
                mHomeName.setText(homeName);
                mAwayName.setText(awayName);

                if(mHomeScore != null) {

                    mHomeScore.setText(homeScore);
                    mAwayScore.setText(awayScore);

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
