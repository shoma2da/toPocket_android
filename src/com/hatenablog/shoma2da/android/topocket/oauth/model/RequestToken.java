package com.hatenablog.shoma2da.android.topocket.oauth.model;

public class RequestToken {
    
    private String mTokenStr;
    
    public RequestToken(String tokenStr) {
        mTokenStr = tokenStr;
    }
    
    @Override
    public String toString() {
        return "[token:" + mTokenStr + "]";
    }
    
}
