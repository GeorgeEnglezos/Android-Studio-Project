package com.example.project2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class LocationDB extends SQLiteOpenHelper {

        private SQLiteDatabase writableDB =this.getWritableDatabase(); //To write at the db
        private SQLiteDatabase readableDB =this.getReadableDatabase(); //To read from the db

        //To make the Table
        public static String DB_NAME = "Locations_DB";
        public static String TABLE_NAME = "Locations";
        public static final String KEY_ID = "id";
        //public static final String KEY_USERID = "userid";
        public static final String KEY_LONGITUDE = "longitude";
        public static final String KEY_LATITUDE = "latitude";
        public static final String KEY_TIMESTAMP = "dt";
        public static final String SQL_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" ("+
                KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_LONGITUDE+" REAL, "+
                KEY_LATITUDE+" REAL, "+
                KEY_TIMESTAMP+" TEXT(20) "+
                ")";

    public LocationDB(@Nullable Context context) {
            super(context, DB_NAME, null, 1);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            //Once we open the app we have to make the table
            db.execSQL(SQL_CREATE_QUERY);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
}
