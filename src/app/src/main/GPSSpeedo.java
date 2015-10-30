package com.team.runningapp;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;

public class GPSSpeedo extends AppCompatActivity implements LocationListener {
    //Global variables
    ArrayList<Double> speedReports = new ArrayList<Double>(); //Stores information every time pace changes
    Calendar timeStamper = Calendar.getInstance(); //Takes in time info for speedReports

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Auto generated code begins here
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        //Auto generated code ends here

        //Creating a LocationManager object and connecting it to GPS location services
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        //Setting the object to request location updates on any distance change and as often as
        //possible
        lm.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);

        this.onLocationChanged(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {
        //Declaring the MainActivity's TextView
        TextView txt = (TextView) findViewById(R.id.speedo_out);

        //If no location change data is sent
        if(location == null) {
            txt.setText("-.- m/s");
        }
        else { //Else location data was given
            //Get the user's speed from location object
            double nCurrentSpeed = location.getSpeed();
            //Set the TextView's text to the user's speed
            txt.setText(nCurrentSpeed + " m/s");

            speedReports.add(nCurrentSpeed);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
