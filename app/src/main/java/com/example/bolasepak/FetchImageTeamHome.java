package com.example.bolasepak;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import com.squareup.picasso.Picasso;

public class FetchImageTeamHome extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        return NetworkUtilsParam.getData(strings[0],"https://www.thesportsdb.com/api/v1/json/1/lookupteam.php?","id");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray eventsArray = jsonObject.getJSONArray("teams");

            for (int i = 0; i < eventsArray.length(); i++){
                JSONObject team = eventsArray.getJSONObject(i);
                String teamImage = null;

                try {
                    teamImage = team.getString("strTeamBadge");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                HomePage.LogoTeam.add(teamImage);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
