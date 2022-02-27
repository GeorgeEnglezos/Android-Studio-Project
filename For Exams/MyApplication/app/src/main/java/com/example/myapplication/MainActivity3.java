package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //The TextViews
        TextView chosenFrom2= findViewById(R.id.ChosenTextID);
        TextView userIdText= findViewById(R.id.userIdTextView);
        TextView latitudeID= findViewById(R.id.latitudeID);
        TextView longtitudeID= findViewById(R.id.longtitudeID);
        TextView timeStampID= findViewById(R.id.timeStampID);

        //Getting the values of Activity2
        String userID;
        String timeStamp;
        Intent i=getIntent();
        userID=i.getStringExtra("userID");
        timeStamp=i.getStringExtra("timeStamp");
        chosenFrom2.setText(chosenFrom2.getText()+userID+" "+timeStamp);

        //Checking the db for userID and TimeStamp combination
        UserDB db=new UserDB(MainActivity3.this);
        User thisUser=db.checkUser(userID,timeStamp);

        //If User Exists
        if(thisUser!=null){
            //Setting the values we got from the db
            timeStampID.setText(timeStampID.getText()+thisUser.getTimestamp());
            userIdText.setText(userIdText.getText()+thisUser.userID);
            latitudeID.setText(latitudeID.getText()+thisUser.latitude);
            longtitudeID.setText(longtitudeID.getText()+thisUser.getLongitude());
        }else{
            //The TextViews will stay "empty"
            Toast.makeText(this, "No Matching userID and TimeStamp combination!", Toast.LENGTH_SHORT).show();
        }
    }
}