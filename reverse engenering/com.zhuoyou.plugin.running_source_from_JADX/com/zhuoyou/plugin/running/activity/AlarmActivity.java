package com.zhuoyou.plugin.running.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.AlarmData;
import com.zhuoyou.plugin.running.tools.AlarmUtils;
import com.zhuoyou.plugin.running.tools.SPUtils;
import java.text.DecimalFormat;

public class AlarmActivity extends BaseActivity {
    private static final String TAG = "AlarmActivity";
    private CheckBox cbAlarmOpen1;
    private CheckBox cbAlarmOpen2;
    private CheckBox cbAlarmOpen3;
    private AlarmData data1;
    private AlarmData data2;
    private AlarmData data3;
    private TextView tvAlarmDuration1;
    private TextView tvAlarmDuration2;
    private TextView tvAlarmDuration3;
    private TextView tvAlarmTime1;
    private TextView tvAlarmTime2;
    private TextView tvAlarmTime3;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_alarm);
        initView();
        initData();
    }

    private void initView() {
        this.tvAlarmTime1 = (TextView) findViewById(C1680R.id.tv_alarm_1);
        this.tvAlarmDuration1 = (TextView) findViewById(C1680R.id.tv_alarm_1_duration);
        this.cbAlarmOpen1 = (CheckBox) findViewById(C1680R.id.cb_alarm_1);
        this.tvAlarmTime2 = (TextView) findViewById(C1680R.id.tv_alarm_2);
        this.tvAlarmDuration2 = (TextView) findViewById(C1680R.id.tv_alarm_2_duration);
        this.cbAlarmOpen2 = (CheckBox) findViewById(C1680R.id.cb_alarm_2);
        this.tvAlarmTime3 = (TextView) findViewById(C1680R.id.tv_alarm_3);
        this.tvAlarmDuration3 = (TextView) findViewById(C1680R.id.tv_alarm_3_duration);
        this.cbAlarmOpen3 = (CheckBox) findViewById(C1680R.id.cb_alarm_3);
    }

    private void initData() {
        this.data1 = SPUtils.getAlarm(0);
        this.data2 = SPUtils.getAlarm(1);
        this.data3 = SPUtils.getAlarm(2);
        DecimalFormat intFormat = new DecimalFormat("#00");
        this.tvAlarmTime1.setText(intFormat.format((long) this.data1.getHour()) + ":" + intFormat.format((long) this.data1.getMin()));
        this.tvAlarmTime2.setText(intFormat.format((long) this.data2.getHour()) + ":" + intFormat.format((long) this.data2.getMin()));
        this.tvAlarmTime3.setText(intFormat.format((long) this.data3.getHour()) + ":" + intFormat.format((long) this.data3.getMin()));
        this.cbAlarmOpen1.setChecked(this.data1.isOpen());
        this.cbAlarmOpen2.setChecked(this.data2.isOpen());
        this.cbAlarmOpen3.setChecked(this.data3.isOpen());
        this.tvAlarmDuration1.setText(AlarmUtils.getDurationString(this.data1.getCustomData()));
        this.tvAlarmDuration2.setText(AlarmUtils.getDurationString(this.data2.getCustomData()));
        this.tvAlarmDuration3.setText(AlarmUtils.getDurationString(this.data3.getCustomData()));
    }

    public void onClick(View v) {
        Intent it = new Intent();
        switch (v.getId()) {
            case C1680R.id.rl_alarm_1:
                it.setClass(this, AlarmSettingActivity.class);
                it.putExtra("alarm_id", 0);
                startActivity(it);
                return;
            case C1680R.id.cb_alarm_1:
                this.data1.setOpen(this.cbAlarmOpen1.isChecked());
                SPUtils.saveAlarm(this.data1);
                return;
            case C1680R.id.rl_alarm_2:
                it.setClass(this, AlarmSettingActivity.class);
                it.putExtra("alarm_id", 1);
                startActivity(it);
                return;
            case C1680R.id.cb_alarm_2:
                this.data2.setOpen(this.cbAlarmOpen2.isChecked());
                SPUtils.saveAlarm(this.data2);
                return;
            case C1680R.id.rl_alarm_3:
                it.setClass(this, AlarmSettingActivity.class);
                it.putExtra("alarm_id", 2);
                startActivity(it);
                return;
            case C1680R.id.cb_alarm_3:
                this.data3.setOpen(this.cbAlarmOpen3.isChecked());
                SPUtils.saveAlarm(this.data3);
                return;
            default:
                return;
        }
    }

    protected void onResume() {
        super.onResume();
        initData();
    }
}
