package com.hatenablog.shoma2da.android.topocket;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.CheckBox;

import com.crashlytics.android.Crashlytics;
import com.flurry.android.FlurryAgent;
import com.google.android.gms.ads.*;
import com.hatenablog.shoma2da.android.topocket.oauth.AccessTokenLoaderCallbackImpl;
import com.hatenablog.shoma2da.android.topocket.oauth.model.ConsumerKey;
import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.util.RequestTokenLoader;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Crashlytics.start(this);
        setContentView(R.layout.activity_main);
        
        //チェックボタンの初期表示設定
        CheckBox startWatchClipboardSwitch = (CheckBox)findViewById(R.id.StartWatchClipboardSwitch);
        SharedPreferences preferences = getSharedPreferences(SwitchListener.FILE_CHECKED_STATE, Context.MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean(SwitchListener.KEY_VALUE, false);
        startWatchClipboardSwitch.setChecked(isChecked);
        
        //チェック時の動作を規定
        startWatchClipboardSwitch.setOnCheckedChangeListener(new SwitchListener(getLoaderManager()));
        
        //URLスキーマで起動された時はアクセストークンを取得するタイミングかも
        RequestToken requestToken = new RequestTokenLoader(this).load();
        if (getIntent().getDataString() != null && requestToken != null) {
            AccessTokenLoaderCallbackImpl callback = new AccessTokenLoaderCallbackImpl(this, new ConsumerKey(), requestToken);
            getLoaderManager().initLoader(1, null, callback);
            getLoaderManager().getLoader(1).forceLoad();
        }
        
        //広告引き当て
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        FlurryAgent.onStartSession(this, "QKBP8MGBQHB99WDSFN5X");
    }
    
    @Override
    protected void onStop() {
        super.onStop();
        FlurryAgent.onEndSession(this);
    }
    
}
