package com.hatenablog.shoma2da.android.topocket;

import com.hatenablog.shoma2da.android.topocket.action.SwitchActionStrategyFactory;

import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class SwitchListener implements OnCheckedChangeListener {
    
    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        createFactory().create(isChecked).act();
    }
    
    public SwitchActionStrategyFactory createFactory() {
        return new SwitchActionStrategyFactory();
    }
    
}
