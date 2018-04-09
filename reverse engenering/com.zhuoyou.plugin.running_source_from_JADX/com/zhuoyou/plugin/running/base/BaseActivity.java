package com.zhuoyou.plugin.running.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import com.droi.sdk.analytics.DroiAnalytics;
import com.zhuoyou.plugin.running.bean.EventLogout;
import com.zhuoyou.plugin.running.tools.StatusBarUtils;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class BaseActivity extends AppCompatActivity {
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        StatusBarUtils.transparencyBar(this, false);
    }

    @Subscribe
    public void onEventMainThread(EventLogout event) {
        finish();
        Log.i("zhuqichao", getClass().getSimpleName() + "退出……");
    }

    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    protected void onResume() {
        super.onResume();
        DroiAnalytics.onResume(this);
    }

    protected void onPause() {
        super.onPause();
        DroiAnalytics.onPause(this);
    }
}
