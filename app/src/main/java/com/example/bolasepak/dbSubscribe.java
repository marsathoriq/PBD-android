package com.example.bolasepak;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

public class dbSubscribe {
    dbSubscribe.SubscribeDbHelper myhelper;
    private static final String TAG = dbSubscribe.class.getSimpleName();
    public dbSubscribe(Context context)
    {
        myhelper = new dbSubscribe.SubscribeDbHelper(context);
    }

    public long insertData(String team)
    {
        SQLiteDatabase dbb = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(dbSubscribe.SubscribeDbHelper.TEAM, team);
        contentValues.put(SubscribeDbHelper.DATESATU, "test");
        contentValues.put(SubscribeDbHelper.DATEDUA, "test");
        Log.d(TAG, contentValues.toString());
        long id = dbb.insert(dbSubscribe.SubscribeDbHelper.TABLE_NAME, null , contentValues);
        return id;
    }

    public JSONArray getData()
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] columns = {dbSubscribe.SubscribeDbHelper.ID, dbSubscribe.SubscribeDbHelper.TEAM, SubscribeDbHelper.DATESATU, SubscribeDbHelper.DATEDUA};
        Cursor cursor =db.query(dbSubscribe.SubscribeDbHelper.TABLE_NAME,columns,null,null,null,null,null);
        StringBuffer buffer= new StringBuffer();
        JSONArray result = new JSONArray();

        while (cursor.moveToNext())
        {
            JSONObject row = new JSONObject();
            try {
                row.put(dbSubscribe.SubscribeDbHelper.ID, cursor.getInt(0));
                row.put(dbSubscribe.SubscribeDbHelper.TEAM, cursor.getString(1));
                row.put(SubscribeDbHelper.DATESATU, cursor.getString(2));
                row.put(SubscribeDbHelper.DATEDUA, cursor.getString(3));
            } catch (Exception e) {
                e.printStackTrace();
            }

            result.put(row);
        }
        return result;
    }

    public  int delete(String team)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        String[] whereArgs ={team};

        int count =db.delete(dbSubscribe.SubscribeDbHelper.TABLE_NAME , dbSubscribe.SubscribeDbHelper.TEAM+" = ?",whereArgs);
        return  count;
    }

    public int updateName(String team , String date1, String date2)
    {
        SQLiteDatabase db = myhelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(SubscribeDbHelper.DATESATU,date1);
        contentValues.put(SubscribeDbHelper.DATEDUA,date2);
        String[] whereArgs= {team};
        int count =db.update(dbSubscribe.SubscribeDbHelper.TABLE_NAME,contentValues, dbSubscribe.SubscribeDbHelper.TEAM+" = ?",whereArgs );
        return count;
    }

    static class SubscribeDbHelper extends SQLiteOpenHelper
    {
        private static final String DATABASE_NAME = "DATABASE";    // Database Name
        private static final String TABLE_NAME = "SUBSCRIBE";   // Table Name
        private static final int DATABASE_Version = 6;    // Database Version
        private static final String ID="Team_ID";     // Column I (Primary Key)
        private static final String TEAM = "Team";    //Column II
        private static final String DATESATU = "DateSatu";
        private static final String DATEDUA = "DateDua";
        private static final String CREATE_TABLE = "CREATE TABLE "+TABLE_NAME+" ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+TEAM+" VARCHAR(225), "+DATESATU+" VARCHAR(225), "+DATEDUA+" VARCHAR(225));";
        private static final String DROP_TABLE ="DROP TABLE IF EXISTS "+TABLE_NAME;
        private Context context;

        public SubscribeDbHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_Version);
            this.context=context;
        }

        public void onCreate(SQLiteDatabase db) {

            try {
                db.execSQL(CREATE_TABLE);
            } catch (Exception e) {
                Message.message(context,""+e);
            }
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            try {
                Message.message(context,"OnUpgrade");
                db.execSQL(DROP_TABLE);
                onCreate(db);
            }catch (Exception e) {
                Message.message(context,""+e);
            }
        }

    }
}