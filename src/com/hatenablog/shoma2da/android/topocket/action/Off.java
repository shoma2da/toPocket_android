package com.hatenablog.shoma2da.android.topocket.action;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.hatenablog.shoma2da.android.topocket.WatchClipboardService;

class Off implements SwitchActionStrategy {
    
    private Context mContext;
    
    Off(Context context) {
        mContext = context;
    }
    
    @Override
    public void act() {
        Log.d("test", "off");
        mContext.stopService(new Intent(mContext, WatchClipboardService.class));
    }

}
