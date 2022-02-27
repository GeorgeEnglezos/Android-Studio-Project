package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Instance of the SQL Class
        UserDB helper = new UserDB(MainActivity.this);

        //First Button
        Button saveButton =findViewById(R.id.SaveUserButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //saving all the TextFields to Variables
                String timeStamp; //To save the textview of the timeStamp later
                String userId=((EditText) findViewById(R.id.editTextA2)).getText().toString();
                String longitude=((EditText) findViewById(R.id.editTextTextPersonName3)).getText().toString();
                String latitude=((EditText) findViewById(R.id.editTextTextPersonName4)).getText().toString();

                //Chcecking if fields are empty
                if (userId.isEmpty()||longitude.isEmpty()||latitude.isEmpty()){
                    Toast.makeText(MainActivity.this,"Empty Fields!", Toast.LENGTH_SHORT).show();
                } else{
                    /**
                     * If the fields aren't empty i have to check if the userid has " " in it  .
                     * The other two textFields had the TextField type specified for decimal numbers only so we can't have any errors there
                     *         android:inputType="numberDecimal"
                     * The UserID TextField only allows up to 10 characters in it (DB restrictions).
                     * The Timestamp is exactly 20 characters.(DB restriction).
                     */
                    if (checkUserID(userId)){
                        Toast.makeText(MainActivity.this,"All Ok", Toast.LENGTH_SHORT).show();

                        /**Date:
                         * https://stackabuse.com/how-to-get-current-date-and-time-in-java/
                         */
                        Date date = new Date(); // This object contains the current date value
                        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
                        timeStamp=formatter.format(date);
                        ((TextView)findViewById(R.id.textView)).setText(timeStamp);

                        //Insert in the Table
                        User newUser=new User(userId,longitude,latitude,timeStamp);
                        helper.insertInTable(newUser);

                    }
                    else{
                        Toast.makeText(MainActivity.this, "Can't have blank characters in UserID!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //Second Button to load the second Activity
        Button continueButton =findViewById(R.id.Activity2Button);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(MainActivity.this,MainActivity2.class);
                startActivity(i);

            }
        });
    }

    /**
     * To check if the userid has any spaces and return false
     * I use .charAt() to take all the characters from the String
     */
    static public boolean checkUserID(String string){
        for(int i=0;i<string.length();i++){
            if (string.charAt(i)==(' ')){
                return false;
            }
        }
        return true;
    }
}