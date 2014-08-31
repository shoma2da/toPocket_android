package com.hatenablog.shoma2da.android.topocket.lib.analytics;

import com.google.android.gms.analytics.Tracker;

import java.util.Map;

/**
 * Created by shoma2da on 2014/08/31.
 */
public class TrackerWrapper {

    private Tracker mTracker;

    public TrackerWrapper(Tracker tracker) {
        mTracker = tracker;
    }

    public void setScreenName(String screenName) {
        if (mTracker != null) {
            mTracker.setScreenName(screenName);
        }
    }

    public void send(Map<String, String> params) {
        if (mTracker != null) {
            mTracker.send(params);
        }
    }

}
