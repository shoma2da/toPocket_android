package com.hatenablog.shoma2da.android.topocket.oauth;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.widget.Toast;

import com.hatenablog.shoma2da.android.topocket.WatchClipboardService;
import com.hatenablog.shoma2da.android.topocket.oauth.model.AccessToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.ConsumerKey;
import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.util.AccessTokenSaver;

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
        //アクセストークンの保存
        AccessTokenSaver saver = new AccessTokenSaver(mContext, accessToken);
        saver.save();
        
        //表示
        Toast.makeText(mContext, accessToken.getUserName() + "さん、ようこそ！", Toast.LENGTH_LONG).show();

        //サービスを起動
        mContext.startService(new Intent(mContext, WatchClipboardService.class));
    }

    @Override public void onLoaderReset(Loader<AccessToken> loader) { }

}
