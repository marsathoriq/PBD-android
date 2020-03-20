package com.example.bolasepak;


import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchTeamFuture extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... strings) {
        return com.example.bolasepak.NetworkUtilsParam.getData(strings[0],"https://www.thesportsdb.com/api/v1/json/1/eventsnext.php?","id");
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray eventsArray = jsonObject.getJSONArray("events");

            for (int i = 0; i < 5; i++){
                Match match = new Match();
                JSONObject event = eventsArray.getJSONObject(i);
                String homeName = null;
                String awayName = null;
                String homeId = null;
                String awayId = null;
                String homeScore = null;
                String awayScore = null;
                String date = null;
                String idEvent = null;
                //String homeLogo = null;
                //awayLogo = null;

                try {
                    date = event.getString("dateEvent");
                    homeName = event.getString("strHomeTeam");
                    awayName = event.getString("strAwayTeam");
                    homeId = event.getString("idHomeTeam");
                    awayId = event.getString("idAwayTeam");
                    homeScore = event.getString("intHomeScore");
                    awayScore = event.getString("intAwayScore");
                    idEvent = event.getString("idEvent");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                new FetchImageTeamHome().execute(homeId);
                new FetchImageTeamHome().execute(awayId);
                //awayLogo=fAway.getUrlLogo();

                //  Date tempDate = new SimpleDateFormat("dd/MM/yy").parse(date);

                //  date = new SimpleDateFormat("dd MMMM yyyy").format(tempDate);
                match.setEvent(idEvent);
                match.setSchedule(date);
                match.setTeamHome(homeName);
                match.setTeamAway(awayName);
                match.setIdHome(homeId);
                match.setIdAway(awayId);
                //match.setLogoHome(HomePage.map.get(homeId));
                //match.setLogoAway(HomePage.map.get(awayId));
                if(homeScore == "null") {
                    match.setScoreHome(" ");
                    match.setScoreAway(" ");
                }else{
                    match.setScoreHome(homeScore);
                    match.setScoreAway(awayScore);
                }
                //System.out.println(match.getTeamHome());
                com.example.bolasepak.HomePage.Mdata.add(match);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

