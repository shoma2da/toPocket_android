package com.hatenablog.shoma2da.android.topocket;

import com.hatenablog.shoma2da.android.topocket.action.SwitchActionStrategyFactory;

import android.app.LoaderManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SwitchListener implements OnCheckedChangeListener {
    
    public static final String FILE_CHECKED_STATE = "checked_state";
    public static final String KEY_VALUE = "value";
    
    private LoaderManager mLoaderManager;
    
    public SwitchListener(LoaderManager loaderManager) {
        mLoaderManager = loaderManager;
    }
    
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        //チェック状態の保存
        Context context = buttonView.getContext();
        SharedPreferences preferences = context.getSharedPreferences(FILE_CHECKED_STATE, Context.MODE_PRIVATE);
        preferences.edit().putBoolean(KEY_VALUE, isChecked).commit();
        
        //チェックに応じた処理を開始
        createFactory().create(buttonView.getContext(), mLoaderManager, isChecked).act();
    }
    
    public SwitchActionStrategyFactory createFactory() {
        return new SwitchActionStrategyFactory();
    }
    
}
