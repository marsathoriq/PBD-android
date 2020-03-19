package com.example.bolasepak;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HomePage extends AppCompatActivity {
    public static List<Match> Mdata;
    public static List<String> LogoTeam;
    public static Map<String,String> map=new HashMap<String,String>();
    //int Logo1[] = {R.drawable.mu_logo, R.drawable.mu_logo, R.drawable.mu_logo, R.drawable.mu_logo, R.drawable.mu_logo};
    //int Logo2[] = {R.drawable.liverpool_logo, R.drawable.liverpool_logo, R.drawable.liverpool_logo, R.drawable.liverpool_logo, R.drawable.liverpool_logo};
    String query;
    RecyclerView recyclerView;
    SearchView searchview;

    private RequestQueue mQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);

        //Toast.makeText(getApplicationContext(),"ck",Toast.LENGTH_SHORT).show();
        recyclerView = findViewById(R.id.recyclerView);
        Mdata = new ArrayList<>();
        LogoTeam = new ArrayList<>();

//        mQueue = Volley.newRequestQueue(this);
//        jsonParse();
        searchEvent();
        // jsonParse2();
        //  Toast.makeText(this, Mdata.get(1).getTeamHome(), Toast.LENGTH_SHORT).show();

        setuprecyclerview(Mdata,LogoTeam);
        searchview = findViewById(R.id.search_view);

        // Toast.makeText(this, Mdata.get(1).getTeamHome(), Toast.LENGTH_SHORT).show();
        //   setuprecyclerview(Mdata);
//        searchview.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                nTeam = new ArrayList<String>();
//                sQueue = Volley.newRequestQueue(this);
//                query = searchview.getQuery().toString();
//                jsonParseSearch();
//                if (nTeam.isEmpty()){
//
//                }else{
//                    for(int i = 0;i < nTeam.size();i++){
//                        jsonMatchTeam(nTeam[i]);
//                    }
//                }
//            }
//        });

        //Team1 = getResources().getStringArray(R.array.HomeTeam);
        //Team2 = getResources().getStringArray(R.array.AwayTeam);
        //Score1 = getResources().getStringArray(R.array.HomeScore);
        //Score2 = getResources().getStringArray(R.array.AwayScore);

    }
    //    private void jsonParse() {
//
//        String url = "https://www.thesportsdb.com/api/v1/json/1/lookup_all_teams.php?id=4328";
//
//        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        JSONArray jsonArray = null;
//                        try {
//                            jsonArray = response.getJSONArray("events");
//                            for (int i = 0; i < jsonArray.length(); i++) {
//                                JSONObject team = jsonArray.getJSONObject(i);
//                                map.put(team.getString("idTeam"),team.getString("strTeamBadge"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                error.printStackTrace();
//            }
//        });
//
//        mQueue.add(request);
//    }
    public void searchEvent(){
        String queryString = "4328";

        //for checking the network state and empty search field case
        ConnectivityManager connMngr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMngr.getActiveNetworkInfo();

        if(queryString.length() == 0) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        } else if ((networkInfo != null) && networkInfo.isConnected() && (queryString.length() != 0)) {
            new FetchEventPast().execute(queryString);
            new FetchEventFuture().execute(queryString);
            //Toast.makeText(this, Mdata.get(1).getTeamHome(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "please check yout network connection and try again", Toast.LENGTH_SHORT).show();
        }
    }


    private void setuprecyclerview(List<Match> Mdata,List<String> logo){
        MyAdapter myAdapter = new MyAdapter(this, Mdata,logo);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}