package com.hatenablog.shoma2da.android.topocket;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;

public class WatchClipboardService extends Service {
    
    public static int NOTIFICATION_ID = 315;
    
    @Override
    @SuppressWarnings("deprecation")
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder builder = new Notification.Builder(this);
        builder.setTicker("Pocketに簡単に投稿できます");
        builder.setContentTitle("toPocket");
        builder.setContentText("投稿したいURLをコピーしてください");
        builder.setOngoing(true);
        builder.setSmallIcon(R.drawable.ic_launcher);
        
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, builder.getNotification());
        
        return super.onStartCommand(intent, flags, startId);
    }
    
    @Override
    public void onDestroy() {
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.cancel(NOTIFICATION_ID);
        
        super.onDestroy();
    }
    
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
