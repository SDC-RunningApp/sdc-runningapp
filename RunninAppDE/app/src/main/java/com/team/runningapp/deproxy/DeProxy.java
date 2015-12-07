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
     * It registers a BroadcastReceiver which will be triggered whenever a distorting sounds needs to be played with the
     * receiver's intent containing information about the sound to be played.
     *
     * This method must be called at app startup failing which the DE will do nothing.
     * @param deProxyFilter The IntentFilter specifying the condition under which the BroadcastReceiver will be triggered
     */
    public void startProxy(IntentFilter deProxyFilter) {
        if(deListener != null)
            stopProxy();

        deListener = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
               distort.playSound(context, intent.getStringExtra(Constants.DE_SOUND_NAME_KEY),
                                          intent.getIntExtra(Constants.DE_SOUND_LENGTH_KEY, Constants.DE_SOUND_LENGTH_DEFAULT)*1000);
            }
        };

        appContext.registerReceiver(deListener, deProxyFilter);
    }

    /**
     * This method stops the Distortion Engine (DE) by stopping any playing sounds and un-registering the BroadcastReceiver registered in startProxy.
     *
     * This method must be called at app shutdown.
     */
    public void stopProxy() {
        if(deListener != null) {
            distort.stopSound();

            appContext.unregisterReceiver(deListener);
            deListener = null;
        }
    }
}
