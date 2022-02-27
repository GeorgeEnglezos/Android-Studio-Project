package com.example.project2;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.icu.util.ULocale;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.security.Provider;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LocationService extends Service {
    LocationManager locationManager;
    LocationListener locationListener;

    @SuppressLint("MissingPermission")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        locationListener=new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                ContentValues values=new ContentValues();

                /**Date:
                 * https://stackabuse.com/how-to-get-current-date-and-time-in-java/
                 */
                Date date = new Date(); // This object contains the current date value
                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

                values.put("longitude",location.getLongitude());
                values.put("latitude",location.getLatitude());
                values.put("dt",formatter.format(date));
                Uri id = getContentResolver().insert(Uri.parse("content://locations_db/locations"),values);
                Toast.makeText(getApplicationContext(),String.valueOf(id),Toast.LENGTH_LONG).show();
            }
        };

        /**
         *  Η onRequestPermissionsResult δεν χρειάζεται, κάνει @SuppressLint("MissingPermission")
         *  και ελέγχω στην onCreate αν πήραμε το Permission
         */

        try{
            locationManager=(LocationManager) getApplication().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,10,locationListener);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return super.onStartCommand(intent, flags, startId);

    }

    @Override
    public void onDestroy() {
        stopLocationListener();
        stopSelf();
        Toast.makeText(getApplicationContext(),"Destroyed",Toast.LENGTH_LONG).show();
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    /**Stops the locationListener*/
    private void stopLocationListener(){
        try {
            locationManager.removeUpdates(locationListener);
        }catch (RuntimeException e){
            e.printStackTrace();
        }
    }
    /**  onLocationChanged()
     * 1) Καλείται όποτε αλλάζει η τοποθεσία
     * 2) παίρνει τα στοιχεία που θέλουμε
     * 3) χρησιμοποιεί ContentResolver() για να περάσει τα δεδομένα στον Content Provider χρησιμοποιώντας την μέθοδο insert
     * 4) Ο ContentProvider είναι στο ίδιο project (LocationDbProvider) το οποίο από όσο ξέρω και λέει και η λογική
     * είναι κακή πρακτική και "άσκοπο" αλλά για λόγους απλότητας της εργασίας το αφήνω έτσι.
     */


}