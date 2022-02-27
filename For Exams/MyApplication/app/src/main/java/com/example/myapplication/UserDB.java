package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * Code from Lecture + 2 new methods for getting
 * the timestamps and searching for existing user in the db.
 */


public class UserDB extends SQLiteOpenHelper {

    private SQLiteDatabase writableDB =this.getWritableDatabase(); //To write at the db
    private SQLiteDatabase readableDB =this.getReadableDatabase(); //To read from the db

    //To make the Table
    public static String DB_NAME = "ContactsDB";
    public static String TABLE_NAME = "Users";
    public static final String KEY_ID = "id";
    public static final String KEY_USERID = "userid";
    public static final String KEY_LONGITUDE = "longitude";
    public static final String KEY_LATITUDE = "latitude";
    public static final String KEY_TIMESTAMP = "dt";
    public static final String SQL_CREATE_QUERY = "CREATE TABLE "+TABLE_NAME+" ("+
            KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                KEY_USERID+" TEXT(10), "+
                KEY_LONGITUDE+" REAL, "+
                KEY_LATITUDE+" REAL, "+
                KEY_TIMESTAMP+" TEXT(20) "+
            ")";

    public UserDB(@Nullable Context context) {
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

    public  long insertInTable(User user){
        //Insert in the Table
        ContentValues values=new ContentValues();
        values.put(UserDB.KEY_USERID,user.getUserID());
        values.put(UserDB.KEY_LONGITUDE,user.getLongitude());
        values.put(UserDB.KEY_LATITUDE,user.getLatitude());
        values.put(UserDB.KEY_TIMESTAMP,user.getTimestamp());

        long id=writableDB.insert(TABLE_NAME,null,values);
        return id;
    }

    //Check if UserID+TimeStamp Combination Exists
    public User checkUser(String userID, String timeStamp){
        //We want to get all the values except from the PK (id) in the user
        Cursor cursor = readableDB.query(UserDB.TABLE_NAME, new String[]{UserDB.KEY_TIMESTAMP,UserDB.KEY_USERID,UserDB.KEY_LATITUDE,UserDB.KEY_LONGITUDE}, UserDB.KEY_TIMESTAMP+"=? AND "+UserDB.KEY_USERID+"=?",new String[]{timeStamp,userID},null,null,null,null);
        if (cursor.moveToFirst()){ //Check if we found a match and create the user
            User newUser=new User(cursor.getString(1),cursor.getString(3),cursor.getString(2),cursor.getString(0));
            return newUser;
        }else{ //if we didn't find the user return null
            return null;
        }
    }


    //Get all the timeStamps from the db(Works in the same way as the method above)
    public void getListFromDB(List<String> timeStamps){
        Cursor cursor=  readableDB.query(UserDB.TABLE_NAME,new String[]{UserDB.KEY_TIMESTAMP},null,null,null,null,null,null);
        if (cursor.moveToFirst()){
            do {
                timeStamps.add(cursor.getString(0));
            }while (cursor.moveToNext());
        }
    }
}
