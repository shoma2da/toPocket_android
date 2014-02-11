package com.hatenablog.shoma2da.android.topocket;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;

import com.hatenablog.shoma2da.android.topocket.oauth.AccessTokenLoader;
import com.hatenablog.shoma2da.android.topocket.oauth.model.AccessToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.ConsumerKey;
import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;

public class AccessTokenLoaderCallbackImpl implements LoaderCallbacks<AccessToken> {
    
    private Context mContext;
    private ConsumerKey mConsumerKey;
    private RequestToken mRequestToken;
    
    public AccessTokenLoaderCallbackImpl(Context context, ConsumerKey consumerKey, RequestToken requestToken) {
        mContext = context;
        mConsumerKey = consumerKey;
        mRequestToken = requestToken;
    }
    
    @Override
    public Loader<AccessToken> onCreateLoader(int id, Bundle args) {
        return new AccessTokenLoader(mContext, mConsumerKey, mRequestToken);
    }

    @Override
    public void onLoadFinished(Loader<AccessToken> loader, AccessToken accessToken) {
        Log.d("test", "onLoadFinished");
        Log.d("test", accessToken.toString());
    }

    @Override public void onLoaderReset(Loader<AccessToken> loader) { }

}
