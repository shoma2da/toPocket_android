package com.hatenablog.shoma2da.android.topocket.oauth.model.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;

public class RequestTokenLoader {
    
    private SharedPreferences mPreferences;
    
    public RequestTokenLoader(Context context) {
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }
    
    public RequestToken load() {
        String tokenStr = mPreferences.getString(RequestTokenSaver.KEY, null);
        if (tokenStr == null) {
           return null; 
        }
        return new RequestToken(tokenStr);
    }
    
}
