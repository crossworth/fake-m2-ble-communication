package com.zhuoyi.account.application;

import android.app.Application;
import android.content.res.Configuration;

public class LoginApplication extends Application {
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onCreate() {
        super.onCreate();
    }

    public void onLowMemory() {
        super.onLowMemory();
    }

    public void onTerminate() {
        super.onTerminate();
    }
}
