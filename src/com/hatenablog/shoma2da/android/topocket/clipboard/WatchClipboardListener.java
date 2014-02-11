package com.hatenablog.shoma2da.android.topocket.clipboard;

import java.net.MalformedURLException;
import java.net.URL;

import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.hatenablog.shoma2da.android.topocket.api.AddRequestManager;

public class WatchClipboardListener implements OnPrimaryClipChangedListener {
    
    private Context mContext;
    private ClipboardManager mClipboardManager;
    private AddRequestManager mAddRequestManager;
    
    public WatchClipboardListener(Context context, ClipboardManager clipboardManager, AddRequestManager addRequestManager) {
        mContext = context;
        mClipboardManager = clipboardManager;
        mAddRequestManager = addRequestManager;
    }
    
    @Override
    public void onPrimaryClipChanged() {
        if (mClipboardManager.getPrimaryClip() == null || mClipboardManager.getPrimaryClip().getItemAt(0) == null) {
            return;
        }
        
        String text = mClipboardManager.getPrimaryClip().getItemAt(0).getText().toString();
        if (isUrl(text)) {
            if (isConnectNetwork() == false) {
                Toast.makeText(mContext, "Pocketに保存できません。ネットワーク状態を確認してください。", Toast.LENGTH_LONG).show();
            }
            
            mAddRequestManager.request(text);
            Toast.makeText(mContext, "Pocketに保存しました。", Toast.LENGTH_SHORT).show();
        }
    }
    
    public boolean isConnectNetwork() {
        ConnectivityManager connectivityManager = (ConnectivityManager)mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo == null || networkInfo.isConnected() == false) {
            return false;
        }
        return true;
    }
    
    public boolean isUrl(String text) {
        try {
            new URL(text);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }
    
}
