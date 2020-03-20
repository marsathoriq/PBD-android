package com.example.bolasepak;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchTeam extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        return com.example.bolasepak.NetworkUtilsParam.getData(strings[0],"https://www.thesportsdb.com/api/v1/json/1/searchteams.php?","t");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray teamsArray = jsonObject.getJSONArray("teams");
            for (int i = 0; i < 1; i++){
                JSONObject event = teamsArray.getJSONObject(i);
                String teamId = null;
                //String homeLogo = null;
                //awayLogo = null;

                try {
                    teamId = event.getString("idTeam");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                new FetchTeamFuture().execute(teamId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
