package com.hatenablog.shoma2da.android.topocket;

import android.app.Activity;
import android.os.Bundle;
import android.widget.CheckBox;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        CheckBox startWatchClipboardSwitch = (CheckBox)findViewById(R.id.StartWatchClipboardSwitch);
        startWatchClipboardSwitch.setOnCheckedChangeListener(new SwitchListener(getLoaderManager()));
    }

}
