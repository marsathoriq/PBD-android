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

public class teamDetail extends AppCompatActivity {
    private static final String TAG = teamDetail.class.getSimpleName();
    private TextView mDate, mEvent, mHomeName, mAwayName, mHomeScore, mAwayScore;
    private ImageView mHomeImage, mAwayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_detail);

        mDate = (TextView) findViewById(R.id.MatchSchedule);

        mHomeName = (TextView) findViewById(R.id.TeamHome);
        mAwayName = (TextView) findViewById(R.id.TeamAway);
        mHomeScore = (TextView) findViewById(R.id.ScoreHome);
        mAwayScore = (TextView) findViewById(R.id.ScoreAway);

        mHomeImage = (ImageView) findViewById(R.id.LogoHome);
        mAwayImage = (ImageView) findViewById(R.id.LogoAway);
        //this.searchEvent();
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

//    public  void searchEvent(){
//        String queryString = "441613";
//
//        //for checking the network state and empty search field case
//        ConnectivityManager connMngr = (ConnectivityManager)
//                getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();
//
//        if(queryString.length() == 0) {
//            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
//        } else if ((networkInfo != null) && networkInfo.isConnected() && (queryString.length() != 0)) {
//            new FetchNextEvent(mDate, mEvent, mHomeName, mAwayName, mHomeScore, mAwayScore, mHomeImage, mAwayImage).execute(queryString);
//        } else {
//            Toast.makeText(this, "please check yout network connection and try again", Toast.LENGTH_SHORT).show();
//        }
//    }
}
