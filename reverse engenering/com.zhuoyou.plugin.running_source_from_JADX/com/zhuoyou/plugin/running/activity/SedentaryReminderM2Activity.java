package com.zhuoyou.plugin.running.activity;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.droi.btlib.service.BtDevice;
import com.droi.btlib.service.BtManagerService;
import com.droi.library.pickerviews.picker.TimePickerDialog;
import com.droi.library.pickerviews.picker.TimePickerView;
import com.droi.library.pickerviews.picker.TimePickerView.OnTimePickedListener;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import java.text.DecimalFormat;
import org.andengine.util.time.TimeConstants;

public class SedentaryReminderM2Activity extends BaseActivity implements OnClickListener {
    private CheckBox cbLunchNotBreakOpen;
    private CheckBox cbSedentaryRemindOpen;
    private int endTimeHour = 21;
    private int endTimeMin;
    private boolean isBreak;
    private boolean isRemind;
    private LinearLayout mActionBarBackRl;
    private TextView mActionBarLeftTitleTv;
    private LinearLayout mEndTimeRl;
    private TextView mEndTimeTv;
    private LinearLayout mStartTimeRl;
    private TextView mStartTimeTv;
    private String sedentaryRemindInfo = "";
    private String sedentaryRemindTime = "";
    private int startTimeHour = 8;
    private int startTimeMin;

    class C18011 implements OnDismissListener {
        C18011() {
        }

        public void onDismiss(DialogInterface dialog) {
            int i = 1;
            SedentaryReminderM2Activity sedentaryReminderM2Activity = SedentaryReminderM2Activity.this;
            StringBuilder append = new StringBuilder().append("0|").append(SPUtils.getSedentaryRemindSwitch() ? 1 : 0).append("|").append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeHour)).append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeMin)).append("|").append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour)).append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin)).append("|");
            if (!SPUtils.getLunchNotBreakSwitch()) {
                i = 0;
            }
            sedentaryReminderM2Activity.sedentaryRemindInfo = append.append(i).toString();
            SedentaryReminderM2Activity.this.sedentaryRemindTime = SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeHour) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeMin) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin);
            SPUtils.setSedentaryRemindTime(SedentaryReminderM2Activity.this.sedentaryRemindTime);
            SedentaryReminderM2Activity.this.mStartTimeTv.setText(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeHour) + ":" + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeMin));
        }
    }

    class C18022 implements OnTimePickedListener {
        C18022() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            SedentaryReminderM2Activity.this.startTimeHour = hourIndex;
            SedentaryReminderM2Activity.this.startTimeMin = minuteIndex;
        }
    }

    class C18033 implements OnDismissListener {
        C18033() {
        }

        public void onDismiss(DialogInterface dialog) {
            int i;
            int i2 = 1;
            if (SedentaryReminderM2Activity.this.endTimeHour > SedentaryReminderM2Activity.this.startTimeHour) {
                SedentaryReminderM2Activity.this.mEndTimeTv.setText(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour) + ":" + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin));
            } else if (SedentaryReminderM2Activity.this.endTimeMin > SedentaryReminderM2Activity.this.startTimeMin) {
                SedentaryReminderM2Activity.this.mEndTimeTv.setText(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour) + ":" + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin));
            } else {
                SedentaryReminderM2Activity.this.initSedentaryRemindTime();
                SedentaryReminderM2Activity.this.mEndTimeTv.setText(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour) + ":" + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin));
                Tools.makeToast(SedentaryReminderM2Activity.this.getString(C1680R.string.end_time_not_start_time));
            }
            SedentaryReminderM2Activity sedentaryReminderM2Activity = SedentaryReminderM2Activity.this;
            StringBuilder append = new StringBuilder().append("0|");
            if (SPUtils.getSedentaryRemindSwitch()) {
                i = 1;
            } else {
                i = 0;
            }
            StringBuilder append2 = append.append(i).append("|").append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeHour)).append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeMin)).append("|").append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour)).append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin)).append("|");
            if (!SPUtils.getLunchNotBreakSwitch()) {
                i2 = 0;
            }
            sedentaryReminderM2Activity.sedentaryRemindInfo = append2.append(i2).toString();
            SedentaryReminderM2Activity.this.sedentaryRemindTime = SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeHour) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeMin) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin);
            SPUtils.setSedentaryRemindTime(SedentaryReminderM2Activity.this.sedentaryRemindTime);
            Log.i("hph", "end sedentaryRemindTime=" + SedentaryReminderM2Activity.this.sedentaryRemindTime);
            Log.i("hph", "sedentaryRemindInfo=" + SedentaryReminderM2Activity.this.sedentaryRemindInfo);
        }
    }

    class C18044 implements OnTimePickedListener {
        C18044() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            SedentaryReminderM2Activity.this.endTimeHour = hourIndex;
            SedentaryReminderM2Activity.this.endTimeMin = minuteIndex;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_sedentary_remind_m2);
        initView();
        initData();
    }

    private void initView() {
        this.mStartTimeTv = (TextView) findViewById(C1680R.id.sedentary_remind_start_time_tv);
        this.mEndTimeTv = (TextView) findViewById(C1680R.id.sedentary_remind_end_time_tv);
        this.mStartTimeRl = (LinearLayout) findViewById(C1680R.id.sedentary_remind_start_time_rl);
        this.mEndTimeRl = (LinearLayout) findViewById(C1680R.id.sedentary_remind_end_time_rl);
        this.cbSedentaryRemindOpen = (CheckBox) findViewById(C1680R.id.cb_sedentary_remind);
        this.cbLunchNotBreakOpen = (CheckBox) findViewById(C1680R.id.cb_lunch_not_break);
        this.mStartTimeRl.setOnClickListener(this);
        this.mEndTimeRl.setOnClickListener(this);
    }

    private void initData() {
        this.isRemind = SPUtils.getSedentaryRemindSwitch();
        this.isBreak = SPUtils.getLunchNotBreakSwitch();
        initSedentaryRemindTime();
        this.cbSedentaryRemindOpen.setChecked(this.isRemind);
        this.cbLunchNotBreakOpen.setChecked(this.isBreak);
        this.mStartTimeTv.setText(decimalFormat(this.startTimeHour) + ":" + decimalFormat(this.startTimeMin));
        this.mEndTimeTv.setText(decimalFormat(this.endTimeHour) + ":" + decimalFormat(this.endTimeMin));
        Log.i("hph", "isRemind=" + this.isRemind);
        Log.i("hph", "isBreak=" + this.isBreak);
    }

    public void onClick(View v) {
        int i = 1;
        StringBuilder append;
        int i2;
        StringBuilder append2;
        switch (v.getId()) {
            case C1680R.id.cb_sedentary_remind:
                this.isRemind = this.cbSedentaryRemindOpen.isChecked();
                if (this.isRemind) {
                    SPUtils.setSedentaryRemindSwitch(true);
                } else {
                    SPUtils.setSedentaryRemindSwitch(false);
                }
                append = new StringBuilder().append("0|");
                if (SPUtils.getSedentaryRemindSwitch()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append2 = append.append(i2).append("|").append(decimalFormat(this.startTimeHour)).append(decimalFormat(this.startTimeMin)).append("|").append(decimalFormat(this.endTimeHour)).append(decimalFormat(this.endTimeMin)).append("|");
                if (!SPUtils.getLunchNotBreakSwitch()) {
                    i = 0;
                }
                this.sedentaryRemindInfo = append2.append(i).toString();
                return;
            case C1680R.id.sedentary_remind_start_time_rl:
                showTimePickerStartDialog();
                return;
            case C1680R.id.sedentary_remind_end_time_rl:
                showTimePickerEndDialog();
                return;
            case C1680R.id.cb_lunch_not_break:
                this.isBreak = this.cbLunchNotBreakOpen.isChecked();
                if (this.isBreak) {
                    SPUtils.setLunchNotBreakSwitch(true);
                } else {
                    SPUtils.setLunchNotBreakSwitch(false);
                }
                append = new StringBuilder().append("0|");
                if (SPUtils.getSedentaryRemindSwitch()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append2 = append.append(i2).append("|").append(decimalFormat(this.startTimeHour)).append(decimalFormat(this.startTimeMin)).append("|").append(decimalFormat(this.endTimeHour)).append(decimalFormat(this.endTimeMin)).append("|");
                if (!SPUtils.getLunchNotBreakSwitch()) {
                    i = 0;
                }
                this.sedentaryRemindInfo = append2.append(i).toString();
                return;
            default:
                return;
        }
    }

    private void showTimePickerStartDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this);
        TimePickerView view = (TimePickerView) dialog.getPickerView();
        view.setTimeRange(0, 0, 23, 59);
        view.setInitTimeIndex(this.startTimeHour, this.startTimeMin);
        view.showLabel(true);
        dialog.setTitle(getString(C1680R.string.start_time));
        dialog.getDialog().setOnDismissListener(new C18011());
        view.setOnTimePickedListener(new C18022());
        view.show();
        dialog.show();
    }

    private void showTimePickerEndDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this);
        TimePickerView view = (TimePickerView) dialog.getPickerView();
        view.setTimeRange(0, 0, 23, 59);
        view.setInitTimeIndex(this.endTimeHour, this.endTimeMin);
        view.showLabel(true);
        dialog.setTitle(getString(C1680R.string.end_time));
        dialog.getDialog().setOnDismissListener(new C18033());
        view.setOnTimePickedListener(new C18044());
        view.show();
        dialog.show();
    }

    private String decimalFormat(int time) {
        return new DecimalFormat("#00").format((long) time);
    }

    private void initSedentaryRemindTime() {
        this.sedentaryRemindTime = SPUtils.getSedentaryRemindTime();
        if (this.sedentaryRemindTime != null && !TextUtils.isEmpty(this.sedentaryRemindTime)) {
            int remindTime = Integer.valueOf(this.sedentaryRemindTime).intValue();
            this.endTimeMin = remindTime % 100;
            this.endTimeHour = (remindTime / 100) % 100;
            this.startTimeMin = (remindTime / 10000) % 100;
            this.startTimeHour = remindTime / TimeConstants.MICROSECONDS_PER_SECOND;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.i("hph", "onBackPressed sedentaryRemindInfo=" + this.sedentaryRemindInfo);
        BtDevice device = BtManagerService.getConnectDevice(null);
        if (device != null) {
            device.setSedentary(this.sedentaryRemindInfo);
        }
    }
}
