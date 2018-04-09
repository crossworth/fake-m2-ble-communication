package com.zhuoyou.plugin.ble;

public abstract class CustomTimerCallback {
    protected abstract void onTick(int i);

    protected abstract void onTimeout();
}
