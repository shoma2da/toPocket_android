package com.hatenablog.shoma2da.android.topocket;

import com.hatenablog.shoma2da.android.topocket.action.SwitchActionStrategyFactory;

import android.app.LoaderManager;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SwitchListener implements OnCheckedChangeListener {
    
    private LoaderManager mLoaderManager;
    
    public SwitchListener(LoaderManager loaderManager) {
        mLoaderManager = loaderManager;
    }
    
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        createFactory().create(buttonView.getContext(), mLoaderManager, isChecked).act();
    }
    
    public SwitchActionStrategyFactory createFactory() {
        return new SwitchActionStrategyFactory();
    }
    
}
