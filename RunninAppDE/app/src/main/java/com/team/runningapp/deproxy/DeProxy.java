package com.team.runningapp.deproxy;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.team.runningapp.player.DistortParent;

/**
 * Created by sunny on 12/5/15.
 */
public class DeProxy {

    private BroadcastReceiver deListener;
    private Context appContext = null;
    private DistortParent distort;

    public DeProxy(Context _appContext){
        deListener = null;
        distort = null;
        appContext = _appContext;
    }

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

    public void stopProxy() {
        if(deListener != null) {
            appContext.unregisterReceiver(deListener);
            deListener = null;
        }
    }
}
