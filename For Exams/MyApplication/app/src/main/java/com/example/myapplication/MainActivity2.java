package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {

    /**
     * A TextField, a spinner and a button
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        //1)Make an instance of the UserDB class
        UserDB db=new UserDB(MainActivity2.this);

        //2)Make a list to save all the timestamps as Strings
        List timeStamps=new ArrayList<String>();

        //3)Load the timeStamsp from the db
        db.getListFromDB(timeStamps);

        //4)if its empty it will simply go back to Activity1
        if (timeStamps.isEmpty()){
            Toast.makeText(this, "No TimeStamps Found", Toast.LENGTH_SHORT).show();
            Intent i =new Intent(this,MainActivity.class);
            startActivity(i);
        }

        //5)Set The dropdown(spinner)
        Spinner spinner =findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, timeStamps);
        spinner.setAdapter(adapter);

        EditText text=findViewById(R.id.editTextA2); //The user id

        //6)Button to move to the 3rd Activity
        Button continueButton =findViewById(R.id.GotoActivity3);
        continueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //6.1 Get the values in variables
                String userID=text.getText().toString();
                String timeStamp=spinner.getSelectedItem().toString();

                //6.2 Check if userID is empty
                if (userID.isEmpty()){
                    Toast.makeText(MainActivity2.this, "UserID is Empty!", Toast.LENGTH_SHORT).show();
                }else{

                    //Check the UserID with the public static method from the first activity
                    if (MainActivity.checkUserID(userID)){
                        Intent i=new Intent(MainActivity2.this,MainActivity3.class);

                        //Pass those two ,to the third Activity
                        i.putExtra("userID",userID);
                        i.putExtra("timeStamp",timeStamp);
                        startActivity(i);
                    }else{
                        Toast.makeText(MainActivity2.this, "Can't have whitespaces in UserID!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //7)Exams Button
        Button examsButton =findViewById(R.id.exetash);
        examsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //7.1 Get the value of userID in a variable
                String userID=text.getText().toString();

                //7.2 Check if userID is empty
                if (userID.isEmpty()){
                    Toast.makeText(MainActivity2.this, "UserID is Empty!", Toast.LENGTH_SHORT).show();
                }else{

                    //Check the UserID with the public static method from the first activity
                    if (MainActivity.checkUserID(userID)){

                        //7.3 Open the the new app
                        Toast.makeText(MainActivity2.this, "Redirecting to new app!", Toast.LENGTH_SHORT).show();

                        Intent i=getPackageManager().getLaunchIntentForPackage("com.example.examsapp");
                        i.putExtra("userID",userID);
                        startActivity(i);

                    }else{
                        Toast.makeText(MainActivity2.this, "Can't have whitespaces in UserID!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }
}
