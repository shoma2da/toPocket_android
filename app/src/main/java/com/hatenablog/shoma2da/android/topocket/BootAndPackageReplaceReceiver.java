package com.hatenablog.shoma2da.android.topocket;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.hatenablog.shoma2da.android.topocket.oauth.LoginChecker;

/**
 * Created by shoma2da on 2014/08/30.
 */
public class BootAndPackageReplaceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        SharedPreferences preferences = context.getSharedPreferences(SwitchListener.FILE_CHECKED_STATE, Context.MODE_PRIVATE);
        boolean isChecked = preferences.getBoolean(SwitchListener.KEY_VALUE, false);
        boolean isSuccessLogin = new LoginChecker().check(context);
        if (isChecked && isSuccessLogin) {
            context.startService(new Intent(context, WatchClipboardService.class));
        }
    }
}
