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

import java.util.ArrayList;
import java.util.Calendar;

public class GPSSpeedo implements LocationListener
{
    ArrayList<Double> speedReports = new ArrayList<Double>(); //Stores information every time pace changes
    
    @Override
    public void onLocationChanged(Location location) {
        //if no location change data is sent
        if(location == null) {
            txt.setText("-.- m/s");
        }
        else { //else location data was given
            //Get the user's speed from location object
            double nCurrentSpeed = location.getSpeed();

            //Add the user's current speed to the list
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
