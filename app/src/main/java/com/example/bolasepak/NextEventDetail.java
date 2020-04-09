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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class NextEventDetail extends AppCompatActivity {
    private static final String TAG = NextEventDetail.class.getSimpleName();
    private String id;
    private TextView mDate, mTime, mWeather, mHomeName, mAwayName, homeId, awayId;
    private ImageView mHomeImage, mAwayImage;
    dbSubscribe subscribe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_event_detail);
        subscribe = new dbSubscribe(this);

        mDate = (TextView) findViewById(R.id.tanggal);
        mTime = (TextView) findViewById(R.id.jam);
        mWeather = (TextView) findViewById(R.id.weather);
        mHomeName = (TextView) findViewById(R.id.homeName);
        mAwayName = (TextView) findViewById(R.id.awayName);

        mHomeImage = (ImageView) findViewById(R.id.homeImage);
        mAwayImage = (ImageView) findViewById(R.id.awayImage);

        Intent get = getIntent();
        this.id = get.getStringExtra("id");

        homeId = (TextView) findViewById(R.id.homeId);
        awayId = (TextView) findViewById(R.id.awayId);

        Button awayButton = findViewById(R.id.subscribeAway);
        Button homeButton = findViewById(R.id.subscribeHome);

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
            new FetchEvent(homeId, awayId, mDate, mTime, mWeather, mHomeName, mAwayName, null, null, null, null, null, null, mHomeImage, mAwayImage).execute(queryString);
        } else {
            Toast.makeText(this, "please check yout network connection and try again", Toast.LENGTH_SHORT).show();
        }
    }

    public void goToHomeTeam(View view) {
        //Toast.makeText(this, this.homeId.getText().toString(), Toast.LENGTH_SHORT).show();
        //this.dbInput();
        Intent intent = new Intent(this, teamDetailDone.class);
        intent.putExtra("id", this.homeId.getText().toString());
        startActivity(intent);
    }

    public void goToAwayTeam(View view) {
        //Toast.makeText(this, this.homeId.getText().toString(), Toast.LENGTH_SHORT).show();
        //this.dbInput();
        Intent intent = new Intent(this, teamDetailDone.class);
        intent.putExtra("id", this.awayId.getText().toString());
        startActivity(intent);
    }

    public void awaySubscribe(View view){
        Button awayButton = findViewById(R.id.subscribeAway);
        if (awayButton.getText().toString().equals("Subscribe")){
            awayButton.setText("Unsubscribe");
        } else {
            awayButton.setText("Subscribe");
        }

        this.dbInput(this.awayId.getText().toString());
    }

    public void homeSubscribe(View view){
        Button homeButton = findViewById(R.id.subscribeHome);
        if (homeButton.getText().toString().equals("Subscribe")){
            homeButton.setText("Unsubscribe");
        } else {
            homeButton.setText("Subscribe");
        }

        this.dbInput(this.homeId.getText().toString());
    }

    public void dbInput(String team_id) {
        Log.d(TAG, team_id);
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
                        Message.message(getApplicationContext(), "Unsubscribe Unsuccessful");
                    } else {
                        Message.message(getApplicationContext(),"Unsubscribe Successful");
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
            Message.message(getApplicationContext(), "Subscribe Unsuccessful");
        } else {
            Message.message(getApplicationContext(),"Subscribe Successful");
        }

    }

    public void viewdata(View view)
    {
        JSONArray data = subscribe.getData();
        Message.message(this,data.toString());
    }
}
