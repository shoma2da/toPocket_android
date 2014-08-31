package com.hatenablog.shoma2da.android.topocket;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by shoma2da on 2014/08/31.
 */
public class ToPocketApplication extends Application {

    private Tracker mTracker;

    public Tracker getTracker() {
        if (mTracker == null) {
            GoogleAnalytics analytics= GoogleAnalytics.getInstance(this);
            mTracker = analytics.newTracker("UA-32548600-15");
        }
        return mTracker;
    }

}
