package com.zhuoyou.plugin.ble;

import java.util.Timer;
import java.util.TimerTask;

public class CustomTimer {
    private CustomTimerCallback mCb = null;
    private int mTimeout;
    private Timer mTimer;

    private class ProgressTask extends TimerTask {
        int f3488i;

        private ProgressTask() {
            this.f3488i = 0;
        }

        public void run() {
            this.f3488i++;
            if (this.f3488i >= CustomTimer.this.mTimeout) {
                CustomTimer.this.mTimer.cancel();
                CustomTimer.this.mTimer = null;
                if (CustomTimer.this.mCb != null) {
                    CustomTimer.this.mCb.onTimeout();
                }
            } else if (CustomTimer.this.mCb != null) {
                CustomTimer.this.mCb.onTick(this.f3488i);
            }
        }
    }

    public CustomTimer(int timeout, CustomTimerCallback cb) {
        this.mTimeout = timeout;
        this.mTimer = new Timer();
        this.mTimer.schedule(new ProgressTask(), 0, 1000);
        this.mCb = cb;
    }

    public void stop() {
        if (this.mTimer != null) {
            this.mTimer.cancel();
            this.mTimer = null;
        }
    }
}
