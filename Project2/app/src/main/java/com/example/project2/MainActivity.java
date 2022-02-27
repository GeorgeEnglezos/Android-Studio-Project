package com.example.project2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

import android.net.Uri;
import android.os.BatteryManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity{
//TODO The last method is the problem,remove it and it works but probably makes multiple similar services
    TextView text; //Battery Status
    Button button; //Button for location
    IntentFilter intentFilter;
    private BroadcastReceiver receiver;

    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        i=new Intent (this,LocationService.class);

        //Get the ids
        text = findViewById(R.id.BatteryStatus);
        button = findViewById(R.id.CheckLastLocation);
        TextView text2=findViewById(R.id.lat);
        TextView text3=findViewById(R.id.lon);

        //RunTime permissions
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ((TextView) findViewById(R.id.error)).setText("Need Location Permission!");
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},7);
            return;
        }else{
            //It will remove the textView
            ((TextView) findViewById(R.id.error)).setText("");
        }

        // Button για να γεμίζω τα TextViews με τα τελευταία lat και lon (Το έφτιαξα κυρίως για Debugging)
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ContentResolver for the DB
                Cursor cursor= getContentResolver().query(Uri.parse("content://locations_db/locations"),new String[]{"latitude","longitude"},null,null,null);
                if (cursor.moveToLast()){
                    Toast.makeText(MainActivity.this, "Not null", Toast.LENGTH_SHORT).show();
                    text2.setText("lat:"+cursor.getString(0));
                    text3.setText("lon:"+cursor.getString(1));
                }else{
                    Toast.makeText(MainActivity.this, "No Locations in DB yet", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Check onCreate if phone is charging
        if (isConnected()){
            Toast.makeText(this, "Charging", Toast.LENGTH_SHORT).show();
            text.setText("Charging");
            startOrStopService(false); //false stop, true start
        }else{
            Toast.makeText(this, "Not Charging", Toast.LENGTH_SHORT).show();
            text.setText("Not Charging");
            startOrStopService(true); //false stop, true start
        }

        //Battery BroadCast Receiver
        makeReceiver();
    }


    /** Makes the Broadcast Receiver
     *  1) Ελέγχω με το ACTION_POWER_CONNECTED και DISCONNECTED αν είναι ο φορτιστής AC Charger (Extended Controls)
     *  συνδεδεμένος και αν είναι αρχίζω ή σταματώ το service
     *  2)Ο receiver δεν θα δείξει με το που ανοίγει η εφαρμογή αν το κινητό φορτίζει ή όχι
     *  για αυτό θα πρέπει να χρησιμοποιήσουμε την μέθοδο isConnected() που υλοποιήσαμε, στην onCreate
     *  3) Register το BroadcastReceiver
     * */


    private void makeReceiver(){
        receiver =new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (Intent.ACTION_POWER_CONNECTED.equals(intent.getAction())){
                    startOrStopService(false); //false stop, true start
                    text.setText("Charging");
                }else if (Intent.ACTION_POWER_DISCONNECTED.equals(intent.getAction())){
                    startOrStopService(true); //false stop, true start
                    text.setText("Not Charging");
                }
            }
        };
        registerBatteryReceiver();
    }


    /**isConnected()
     *  1)Κάνουμε Register έναν Receiver με IntentFilter μόνο το ACTION_BATTERY_CHANGED
     *  2)Ελέγχουμε αν είναι συνδεδεμένη κάποια συσκευή/καλώδιο EXTRA_PLUGGED
     *  3)Και ελέγχουμε αν το κινητό φορτίζει με κάποιο από τους 3 πιθανούς τρόπους
     *  (Στον emulator μπορούμε να δούμε μόνο τον AC Charger)
     *  H τελευταία συνθήκη στο return είναι λογικά αχρείαστη αν ξέρουμε τις τιμές τους
     *  https://stackoverflow.com/questions/5283491/check-if-device-is-plugged-in
     */
    private boolean isConnected() {
        Intent intent = registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
        int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1); //plugged=0 για none, 1 για AC, 2 για USB και 4 για Wireless
        return plugged == BatteryManager.BATTERY_PLUGGED_AC || plugged == BatteryManager.BATTERY_PLUGGED_USB || plugged == BatteryManager.BATTERY_PLUGGED_WIRELESS;
    }


    private void stopBatteryReceiver(){
        try {
            unregisterReceiver(receiver);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
        }
    }

    private void registerBatteryReceiver(){
        intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED);
        registerReceiver(receiver,intentFilter);

    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Check onCreate if phone is charging
        /*
        if (isConnected()){
            text.setText("Charging");
            startOrStopService(false);
        }else{
            text.setText("Not Charging");
            startOrStopService(true);
        }
        makeReceiver();
         */
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "OnPause", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        startOrStopService(false); //false stop, true start
        stopBatteryReceiver();
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    private void startOrStopService(Boolean start){
        i=new Intent(this,LocationService.class);
        if (start){
            startService(i); //false stop, true start
        }else if (!start){
            stopService(i); //false stop, true start
        }
    }

}