package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.WaterAlarmUtils;
import com.zhuoyou.plugin.running.view.WavesAnimView;

public class WaterReminderActivity extends BaseActivity {
    private static final int MAX_WATER_NUM = 99;
    private static final int MIN_WATER_NUM = 0;
    private WavesAnimView animView;
    private CheckBox cbWaterSwitch;
    private TextView tvIsOpen;
    private TextView tvWaterNum;
    private int waterNum;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_water_reminder);
        this.waterNum = SPUtils.getWaterNumber();
        initView();
    }

    private void initView() {
        this.animView = (WavesAnimView) findViewById(C1680R.id.waves_anim_view);
        this.tvWaterNum = (TextView) findViewById(C1680R.id.water_intake_number);
        this.cbWaterSwitch = (CheckBox) findViewById(C1680R.id.switch_water_alarm);
        this.tvIsOpen = (TextView) findViewById(C1680R.id.tv_is_open);
        this.cbWaterSwitch.setChecked(SPUtils.isWaterAlarmOpen());
        this.tvIsOpen.setText(SPUtils.isWaterAlarmOpen() ? C1680R.string.drink_alarm_opened : C1680R.string.drink_alarm_closed);
        this.tvWaterNum.setText(String.valueOf(this.waterNum));
        this.animView.setInitWaterLevel(this.waterNum);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.minus_water_intake:
                if (this.waterNum > 0) {
                    this.waterNum--;
                    SPUtils.setWaterNumber(this.waterNum);
                    updateWaterNumUI();
                    return;
                }
                return;
            case C1680R.id.add_water_intake:
                if (this.waterNum < 99) {
                    this.waterNum++;
                    SPUtils.setWaterNumber(this.waterNum);
                    updateWaterNumUI();
                    return;
                }
                return;
            case C1680R.id.btn_water_intake:
                startActivity(new Intent(this, WaterIntakeInfoActivity.class));
                return;
            case C1680R.id.switch_water_alarm:
                SPUtils.setWaterAlarmOpen(this.cbWaterSwitch.isChecked());
                WaterAlarmUtils.initWaterAlarm(SPUtils.isWaterAlarmOpen());
                this.tvIsOpen.setText(SPUtils.isWaterAlarmOpen() ? C1680R.string.drink_alarm_opened : C1680R.string.drink_alarm_closed);
                return;
            default:
                return;
        }
    }

    private void updateWaterNumUI() {
        this.tvWaterNum.setText(String.valueOf(this.waterNum));
        this.animView.setWaterLevel(this.waterNum, true);
    }
}
