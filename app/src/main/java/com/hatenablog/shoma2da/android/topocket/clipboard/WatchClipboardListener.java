package com.hatenablog.shoma2da.android.topocket.clipboard;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.google.android.gms.analytics.HitBuilders;
import com.hatenablog.shoma2da.android.topocket.ToPocketApplication;
import com.hatenablog.shoma2da.android.topocket.api.AddRequestManager;
import com.hatenablog.shoma2da.android.topocket.lib.analytics.TrackerWrapper;

import java.net.MalformedURLException;
import java.net.URL;

public class WatchClipboardListener implements OnPrimaryClipChangedListener {
    
    private Context mContext;
    private ClipboardManager mClipboardManager;
    private AddRequestManager mAddRequestManager;
    
    public WatchClipboardListener(Context context, ClipboardManager clipboardManager, AddRequestManager addRequestManager) {
        mContext = context;
        mClipboardManager = clipboardManager;
        mAddRequestManager = addRequestManager;
    }
    
    @Override
    public void onPrimaryClipChanged() {
        if (mClipboardManager.getPrimaryClip() == null || mClipboardManager.getPrimaryClip().getItemAt(0) == null) {
            return;
        }
        
        final String text = mClipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
        if (isUrl(text)) {
            if (isConnectNetwork() == false) {
                Toast.makeText(mContext, "Pocketに保存できません。ネットワーク状態を確認してください。", Toast.LENGTH_LONG).show();
                return;
            }
            
            mAddRequestManager.request(text, new AddRequestManager.Callback() {
                @Override
                public void onSuccess() {
                    //ホストを取得
                    Uri uri = Uri.parse(text);

                    //通知組み立て
                    Notification notification = new NotificationCompat.Builder(mContext)
                                                        .setSmallIcon(android.R.drawable.ic_dialog_info)
                                                        .setTicker("Pocketに保存しました " + uri.getHost())
                                                        .setContentTitle("Pocketに保存しました")
                                                        .setContentText(text)
                                                        .setAutoCancel(true)
                                                        .setContentIntent(PendingIntent.getActivity(mContext, 0, getPocketIntent(), 0))
                                                        .build();

                    //通知表示
                    NotificationManager notificationManager = (NotificationManager)mContext.getSystemService(Context.NOTIFICATION_SERVICE);
                    notificationManager.notify(1000, notification);
                }

                @Override
                public void onFailure() {
                    Toast.makeText(mContext, "Pocketへの保存に失敗しました。", Toast.LENGTH_SHORT).show();
                }
            });

            //イベント記録
            TrackerWrapper tracker = ((ToPocketApplication)mContext.getApplicationContext()).getTracker();
            tracker.send(new HitBuilders.EventBuilder()
                    .setCategory("clipboard")
                    .setAction("add_item")
                    .setLabel(text)
                    .build());
        }
    }

    public Intent getPocketIntent() {
        PackageManager packageManager = mContext.getApplicationContext().getPackageManager();
        Intent intent = packageManager.getLaunchIntentForPackage("com.ideashower.readitlater.pro");
        if (intent == null) {
            intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://getpocket.com/a/queue/"));
        }
        return intent;
    }
    
    public boolean isConnectNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || networkInfo.isConnected() == false) {
            return false;
        }
        return true;
    }
    
    public boolean isUrl(String text) {
        try {
            new URL(text);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
    
}
