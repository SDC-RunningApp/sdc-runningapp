package com.team.runningapp.deproxy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.team.runningapp.player.DistortParent;

/**
 * Created by ssunny on 12/5/15.
 */
public class DeProxy {

    private BroadcastReceiver deListener;
    private Context appContext = null;
    private DistortParent distort;

    public DeProxy(Context _appContext){
        deListener = null;
        distort = new DistortParent();
        appContext = _appContext;
    }

    /**
     * This method starts the Distortion Engine (DE) proxy which is the entry point into the DE.
     * It registers a BroadcastReceiver which will be triggered whenever a distorting sounds beeds to be played with the
     * receiver's intent containing information about the sound to be played.
     *
     * This method must be called at app startup/resume failing which the DE will do nothing.
     * @param deProxyFilter The IntentFilter specifying the condition under which the BroadcastReceiver will be triggered
     */
    public void startProxy(IntentFilter deProxyFilter) {
        if(deListener != null)
            stopProxy();

        deListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };

        appContext.registerReceiver(deListener, deProxyFilter);
    }

    /**
     * This method stops the Distortion Engine (DE) by un-registering the BroadcastReceiver registered in startProxy.
     *
     * This method must be called at app shutdown/pause.
     */
    public void stopProxy() {
        if(deListener != null) {
            appContext.unregisterReceiver(deListener);
            deListener = null;
        }
    }
}
