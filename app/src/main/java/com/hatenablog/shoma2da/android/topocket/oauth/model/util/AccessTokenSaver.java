package com.hatenablog.shoma2da.android.topocket.oauth.model.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hatenablog.shoma2da.android.topocket.oauth.model.AccessToken;

public class AccessTokenSaver {
    
    public static final String KEY_TOKEN = "access_token";
    public static final String KEY_USER = "user";
    
    private SharedPreferences mPreferences;
    private AccessToken mAccessToken;
    
    public AccessTokenSaver(Context context, AccessToken accessToken) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        mAccessToken = accessToken;
    }
    
    public void save() {
        mPreferences.edit().putString(KEY_USER, mAccessToken.getUserName())
                           .putString(KEY_TOKEN, mAccessToken.getmValidToken()).apply();
    }
    
}
