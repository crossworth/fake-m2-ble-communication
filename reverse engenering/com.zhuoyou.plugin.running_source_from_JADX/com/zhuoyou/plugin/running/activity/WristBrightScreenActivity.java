package com.zhuoyou.plugin.running.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtManagerService;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.tools.SPUtils;

public class WristBrightScreenActivity extends Activity {
    private CheckBox cbWristBrightScreen;
    private boolean isOpen;
    private RelativeLayout mActionBarBackRl;
    private TextView mActionBarLeftTitleTv;
    private String wristBrightScreenInfo = "";

    class C18531 implements OnClickListener {
        C18531() {
        }

        public void onClick(View v) {
            int i = 1;
            if (WristBrightScreenActivity.this.isOpen) {
                SPUtils.setM2WristBrightScreenSwitch(true);
                WristBrightScreenActivity.this.isOpen = false;
            } else {
                SPUtils.setM2WristBrightScreenSwitch(false);
                WristBrightScreenActivity.this.isOpen = true;
            }
            WristBrightScreenActivity wristBrightScreenActivity = WristBrightScreenActivity.this;
            StringBuilder append = new StringBuilder().append("5|");
            if (!SPUtils.getM2WristBrightScreenSwitch()) {
                i = 0;
            }
            wristBrightScreenActivity.wristBrightScreenInfo = append.append(i).append("||||||||||||").toString();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1680R.layout.activity_wrist_bright_screen);
        initView();
        initData();
    }

    private void initView() {
        this.cbWristBrightScreen = (CheckBox) findViewById(C1680R.id.cb_wrist_bright_screen);
    }

    private void initData() {
        this.isOpen = SPUtils.getM2WristBrightScreenSwitch();
        this.cbWristBrightScreen.setChecked(this.isOpen);
        this.cbWristBrightScreen.setOnClickListener(new C18531());
        this.wristBrightScreenInfo = "5|" + (SPUtils.getM2WristBrightScreenSwitch() ? 1 : 0) + "||||||||||||";
    }

    public void onBackPressed() {
        super.onBackPressed();
        BtDevice device = BtManagerService.getConnectDevice(null);
        if (device != null) {
            device.setSedentary(this.wristBrightScreenInfo);
        }
        Log.i("hph", "onBackPressed wristBrightScreenInfo=" + this.wristBrightScreenInfo);
    }
}
