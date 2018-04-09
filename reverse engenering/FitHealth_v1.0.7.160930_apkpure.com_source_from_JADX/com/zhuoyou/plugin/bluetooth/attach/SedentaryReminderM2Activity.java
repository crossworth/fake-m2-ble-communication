package com.zhuoyou.plugin.bluetooth.attach;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.droi.library.pickerviews.picker.TimePickerDialog;
import com.droi.library.pickerviews.picker.TimePickerView;
import com.droi.library.pickerviews.picker.TimePickerView.OnTimePickedListener;
import com.fithealth.running.R;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.running.Tools;
import java.text.DecimalFormat;

public class SedentaryReminderM2Activity extends Activity implements OnClickListener {
    private int endTimeHour = 21;
    private int endTimeMin;
    private boolean isBreak;
    private boolean isRemind;
    private RelativeLayout mActionBarBackRl;
    private TextView mActionBarLeftTitleTv;
    private RelativeLayout mEndTimeRl;
    private TextView mEndTimeTv;
    private ImageView mLunchNotBreakOpenImg;
    private ImageView mSedentaryRemindOpenImg;
    private RelativeLayout mStartTimeRl;
    private TextView mStartTimeTv;
    private String sedentaryRemindInfo = "";
    private String sedentaryRemindTime = "";
    private int startTimeHour = 8;
    private int startTimeMin;

    class C11791 implements OnClickListener {
        C11791() {
        }

        public void onClick(View v) {
            SedentaryReminderM2Activity.this.broadSedentaryRemindTimeInfo(SedentaryReminderM2Activity.this.sedentaryRemindInfo);
            SedentaryReminderM2Activity.this.finish();
        }
    }

    class C11802 implements OnDismissListener {
        C11802() {
        }

        public void onDismiss(DialogInterface dialog) {
            int i = 1;
            SedentaryReminderM2Activity sedentaryReminderM2Activity = SedentaryReminderM2Activity.this;
            StringBuilder append = new StringBuilder().append("0|").append(Tools.getSedentaryRemindSwitch() ? 1 : 0).append("|").append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeHour)).append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeMin)).append("|").append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour)).append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin)).append("|");
            if (!Tools.getLunchNotBreakSwitch()) {
                i = 0;
            }
            sedentaryReminderM2Activity.sedentaryRemindInfo = append.append(i).toString();
            SedentaryReminderM2Activity.this.sedentaryRemindTime = SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeHour) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeMin) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin);
            Tools.setSedentaryRemindTime(SedentaryReminderM2Activity.this.sedentaryRemindTime);
            SedentaryReminderM2Activity.this.mStartTimeTv.setText(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeHour) + ":" + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeMin));
        }
    }

    class C11814 implements OnDismissListener {
        C11814() {
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
                Tools.makeToast(SedentaryReminderM2Activity.this.getString(R.string.end_time_not_start_time));
            }
            SedentaryReminderM2Activity sedentaryReminderM2Activity = SedentaryReminderM2Activity.this;
            StringBuilder append = new StringBuilder().append("0|");
            if (Tools.getSedentaryRemindSwitch()) {
                i = 1;
            } else {
                i = 0;
            }
            StringBuilder append2 = append.append(i).append("|").append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeHour)).append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeMin)).append("|").append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour)).append(SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin)).append("|");
            if (!Tools.getLunchNotBreakSwitch()) {
                i2 = 0;
            }
            sedentaryReminderM2Activity.sedentaryRemindInfo = append2.append(i2).toString();
            SedentaryReminderM2Activity.this.sedentaryRemindTime = SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeHour) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.startTimeMin) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeHour) + SedentaryReminderM2Activity.this.decimalFormat(SedentaryReminderM2Activity.this.endTimeMin);
            Tools.setSedentaryRemindTime(SedentaryReminderM2Activity.this.sedentaryRemindTime);
            Log.i("hph", "end sedentaryRemindTime=" + SedentaryReminderM2Activity.this.sedentaryRemindTime);
            Log.i("hph", "sedentaryRemindInfo=" + SedentaryReminderM2Activity.this.sedentaryRemindInfo);
        }
    }

    class C18783 implements OnTimePickedListener {
        C18783() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            SedentaryReminderM2Activity.this.startTimeHour = hourIndex;
            SedentaryReminderM2Activity.this.startTimeMin = minuteIndex;
        }
    }

    class C18795 implements OnTimePickedListener {
        C18795() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            SedentaryReminderM2Activity.this.endTimeHour = hourIndex;
            SedentaryReminderM2Activity.this.endTimeMin = minuteIndex;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sedentary_remind_m2);
        initView();
        initData();
    }

    private void initView() {
        this.mActionBarLeftTitleTv = (TextView) findViewById(R.id.title);
        this.mActionBarBackRl = (RelativeLayout) findViewById(R.id.back);
        this.mStartTimeTv = (TextView) findViewById(R.id.sedentary_remind_start_time_tv);
        this.mEndTimeTv = (TextView) findViewById(R.id.sedentary_remind_end_time_tv);
        this.mStartTimeRl = (RelativeLayout) findViewById(R.id.sedentary_remind_start_time_rl);
        this.mEndTimeRl = (RelativeLayout) findViewById(R.id.sedentary_remind_end_time_rl);
        this.mSedentaryRemindOpenImg = (ImageView) findViewById(R.id.sedentary_remind_img);
        this.mLunchNotBreakOpenImg = (ImageView) findViewById(R.id.lunch_not_break_img);
        this.mStartTimeRl.setOnClickListener(this);
        this.mEndTimeRl.setOnClickListener(this);
        this.mSedentaryRemindOpenImg.setOnClickListener(this);
        this.mLunchNotBreakOpenImg.setOnClickListener(this);
    }

    private void initData() {
        int i;
        int i2 = 1;
        this.isRemind = Tools.getSedentaryRemindSwitch();
        this.isBreak = Tools.getLunchNotBreakSwitch();
        initSedentaryRemindTime();
        this.mActionBarLeftTitleTv.setText(R.string.sedentary_remind);
        this.mActionBarBackRl.setOnClickListener(new C11791());
        if (this.isRemind) {
            this.mSedentaryRemindOpenImg.setImageResource(R.drawable.warn_on);
        } else {
            this.mSedentaryRemindOpenImg.setImageResource(R.drawable.warn_off);
        }
        if (this.isBreak) {
            this.mLunchNotBreakOpenImg.setImageResource(R.drawable.warn_on);
        } else {
            this.mLunchNotBreakOpenImg.setImageResource(R.drawable.warn_off);
        }
        this.mStartTimeTv.setText(decimalFormat(this.startTimeHour) + ":" + decimalFormat(this.startTimeMin));
        this.mEndTimeTv.setText(decimalFormat(this.endTimeHour) + ":" + decimalFormat(this.endTimeMin));
        StringBuilder append = new StringBuilder().append("0|");
        if (Tools.getSedentaryRemindSwitch()) {
            i = 1;
        } else {
            i = 0;
        }
        StringBuilder append2 = append.append(i).append("|").append(decimalFormat(this.startTimeHour)).append(decimalFormat(this.startTimeMin)).append("|").append(decimalFormat(this.endTimeHour)).append(decimalFormat(this.endTimeMin)).append("|");
        if (!Tools.getLunchNotBreakSwitch()) {
            i2 = 0;
        }
        this.sedentaryRemindInfo = append2.append(i2).toString();
    }

    public void onClick(View v) {
        int i = 1;
        StringBuilder append;
        int i2;
        StringBuilder append2;
        switch (v.getId()) {
            case R.id.sedentary_remind_img:
                if (this.isRemind) {
                    this.mSedentaryRemindOpenImg.setImageResource(R.drawable.warn_on);
                    this.isRemind = false;
                    Tools.setSedentaryRemindSwitch(true);
                } else {
                    this.mSedentaryRemindOpenImg.setImageResource(R.drawable.warn_off);
                    this.isRemind = true;
                    Tools.setSedentaryRemindSwitch(false);
                }
                append = new StringBuilder().append("0|");
                if (Tools.getSedentaryRemindSwitch()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append2 = append.append(i2).append("|").append(decimalFormat(this.startTimeHour)).append(decimalFormat(this.startTimeMin)).append("|").append(decimalFormat(this.endTimeHour)).append(decimalFormat(this.endTimeMin)).append("|");
                if (!Tools.getLunchNotBreakSwitch()) {
                    i = 0;
                }
                this.sedentaryRemindInfo = append2.append(i).toString();
                return;
            case R.id.sedentary_remind_start_time_rl:
                showTimePickerStartDialog();
                return;
            case R.id.sedentary_remind_end_time_rl:
                showTimePickerEndDialog();
                return;
            case R.id.lunch_not_break_img:
                if (this.isBreak) {
                    this.mLunchNotBreakOpenImg.setImageResource(R.drawable.warn_on);
                    this.isBreak = false;
                    Tools.setLunchNotBreakSwitch(true);
                } else {
                    this.mLunchNotBreakOpenImg.setImageResource(R.drawable.warn_off);
                    this.isBreak = true;
                    Tools.setLunchNotBreakSwitch(false);
                }
                append = new StringBuilder().append("0|");
                if (Tools.getSedentaryRemindSwitch()) {
                    i2 = 1;
                } else {
                    i2 = 0;
                }
                append2 = append.append(i2).append("|").append(decimalFormat(this.startTimeHour)).append(decimalFormat(this.startTimeMin)).append("|").append(decimalFormat(this.endTimeHour)).append(decimalFormat(this.endTimeMin)).append("|");
                if (!Tools.getLunchNotBreakSwitch()) {
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
        dialog.setTitle(getString(R.string.start_time));
        dialog.getDialog().setOnDismissListener(new C11802());
        view.setOnTimePickedListener(new C18783());
        view.show();
        dialog.show();
    }

    private void showTimePickerEndDialog() {
        TimePickerDialog dialog = new TimePickerDialog(this);
        TimePickerView view = (TimePickerView) dialog.getPickerView();
        view.setTimeRange(0, 0, 23, 59);
        view.setInitTimeIndex(this.endTimeHour, this.endTimeMin);
        view.showLabel(true);
        dialog.setTitle(getString(R.string.end_time));
        dialog.getDialog().setOnDismissListener(new C11814());
        view.setOnTimePickedListener(new C18795());
        view.show();
        dialog.show();
    }

    private String decimalFormat(int time) {
        return new DecimalFormat("#00").format((long) time);
    }

    private void initSedentaryRemindTime() {
        this.sedentaryRemindTime = Tools.getSedentaryRemindTime();
        if (this.sedentaryRemindTime != null && !TextUtils.isEmpty(this.sedentaryRemindTime)) {
            int remindTime = Integer.valueOf(this.sedentaryRemindTime).intValue();
            this.endTimeMin = remindTime % 100;
            this.endTimeHour = (remindTime / 100) % 100;
            this.startTimeMin = (remindTime / 10000) % 100;
            this.startTimeHour = remindTime / 1000000;
        }
    }

    private void broadSedentaryRemindTimeInfo(String info) {
        Intent intent = new Intent(BleManagerService.ACTION_UPDATE_SEDENTARY_INFO);
        intent.putExtra("sedentary_info", info);
        sendBroadcast(intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.i("hph", "onBackPressed sedentaryRemindInfo=" + this.sedentaryRemindInfo);
        broadSedentaryRemindTimeInfo(this.sedentaryRemindInfo);
    }
}
