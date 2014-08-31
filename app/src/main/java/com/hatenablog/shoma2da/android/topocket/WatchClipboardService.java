package com.hatenablog.shoma2da.android.topocket;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

import com.crashlytics.android.Crashlytics;
import com.flurry.android.FlurryAgent;
import com.hatenablog.shoma2da.android.topocket.api.AddRequestManager;
import com.hatenablog.shoma2da.android.topocket.clipboard.WatchClipboardListener;
import com.hatenablog.shoma2da.android.topocket.oauth.model.AccessToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.ConsumerKey;
import com.hatenablog.shoma2da.android.topocket.oauth.model.util.AccessTokenLoader;

public class WatchClipboardService extends Service {
    
    public static int NOTIFICATION_ID = 315;
    
    private WatchClipboardListener mWatchClipboardListener;
    
    @Override
    @SuppressWarnings("deprecation")
    public int onStartCommand(Intent intent, int flags, int startId) {
        FlurryAgent.logEvent("start_service");
        if (BuildConfig.DEBUG == false) { Crashlytics.start(this); }

        //Notificatio表示
        Notification.Builder builder = new Notification.Builder(this);
        builder.setTicker("Pocketに簡単に投稿できます");
        builder.setContentTitle("toPocket");
        builder.setContentText("投稿したいURLをコピーしてください");
        builder.setOngoing(true);
        builder.setSmallIcon(R.drawable.ic_launcher);
        builder.setContentIntent(PendingIntent.getActivity(this, 0, new Intent(this, MainActivity.class), 0));
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.getNotification());
        
        //Clipboard監視を開始
        ConsumerKey consumerKey = new ConsumerKey();
        AccessToken accessToken = new AccessTokenLoader(this).load();
        AddRequestManager addRequestManager = new AddRequestManager(consumerKey, accessToken);
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        mWatchClipboardListener = new WatchClipboardListener(this, clipboardManager, addRequestManager);
        clipboardManager.addPrimaryClipChangedListener(mWatchClipboardListener);
        
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public void onDestroy() {
        FlurryAgent.logEvent("end_service");

        //Notificatioを消す
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
        
        //Clipboard監視を停止
        ClipboardManager clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.removePrimaryClipChangedListener(mWatchClipboardListener);
        
        super.onDestroy();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
