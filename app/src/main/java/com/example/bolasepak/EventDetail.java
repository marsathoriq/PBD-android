package com.example.bolasepak;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class EventDetail extends AppCompatActivity {
    private static final String TAG = EventDetail.class.getSimpleName();
    private TextView mDate, mTime, mHomeName, mAwayName, mHomeScore, mAwayScore, mHomeShots, mAwayShots, mHomeGoals, mAwayGoals;
    private ImageView mHomeImage, mAwayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
        this.searchEvent();
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    public  void searchEvent(){
        String queryString = "441613";

        //for checking the network state and empty search field case
        ConnectivityManager connMngr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();

        if(queryString.length() == 0) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else if ((networkInfo != null) && networkInfo.isConnected() && (queryString.length() != 0)) {
            new FetchEvent(mDate, mTime, null,mHomeName, mAwayName, mHomeScore, mAwayScore, mHomeShots, mAwayShots, mHomeGoals, mAwayGoals, mHomeImage, mAwayImage).execute(queryString);
        } else {
            Toast.makeText(this, "please check yout network connection and try again", Toast.LENGTH_SHORT).show();
        }
    }
}
