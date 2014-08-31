package com.hatenablog.shoma2da.android.topocket.action;

import android.content.Context;
import android.content.Intent;

import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.hatenablog.shoma2da.android.topocket.ToPocketApplication;
import com.hatenablog.shoma2da.android.topocket.WatchClipboardService;
import com.hatenablog.shoma2da.android.topocket.lib.analytics.TrackerWrapper;

class Off implements SwitchActionStrategy {
    
    private Context mContext;
    
    Off(Context context) {
        mContext = context;
    }
    
    @Override
    public void act() {
        //イベント記録
        TrackerWrapper tracker = ((ToPocketApplication)mContext.getApplicationContext()).getTracker();
        tracker.send(new HitBuilders.EventBuilder()
                .setCategory("switch")
                .setAction("off")
                .build());

        mContext.stopService(new Intent(mContext, WatchClipboardService.class));
    }

}
