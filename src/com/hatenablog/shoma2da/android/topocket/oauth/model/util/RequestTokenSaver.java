package com.hatenablog.shoma2da.android.topocket.oauth.model.util;

import android.content.SharedPreferences;

import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;

public class RequestTokenSaver {
    
    public static final String KEY = "request_token";
    
    private SharedPreferences mPreferences;
    private RequestToken mToken;
    
    public RequestTokenSaver(SharedPreferences preferences, RequestToken token) {
        mPreferences = preferences;
        mToken = token;
    }
    
    public void save() {
        mPreferences.edit().putString(KEY, mToken.getValidToken()).apply();
    }
    
}
