package com.hatenablog.shoma2da.android.topocket.action;

import android.app.LoaderManager;
import android.content.Context;

public class SwitchActionStrategyFactory {
    
    public SwitchActionStrategy create(Context context, LoaderManager loaderManager, boolean isEnabled) {
        if (isEnabled) {
            return new On(context, loaderManager);
        }
        return new Off();
    }
    
}
