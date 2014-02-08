package com.hatenablog.shoma2da.android.topocket;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //チェックボタンの初期表示設定
        CheckBox startWatchClipboardSwitch = (CheckBox)findViewById(R.id.StartWatchClipboardSwitch);
        SharedPreferences preferences = getSharedPreferences(SwitchListener.FILE_CHECKED_STATE, Context.MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean(SwitchListener.KEY_VALUE, false);
        startWatchClipboardSwitch.setChecked(isChecked);
        
        //チェック時の動作を規定
        startWatchClipboardSwitch.setOnCheckedChangeListener(new SwitchListener(getLoaderManager()));
        
        //URLスキーマで起動された時はアクセストークンを取得できるかも
        Log.d("test", "data is " + getIntent().getDataString());
    }

}
