package com.team.runningapp.player;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Handler;

import com.team.runningapp.runninappde.R;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by ssunny on 12/5/15.
 */
public class DistortParent {
    private MediaPlayer mediaPlayer;
    private Runnable mediaHalter = null;

    //This object is used to post a timer to the application's event queue, the timer's purpose being stopping the playing sounds
    private Handler handler = null;

    //Stores a mapping between the name of the raw sound resource (with the extension) and it's ID.
    HashMap<String, Integer> soundMap = null;

    public DistortParent() {
        mediaPlayer = null;

        //We're using reflection to get a list of all raw resources and fill the map with their name and ID
        soundMap = new HashMap<String, Integer>();
        Field[] fields = R.raw.class.getFields();

        //Once all components of the main app are integrated, the exceptions need to be handled in a suitable fashion
        for(Field field : fields) {
            try {
                soundMap.put(field.getName(), field.getInt(null));
            } catch(IllegalArgumentException iare) {
                //TODO: Need to handle this exception
            } catch(IllegalAccessException iace) {
                //TODO: Need to handle this exception
            }
        }

        //Purpose explained while declaring handler
        handler = new Handler();
        mediaHalter = new Runnable() {
            @Override
            public void run() {
                stopSound();
            }
        };
    }

    /**
     * This method gets the ID of a named raw sound resource.
     * All sound files are assumed to be mp3 for now.
     * @param _soundType The name of the resource file without its extension.
     * @return The ID of the resource or -1 if no resource with the given name is found.
     */
    private int getIdFromSoundType(String _soundType) {
        //TODO: might want to allow different extensions in the future
        String soundName = _soundType;

        if(!soundMap.containsKey(soundName))
            return -1;

        return soundMap.get(soundName);
    }

    /**
     * This method plays a distorting sound, selected from a fixed set of sounds shipped with the application for a given duration.
     * @param _appContext The application's Context, used to initialize the MediaPlayer object
     * @param _soundType A string indicating what sound is to be played
     * @param _playLength The length of time, in milliseconds, for which the sound is to be played
     */
    //Method is declared synchronized for obvious reasons
    public synchronized void playSound(Context _appContext, String _soundType, int _playLength) {
        /*I would imagine if the player is not null, there's something else going on but it's hard to define those conditions until
        the full app is developed. Keeping it simple for now, but this is a TODO*/
        if(mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }

        int soundId = getIdFromSoundType(_soundType);
        if(soundId == -1) {
            /*If no resource with the given name was found, we use a default resource (the first in the map) if the map is not empty
            Else, we need to inform the caller that playing will not happen so that the user can be informed of the same.*/
            if(soundMap.size() > 0) {
                //TODO: This should be optimized, there is no need of creating an iterator
                Set<String> soundNames = soundMap.keySet();
                for(String soundName : soundNames) {
                    soundId = soundMap.get(soundName);
                    break;
                }
            }
            else {
                //TODO: Throw an exception or return a status code (thus modifying the signature of this method) indicating playing failed
            }
        }

        mediaPlayer = MediaPlayer.create(_appContext, soundId);
        mediaPlayer.setLooping(true);

        mediaPlayer.start();
        //Post message to event queue to stop the sound after _playLength milliseconds
        handler.postDelayed(mediaHalter, _playLength);
    }

    /**
     * If a distorting sound is currently being played, this method will stop and release the MediaPlayer object
     */
    //Method is declared synchronized for obvious reasons
    public synchronized void stopSound() {
        if(handler != null) {
            //Remove the timer from message queue
            handler.removeCallbacks(mediaHalter);

            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.release();

                mediaPlayer = null;
            }
        }
    }
}
