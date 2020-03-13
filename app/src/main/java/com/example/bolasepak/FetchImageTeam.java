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

public class FetchImageTeam extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchImageTeam.class.getSimpleName();
    private ImageView mImage;

    public FetchImageTeam(ImageView mImage){
        this.mImage = mImage;
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
                String teamImage = null;

                try {
                    teamImage = team.getString("strTeamBadge");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                if(teamImage != null) {
                    Picasso.get().load(teamImage).into(mImage);
                    return;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
