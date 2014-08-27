package com.hatenablog.shoma2da.android.topocket.oauth.model.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hatenablog.shoma2da.android.topocket.oauth.model.AccessToken;

public class AccessTokenLoader {
    
    private SharedPreferences mPreferences;
    
    public AccessTokenLoader(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    public AccessToken load() {
        String userName = mPreferences.getString(AccessTokenSaver.KEY_USER, null);
        String token = mPreferences.getString(AccessTokenSaver.KEY_TOKEN, null);
        if (userName == null || token == null) {
            return null;
        }
        return new AccessToken(token, userName);
    }
}
