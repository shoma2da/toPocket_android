package com.hatenablog.shoma2da.android.topocket.action;

public class SwitchActionStrategyFactory {
    
    public SwitchActionStrategy create(boolean isEnabled) {
        if (isEnabled) {
            return new On();
        }
        return new Off();
    }
    
}
