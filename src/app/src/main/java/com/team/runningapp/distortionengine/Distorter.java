package com.team.runningapp.distortionengine;

import android.media.MediaPlayer;
import android.content.Context;

public class Distorter {

    private MediaPlayer mediaPlayer;
    private Context applicationContext;

    public Distorter(Context _appContext) {
        applicationContext = _appContext;
        mediaPlayer = null;
    }

    public void playSound() {
       // mediaPlayer = MediaPlayer.create(applicationContext, /*sound file*/);
        //mediaPlayer.setLooping(true);
        //mediaPlayer.start();
    }

    public void stopSound() {
        mediaPlayer.stop();
    }

}
