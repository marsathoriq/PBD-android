package com.example.bolasepak;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class EventDetail extends AppCompatActivity {
    private static final String TAG = EventDetail.class.getSimpleName();
    private TextView mDate, mTime, mHomeName, mAwayName, mHomeScore, mAwayScore, mHomeShots, mAwayShots, mHomeGoals, mAwayGoals, homeId, awayId;
    private ImageView mHomeImage, mAwayImage;
    dbSubscribe subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        subscribe = new dbSubscribe(this);

        setContentView(R.layout.activity_event_detail);

        mDate = (TextView) findViewById(R.id.tanggal);
        mTime = (TextView) findViewById(R.id.jam);

        mHomeName = (TextView) findViewById(R.id.homeName);
        mAwayName = (TextView) findViewById(R.id.awayName);
        mHomeScore = (TextView) findViewById(R.id.homeScore);
        mAwayScore = (TextView) findViewById(R.id.awayScore);
        mHomeShots = (TextView) findViewById(R.id.homeShots);
        mAwayShots = (TextView) findViewById(R.id.awayShots);
        mHomeGoals = (TextView) findViewById(R.id.homeGoals);
        mAwayGoals = (TextView) findViewById(R.id.awayGoals);

        mHomeImage = (ImageView) findViewById(R.id.homeImage);
        mAwayImage = (ImageView) findViewById(R.id.awayImage);

        homeId = (TextView) findViewById(R.id.homeId);
        awayId = (TextView) findViewById(R.id.awayId);
        this.searchEvent();
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, HomePage.class);

        startActivity(intent);
    }

    public  void searchEvent(){
        String queryString = getIntent().getExtras().getString("id");

        //for checking the network state and empty search field case
        ConnectivityManager connMngr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();

        if(queryString.length() == 0) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else if ((networkInfo != null) && networkInfo.isConnected() && (queryString.length() != 0)) {
            new FetchEvent(homeId, awayId, mDate, mTime, null,mHomeName, mAwayName, mHomeScore, mAwayScore, mHomeShots, mAwayShots, mHomeGoals, mAwayGoals, mHomeImage, mAwayImage).execute(queryString);
        } else {
            Toast.makeText(this, "please check yout network connection and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToTeam(View view) {
        //Toast.makeText(this, this.homeId.getText().toString(), Toast.LENGTH_SHORT).show();
        //this.dbInput();
        Intent intent = new Intent(this, teamDetailDone.class);
        startActivity(intent);
    }

    public void dbInput(View view) {

        String team_id = "133604";
        JSONArray data = subscribe.getData();
        try {
            for (int i = 0 ; i < data.length(); i++) {
                JSONObject row = data.getJSONObject(i);
                String dataTeaam = row.getString("Team");
                Log.d(TAG, dataTeaam);
                if(team_id.equals(dataTeaam)) {
                    Intent notificationIntent = new Intent( this, MyNotificationPublisher. class ) ;
                    PendingIntent pendingIntent = PendingIntent. getBroadcast ( this, 0 , notificationIntent , PendingIntent. FLAG_NO_CREATE ) ;
                    AlarmManager alarmManager = (AlarmManager) getSystemService(Context. ALARM_SERVICE ) ;

                    try {
                        alarmManager.cancel(pendingIntent);
                        Log.d(TAG, "Cancelled");
                    } catch (Exception e) {
                        Log.e(TAG, "AlarmManager update was not canceled. " + e.toString());
                    }

                    long id = subscribe.delete(team_id);
                    if(id<=0) {
                        Message.message(getApplicationContext(), "Delete Unsuccessful");
                    } else {
                        Message.message(getApplicationContext(),"Delete Successful");
                    }

                    return;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        long id = subscribe.insertData(team_id);
        new FetchNextEvent(subscribe, this, true).execute(team_id);

        if(id<=0) {
            Message.message(getApplicationContext(), "Insertion Unsuccessful");
        } else {
            Message.message(getApplicationContext(),"Insertion Successful");
        }

    }

    public void viewdata(View view)
    {
        JSONArray data = subscribe.getData();
        Message.message(this,data.toString());
    }
}
