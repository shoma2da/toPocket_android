package com.hatenablog.shoma2da.android.topocket.action;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.hatenablog.shoma2da.android.topocket.WatchClipboardService;
import com.hatenablog.shoma2da.android.topocket.oauth.AuthPageViewer;
import com.hatenablog.shoma2da.android.topocket.oauth.RequestTokenLoader;
import com.hatenablog.shoma2da.android.topocket.oauth.model.RequestToken;
import com.hatenablog.shoma2da.android.topocket.oauth.model.util.RequestTokenSaver;

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
        FlurryAgent.logEvent("switch_on");
        
        //ネットワーク状態の確認
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || networkInfo.isConnected() == false) {
            Toast.makeText(mContext, "エラー！！ネットワークに接続してからもう一度スイッチを押してください", Toast.LENGTH_LONG).show();
            return;
        }
        
        //リクエストトークンを未取得だったら取得しにいく
        if (new com.hatenablog.shoma2da.android.topocket.oauth.model.util.RequestTokenLoader(mContext).load() == null) {
            mLoaderManager.getLoader(0).forceLoad();
        }
        
        //サービスを起動
        mContext.startService(new Intent(mContext, WatchClipboardService.class));
    }

    @Override
    public Loader<RequestToken> onCreateLoader(int id, Bundle args) {
        RequestTokenLoader loader = new RequestTokenLoader(mContext);
        return loader;
    }

    @Override
    public void onLoadFinished(Loader<RequestToken> loader, RequestToken token) {
        //リクエストトークンの保存
        RequestTokenSaver saver = new RequestTokenSaver(PreferenceManager.getDefaultSharedPreferences(mContext), token);
        saver.save();
        
        //認証画面への遷移
        AuthPageViewer authPageViewer = new AuthPageViewer(mContext, token);
        authPageViewer.go();
    }

    @Override public void onLoaderReset(Loader<RequestToken> arg0) {}

}
