package com.example.sqlitedbcontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.test)).setText("works");
        User user=new User("hello","52","36","12:00");
        User user2=new User("hello2","s52","36f","12:00");
        UserDB helper=new UserDB(this);
        helper.insertInTable(user);
        helper.insertInTable(user2);
    }
}