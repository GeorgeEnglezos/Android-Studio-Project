package com.example.batterycheck;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.util.Log;
import android.widget.Toast;

public class PowerConnectionReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())) {
            Toast.makeText(context, "Connected", Toast.LENGTH_SHORT).show();
        }else if(Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())){
            Toast.makeText(context, "Disconected", Toast.LENGTH_SHORT).show();
        }
    }
}