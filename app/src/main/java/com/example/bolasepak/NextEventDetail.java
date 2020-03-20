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

public class NextEventDetail extends AppCompatActivity {
    private static final String TAG = NextEventDetail.class.getSimpleName();
    private String id;
    private TextView mDate, mTime, mWeather, mHomeName, mAwayName;
    private ImageView mHomeImage, mAwayImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_next_event_detail);

        mDate = (TextView) findViewById(R.id.tanggal);
        mTime = (TextView) findViewById(R.id.jam);
        mWeather = (TextView) findViewById(R.id.weather);
        mHomeName = (TextView) findViewById(R.id.homeName);
        mAwayName = (TextView) findViewById(R.id.awayName);

        mHomeImage = (ImageView) findViewById(R.id.homeImage);
        mAwayImage = (ImageView) findViewById(R.id.awayImage);

        Intent get = getIntent();
        this.id = get.getStringExtra("id");

        this.searchEvent();
    }

    public void goToMain(View view) {
        Intent intent = new Intent(this, HomePage.class);

        startActivity(intent);
    }

    public  void searchEvent(){
        String queryString = id;

        //for checking the network state and empty search field case
        ConnectivityManager connMngr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();

        if(queryString.length() == 0) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else if ((networkInfo != null) && networkInfo.isConnected() && (queryString.length() != 0)) {
            new FetchEvent(null, null, mDate, mTime, mWeather, mHomeName, mAwayName, null, null, null, null, null, null, mHomeImage, mAwayImage).execute(queryString);
        } else {
            Toast.makeText(this, "please check yout network connection and try again", Toast.LENGTH_SHORT).show();
        }
    }
}
