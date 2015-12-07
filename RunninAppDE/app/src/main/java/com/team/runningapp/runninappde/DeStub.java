package com.team.runningapp.runninappde;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.team.runningapp.deproxy.Constants;
import com.team.runningapp.deproxy.DeProxy;

public class DeStub extends ActionBarActivity {

    private Button btnSoundTest;
    private DeProxy deProxy;
    private IntentFilter deProxyFilter;
    private RadioGroup rgSoundGroup;
    private EditText etSoundLength;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destub);

        btnSoundTest = (Button)findViewById(R.id.btnSoundTest);
        btnSoundTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RadioButton rbSelectedSound = (RadioButton)findViewById(rgSoundGroup.getCheckedRadioButtonId());
                String selectedSoundName = rbSelectedSound.getText().toString().toLowerCase();

                int soundLength = Constants.DE_SOUND_LENGTH_DEFAULT;
                String soundLengthText = etSoundLength.getText().toString();
                if(!(soundLengthText == null || soundLengthText.isEmpty()))
                    soundLength = Integer.parseInt(soundLengthText);

                Intent intent = new Intent(getString(R.string.deproxy_intentfilter_name));
                intent.putExtra(Constants.DE_SOUND_NAME_KEY, selectedSoundName);
                intent.putExtra(Constants.DE_SOUND_LENGTH_KEY, soundLength);

                sendBroadcast(intent);
            }
        });

        rgSoundGroup = (RadioGroup)findViewById(R.id.rgSoundGroup);
        etSoundLength = (EditText)findViewById(R.id.etSoundLength);

        deProxy = new DeProxy(getApplicationContext());
        deProxyFilter = new IntentFilter(getString(R.string.deproxy_intentfilter_name));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_destub, menu);
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

    /*Marking this as TODO.
    For the scope of this app, we're starting and stopping the BroadcastReceiver at app resume/pause.
    This is not a good idea since the main app will be sending broadcasts when running as a service and thus the
    BroadcastReceiver needs to function for the entire duration of the app i.e. until it is killed by the ART.*/
    @Override
    protected void onResume() {
        super.onResume();

        deProxy.startProxy(deProxyFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();

        deProxy.stopProxy();
    }
}
