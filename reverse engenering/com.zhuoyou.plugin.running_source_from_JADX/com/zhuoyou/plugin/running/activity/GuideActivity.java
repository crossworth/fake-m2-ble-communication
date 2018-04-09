package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.view.View;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.EventLogout;
import com.zhuoyou.plugin.running.tools.StatusBarUtils;
import org.greenrobot.eventbus.EventBus;

public class GuideActivity extends BaseActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarUtils.StatusBarLightMode(this);
        setContentView((int) C1680R.layout.activity_guide);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.btn_start:
                finish();
                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        EventBus.getDefault().post(new EventLogout());
        super.onBackPressed();
    }
}
