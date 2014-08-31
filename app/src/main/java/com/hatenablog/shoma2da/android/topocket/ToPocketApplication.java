package com.hatenablog.shoma2da.android.topocket;

import android.app.Application;
import android.os.Build;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;
import com.hatenablog.shoma2da.android.topocket.lib.analytics.TrackerWrapper;

/**
 * Created by shoma2da on 2014/08/31.
 */
public class ToPocketApplication extends Application {

    private TrackerWrapper mTracker;

    public TrackerWrapper getTracker() {
        if (BuildConfig.DEBUG) {
            mTracker = new TrackerWrapper(null);
        }
        if (mTracker == null) {
            GoogleAnalytics analytics= GoogleAnalytics.getInstance(this);
            mTracker = new TrackerWrapper(analytics.newTracker("UA-32548600-15"));
        }
        return mTracker;
    }

}
