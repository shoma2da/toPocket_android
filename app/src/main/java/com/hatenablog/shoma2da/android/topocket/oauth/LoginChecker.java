package com.hatenablog.shoma2da.android.topocket.oauth;

import android.content.Context;

import com.hatenablog.shoma2da.android.topocket.oauth.model.AccessToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.util.AccessTokenLoader;
import com.hatenablog.shoma2da.android.topocket.oauth.model.util.RequestTokenLoader;

/**
 * Created by shoma2da on 2014/08/30.
 */
public class LoginChecker {

    public boolean check(Context context) {
        RequestToken requestToken = new RequestTokenLoader(context).load();
        AccessToken accessToken = new AccessTokenLoader(context).load();
        if (requestToken == null || accessToken == null) {
            return false;
        }
        return true;
    }

}
