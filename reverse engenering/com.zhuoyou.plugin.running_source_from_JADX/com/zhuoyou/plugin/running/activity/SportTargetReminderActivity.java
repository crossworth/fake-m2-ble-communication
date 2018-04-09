package com.zhuoyou.plugin.running.activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtManagerService;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.tools.SPUtils;
import java.text.DecimalFormat;

public class SportTargetReminderActivity extends Activity {
    private CheckBox cbSportTarget;
    private boolean isOpen;
    private RelativeLayout mActionBarBackRl;
    private TextView mActionBarLeftTitleTv;
    private String sportTargetRemindInfo = "";

    class C18191 implements OnCheckedChangeListener {
        C18191() {
        }

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            SportTargetReminderActivity.this.cbSportTarget.setChecked(isChecked);
            SportTargetReminderActivity.this.sportTargetRemindInfo = "2|" + (SPUtils.getSportTargetReminderSwitch() ? 1 : 0) + "|" + SportTargetReminderActivity.this.decimalFormat(SPUtils.getTargetStep()) + "||||||";
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C1680R.layout.activity_sport_target_reminder);
        initView();
        initData();
    }

    private void initView() {
        this.cbSportTarget = (CheckBox) findViewById(C1680R.id.cb_sport_target);
    }

    private void initData() {
        this.isOpen = SPUtils.getSportTargetReminderSwitch();
        this.cbSportTarget.setChecked(this.isOpen);
        this.cbSportTarget.setOnCheckedChangeListener(new C18191());
    }

    public void onBackPressed() {
        super.onBackPressed();
        BtDevice device = BtManagerService.getConnectDevice(null);
        if (device != null) {
            device.setSedentary(this.sportTargetRemindInfo);
        }
        Log.i("hph", "onBackPressed sportTargetRemindInfo=" + this.sportTargetRemindInfo);
    }

    private String decimalFormat(int step) {
        return new DecimalFormat("#00000").format((long) step);
    }
}
