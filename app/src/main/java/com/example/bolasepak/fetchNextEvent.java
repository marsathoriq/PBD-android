package com.example.bolasepak;

import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

public class fetchNextEvent extends AsyncTask<String, Void, String> {

    private TextView mEvent, eventName, homeId, homeName, awayId, awayName, homeScore, awayScore,  mDate;
    private ImageView mHomeImage, mAwayImage;

    public fetchNextEvent(TextView event, TextView eventName, TextView homeId, TextView homeName, TextView awayId, TextView awayName, TextView homeScore, TextView awayScore, TextView mDate, ImageView mHomeImage, ImageView mAwayImage) {
        this.mEvent = event;
        this.eventName = eventName;
        this.homeId = homeId;
        this.homeName = homeName;
        this.awayId = awayId;
        this.awayName = awayName;
        this.homeScore = homeScore;
        this.awayScore = awayScore;
        this.mDate = mDate;
        this.mHomeImage = mHomeImage;
        this.mAwayImage = mAwayImage;
    }

    @Override
    protected String doInBackground(String... strings) {
        return utility.getTeamNextEvent(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray eventsArray = jsonObject.getJSONArray("events");

            for (int i = 0; i < eventsArray.length(); i++){
                JSONObject event = eventsArray.getJSONObject(i);
                String mEvent = null;
                String eventName = null;
                String homeId = null;
                String homeName = null;
                String awayId = null;
                String awayName = null;
                String homeScore = null;
                String awayScore = null;
                String mDate = null;
                String date = null;

                try {
                    mEvent = event.getString("idEvent");
                    eventName = event.getString("strEvent");
                    homeId = event.getString("idHomeTeam");
                    homeName = event.getString("strHomeTeam");
                    awayId = event.getString("idAwayTeam");
                    awayName = event.getString("strAwayTeam");
                    homeScore = event.getString("intHomeScore");
                    awayScore = event.getString("intAwayScore");
                    mDate = event.getString("dateEvent");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new fetchTeamImage(mHomeImage).execute(homeId);
                new fetchTeamImage(mAwayImage).execute(awayId);

                Date tempDate = new SimpleDateFormat("dd/MM/yy").parse(mDate);

                date = new SimpleDateFormat("dd MMMM yyyy").format(tempDate);

                mDate.setText(date);
                homeName.setText(strHomeTeam);
                awayName.setText(strAwayTeam);

                if(homeScore != null) {

                    homeScore.setText(intHomeScore);
                    awayScore.setText(intAwayScore);
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
