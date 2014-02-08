package com.hatenablog.shoma2da.android.topocket.action;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Loader;
import android.os.Bundle;

import com.hatenablog.shoma2da.android.topocket.oauth.AuthPageViewer;
import com.hatenablog.shoma2da.android.topocket.oauth.RequestTokenLoader;
import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;

class On implements SwitchActionStrategy, LoaderCallbacks<RequestToken> {
    
    private Context mContext;
    private LoaderManager mLoaderManager;
    
    public On(Context context, LoaderManager loaderManager) {
        mContext = context;
        mLoaderManager = loaderManager;
        mLoaderManager.initLoader(0, null, this);
    }
    
    @Override
    public void act() {
        mLoaderManager.getLoader(0).forceLoad();
    }

    @Override
    public Loader<RequestToken> onCreateLoader(int id, Bundle args) {
        RequestTokenLoader loader = new RequestTokenLoader(mContext);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<RequestToken> loader, RequestToken token) {
        AuthPageViewer authPageViewer = new AuthPageViewer(mContext, token);
        authPageViewer.go();
    }

    @Override public void onLoaderReset(Loader<RequestToken> arg0) {}

}
