package com.example.locationmanagertesting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements LocationListener {

    TextView text;
    Button button;
    LocationManager locationManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get the ids
        text=findViewById(R.id.location);
        button=findViewById(R.id.button);

        //RunTime permissions
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.ACCESS_FINE_LOCATION},7);
            return;
        }

        //Make the button onClick listener
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get current Location
                getLocation();

            }
        });
    }

    /*
    Η onRequestPermissionsResult δεν χρειάζεται επειδή ελέγχω μέσα εδώ
     */
    @SuppressLint("MissingPermission")
    private void getLocation() {
        //RunTime permissions

        try{
            locationManager=(LocationManager) getApplication().getSystemService(LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,5000,10,MainActivity.this);
        }catch(Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        //TODO save the lon,lat in the db
        Toast.makeText(this, location.getLatitude()+","+location.getLongitude(), Toast.LENGTH_SHORT).show();
        text.setText("Changed");
    }

    @Override
    protected void onPause() {
        super.onPause();
        locationManager.removeUpdates(MainActivity.this);

    }

    @Override
    protected void onStop() {
        super.onStop();
        locationManager.removeUpdates(MainActivity.this);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
