package com.zhuoyou.plugin.bluetooth.attach;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.running.Tools;

public class WristBrightScreenActivity extends Activity {
    private boolean isOpen;
    private RelativeLayout mActionBarBackRl;
    private TextView mActionBarLeftTitleTv;
    private ImageView mWristBrightScreenImg;
    private String wristBrightScreenInfo = "";

    class C11911 implements OnClickListener {
        C11911() {
        }

        public void onClick(View v) {
            WristBrightScreenActivity.this.broadWristBrightInfo(WristBrightScreenActivity.this.wristBrightScreenInfo);
            WristBrightScreenActivity.this.finish();
        }
    }

    class C11922 implements OnClickListener {
        C11922() {
        }

        public void onClick(View v) {
            int i = 1;
            if (WristBrightScreenActivity.this.isOpen) {
                WristBrightScreenActivity.this.mWristBrightScreenImg.setImageResource(R.drawable.warn_on);
                Tools.setM2WristBrightScreenSwitch(true);
                WristBrightScreenActivity.this.isOpen = false;
            } else {
                WristBrightScreenActivity.this.mWristBrightScreenImg.setImageResource(R.drawable.warn_off);
                Tools.setM2WristBrightScreenSwitch(false);
                WristBrightScreenActivity.this.isOpen = true;
            }
            WristBrightScreenActivity wristBrightScreenActivity = WristBrightScreenActivity.this;
            StringBuilder append = new StringBuilder().append("5|");
            if (!Tools.getM2WristBrightScreenSwitch()) {
                i = 0;
            }
            wristBrightScreenActivity.wristBrightScreenInfo = append.append(i).append("||||||||||||").toString();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wrist_bright_screen);
        initView();
        initData();
    }

    private void initView() {
        this.mActionBarLeftTitleTv = (TextView) findViewById(R.id.title);
        this.mActionBarBackRl = (RelativeLayout) findViewById(R.id.back);
        this.mWristBrightScreenImg = (ImageView) findViewById(R.id.wrist_bright_screen_img);
    }

    private void initData() {
        this.mActionBarLeftTitleTv.setText(R.string.wrist_bright_screen);
        this.mActionBarBackRl.setOnClickListener(new C11911());
        this.isOpen = Tools.getM2WristBrightScreenSwitch();
        if (this.isOpen) {
            this.mWristBrightScreenImg.setImageResource(R.drawable.warn_on);
        } else {
            this.mWristBrightScreenImg.setImageResource(R.drawable.warn_off);
        }
        this.mWristBrightScreenImg.setOnClickListener(new C11922());
        this.wristBrightScreenInfo = "5|" + (Tools.getM2WristBrightScreenSwitch() ? 1 : 0) + "||||||||||||";
    }

    public void onBackPressed() {
        super.onBackPressed();
        broadWristBrightInfo(this.wristBrightScreenInfo);
        Log.i("hph", "onBackPressed wristBrightScreenInfo=" + this.wristBrightScreenInfo);
    }

    private void broadWristBrightInfo(String info) {
        Intent intent = new Intent(BleManagerService.ACTION_UPDATE_SEDENTARY_INFO);
        intent.putExtra("sedentary_info", info);
        sendBroadcast(intent);
    }
}
