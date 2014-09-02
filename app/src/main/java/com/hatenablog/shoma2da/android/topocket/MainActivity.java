package com.hatenablog.shoma2da.android.topocket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.crashlytics.android.Crashlytics;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.analytics.HitBuilders;
import com.hatenablog.shoma2da.android.topocket.lib.analytics.TrackerWrapper;
import com.hatenablog.shoma2da.android.topocket.oauth.AccessTokenLoaderCallbackImpl;
import com.hatenablog.shoma2da.android.topocket.oauth.LoginChecker;
import com.hatenablog.shoma2da.android.topocket.oauth.model.ConsumerKey;
import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.util.RequestTokenLoader;

public class MainActivity extends Activity {

    private boolean mIsInGetAccessToken = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (BuildConfig.DEBUG == false) { Crashlytics.start(this); }
        setContentView(R.layout.activity_main);

        //イベント取得
        TrackerWrapper tracker = ((ToPocketApplication) getApplication()).getTracker();
        tracker.setScreenName("MainActivity");
        tracker.send(new HitBuilders.AppViewBuilder().build());

        //チェックボタンの初期表示設定
        CheckBox startWatchClipboardSwitch = (CheckBox)findViewById(R.id.StartWatchClipboardSwitch);
        SharedPreferences preferences = getSharedPreferences(SwitchListener.FILE_CHECKED_STATE, Context.MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean(SwitchListener.KEY_VALUE, false);
        startWatchClipboardSwitch.setChecked(isChecked);

        //Pocketを開けるようにする
        findViewById(R.id.textOpenPocket).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(getPocketIntent());
            }
        });

        //チェック時の動作を規定
        startWatchClipboardSwitch.setOnCheckedChangeListener(new SwitchListener(getLoaderManager()));

        //URLスキーマで起動された時はアクセストークンを取得するタイミング
        RequestToken requestToken = new RequestTokenLoader(this).load();
        if (getIntent().getDataString() != null && requestToken != null) {
            mIsInGetAccessToken = true;
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
    protected void onResume() {
        super.onResume();

        //ログイン状態を確認してボタンの表示を変更
        SharedPreferences preferences = getSharedPreferences(SwitchListener.FILE_CHECKED_STATE, Context.MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean(SwitchListener.KEY_VALUE, false);
        if ((mIsInGetAccessToken == false) && isChecked && (new LoginChecker().check(this) == false)) {
            CheckBox startWatchClipboardSwitch = (CheckBox)findViewById(R.id.StartWatchClipboardSwitch);
            startWatchClipboardSwitch.setChecked(false);
        }
    }

    public Intent getPocketIntent() {
        PackageManager packageManager = getApplicationContext().getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage("com.ideashower.readitlater.pro");
        if (intent == null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://getpocket.com/a/queue/"));
        }
        return intent;
    }

}
