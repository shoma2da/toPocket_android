package com.hatenablog.shoma2da.android.topocket.oauth.model;

public class AccessToken {
    
    private String mTokenStr;
    private String mUserName;
    
    public AccessToken(String tokenStr, String userName) {
        mTokenStr = tokenStr;
        mUserName = userName;
    }
    
    public String getUserName() {
        return mUserName;
    }
    
    public String getmValidToken() {
        return mTokenStr;
    }
    
    public String toString() {
        return "[access token : " + mTokenStr + ", username : " + mUserName + "]";
    }
    
}
