package com.example.bolasepak;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.app.NotificationCompat;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import static androidx.core.content.ContextCompat.getSystemService;

public class FetchNextEvent extends AsyncTask<String, Void, String> {
    private static final String TAG = FetchNextEvent.class.getSimpleName();
    private dbSubscribe subscribe;
    private String idTeam;
    private Context context;
    private Boolean isFirst;

    public FetchNextEvent(dbSubscribe subscribe, Context context, Boolean isFirst){
        this.subscribe = subscribe;
        this.context = context;
        this.isFirst = isFirst;
    }

    @Override
    protected String doInBackground(String... strings) {
        this.idTeam = strings[0];
        return NetworkUtils.getNextEventInfo(strings[0]);
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

        try {
            JSONObject jsonObject = new JSONObject(s);
            JSONArray eventsArray = jsonObject.getJSONArray("events");

            String date1 = null;
            String time1 = null;

            String date2 = null;
            String time2 = null;
            JSONObject event = eventsArray.getJSONObject(0);

            try {
                date1 = event.getString("strDate");
                time1 = event.getString("strTime");
            } catch (Exception e) {
                e.printStackTrace();
            }


            event = eventsArray.getJSONObject(1);

            try {
                date2 = event.getString("strDate");
                time2 = event.getString("strTime");
            } catch (Exception e) {
                e.printStackTrace();
            }

            subscribe.updateName(this.idTeam, date1+" "+time1, date2+" "+time2);
            String namaTeam = null;
            JSONArray dataSubscribe = subscribe.getData();
            for (int i = 0 ; i < dataSubscribe.length(); i++){
                try {
                    JSONObject baris = dataSubscribe.getJSONObject(i);
                    if (baris.getString("Team").equals(this.idTeam)) {
                        if (event.getString("idHomeTeam").equals(idTeam)){
                            namaTeam = event.getString("strHomeTeam");
                        } else {
                            namaTeam = event.getString("strAwayTeam");
                        }

                        String tempDate = null;
                        if (this.isFirst){
                            tempDate = baris.getString("DateSatu");
                        } else {
                            tempDate = baris.getString("DateDua");
                        }

                        Date date = new SimpleDateFormat("dd/MM/yy HH:mm:ss").parse(tempDate);
                        Date today = new Date();
                        long delay = date.getTime() - today.getTime();
                        Log.d(TAG, Long.toString(delay));
                        String isi = "tim " + namaTeam + " akan main beberapa saat lagi!";
                        scheduleNotification(getNotification( isi, this.idTeam) , delay) ;

                        return;
                    }
                } catch (Exception e){
                    e.printStackTrace();
                }

            }

            return;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Notification getNotification (String content, String team_id) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.context , "default" ) ;
        builder.setContentTitle( "Scheduled Notification" ) ;
        builder.setContentText(content) ;
        builder.setSmallIcon(R.drawable. ic_launcher_foreground ) ;
        builder.setAutoCancel( true ) ;
        builder.setChannelId( "10001" ) ;
        builder.setGroup(team_id);
        return builder.build() ;
    }

    private void scheduleNotification (Notification notification , long delay) {
        Intent notificationIntent = new Intent(this.context, MyNotificationPublisher. class ) ;
        Log.d(TAG, Boolean.toString(notificationIntent != null));
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION_ID , 1 ) ;
        notificationIntent.putExtra(MyNotificationPublisher. NOTIFICATION , notification) ;
        PendingIntent pendingIntent = PendingIntent. getBroadcast ( this.context, 0 , notificationIntent , PendingIntent. FLAG_UPDATE_CURRENT ) ;
        AlarmManager alarmManager = (AlarmManager) getSystemService(this.context, AlarmManager.class) ;
        assert alarmManager != null;
        long time = System.currentTimeMillis() + delay;
        alarmManager.set(AlarmManager.RTC_WAKEUP , time, pendingIntent); ;
        Log.d(TAG, Long.toString(delay));
    }
}