package com.hatenablog.shoma2da.android.topocket.oauth;

import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public class AuthPageViewer {
    
    public static final String URL_STR = "https://getpocket.com/auth/authorize?request_token=%s&redirect_uri=%s";
    public static final String REDIRECT_URL_STR = "http://yahoo.co.jp";
    
    private Context mContext;
    private RequestToken mRequestToken;
    
    public AuthPageViewer(Context context, RequestToken requestToken) {
        mContext = context;
        mRequestToken = requestToken;
    }
    
    public void go() {
        Uri uri = Uri.parse(String.format(URL_STR, mRequestToken.getValidToken(), REDIRECT_URL_STR));
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        mContext.startActivity(intent);
    }
    
}
