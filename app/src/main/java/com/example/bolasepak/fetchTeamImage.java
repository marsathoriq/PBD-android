package com.example.bolasepak;

import android.os.AsyncTask;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

public class fetchTeamImage extends AsyncTask<String, Void, String> {
    private static final String TAG = fetchTeamImage.class.getSimpleName();
    private ImageView mImage;

    public fetchTeamImage(ImageView mImage){
        this.mImage = mImage;
    }

    @Override
    protected String doInBackground(String... strings) {
        return utility.getTeamInfo(strings[0]);
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
                    teamImage = team.getString("strTeamLogo");
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
