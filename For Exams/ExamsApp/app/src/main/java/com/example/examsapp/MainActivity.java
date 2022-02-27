package com.example.examsapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get the userID here

        Intent i=getIntent();
        if( getIntent().getExtras() == null){
            Toast.makeText(this, "No Intent found, open the other app first!", Toast.LENGTH_SHORT).show();
            TextView text =findViewById(R.id.exetasi);
            text.setText("No Intent Found!");
        }else{
            String textFromApp1=i.getStringExtra("userID");
            TextView text =findViewById(R.id.exetasi);
            text.setText(textFromApp1);
        }

    }
}