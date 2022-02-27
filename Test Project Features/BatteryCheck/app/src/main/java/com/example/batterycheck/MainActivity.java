package com.example.batterycheck;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.BatteryManager;
import android.os.Bundle;

import java.util.logging.Filter;

public class MainActivity extends AppCompatActivity {

    PowerConnectionReceiver powerConnectionReceiver = new PowerConnectionReceiver();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter filter =new IntentFilter();
        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(powerConnectionReceiver,filter);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(powerConnectionReceiver);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(powerConnectionReceiver);
    }
}