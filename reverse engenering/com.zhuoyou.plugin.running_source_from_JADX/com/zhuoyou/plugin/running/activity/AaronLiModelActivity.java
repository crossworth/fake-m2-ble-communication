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
import java.text.DecimalFormat;
import org.andengine.util.time.TimeConstants;

public class AaronLiModelActivity extends BaseActivity implements OnClickListener {
    private CheckBox cbAaronliMode;
    private String disturbanceModeInfo = "";
    private String disturbanceModeTime = "";
    private int endTimeHour = 7;
    private int endTimeMin;
    private boolean isOpen;
    private LinearLayout mActionBarBackRl;
    private TextView mActionBarLeftTitleTv;
    private LinearLayout mEndTimeRl;
    private TextView mEndTimeTv;
    private LinearLayout mStartTimeRl;
    private TextView mStartTimeTv;
    private int startTimeHour = 22;
    private int startTimeMin;

    class C16811 implements OnDismissListener {
        C16811() {
        }

        public void onDismiss(DialogInterface dialog) {
            AaronLiModelActivity.this.disturbanceModeInfo = "1|" + (SPUtils.getDisturbanceModleSwitch() ? 1 : 0) + "|" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeMin) + "|" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin) + "||";
            AaronLiModelActivity.this.disturbanceModeTime = AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeMin) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin);
            SPUtils.setDisturbanceModleTime(AaronLiModelActivity.this.disturbanceModeTime);
            AaronLiModelActivity.this.mStartTimeTv.setText(AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeHour) + ":" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeMin));
            Log.i("hph", "start disturbanceModeTime=" + AaronLiModelActivity.this.disturbanceModeTime);
            Log.i("hph", "start disturbanceModeInfo=" + AaronLiModelActivity.this.disturbanceModeInfo);
        }
    }

    class C16822 implements OnTimePickedListener {
        C16822() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            AaronLiModelActivity.this.startTimeHour = hourIndex;
            AaronLiModelActivity.this.startTimeMin = minuteIndex;
        }
    }

    class C16833 implements OnDismissListener {
        C16833() {
        }

        public void onDismiss(DialogInterface dialog) {
            if (AaronLiModelActivity.this.endTimeHour > AaronLiModelActivity.this.startTimeHour) {
                AaronLiModelActivity.this.mEndTimeTv.setText(AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + ":" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin));
            } else if (AaronLiModelActivity.this.endTimeMin > AaronLiModelActivity.this.startTimeMin) {
                AaronLiModelActivity.this.mEndTimeTv.setText(AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + ":" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin));
            } else {
                AaronLiModelActivity.this.mEndTimeTv.setText(AaronLiModelActivity.this.getString(C1680R.string.next_day) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + ":" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin));
            }
            AaronLiModelActivity.this.disturbanceModeInfo = "1|" + (SPUtils.getDisturbanceModleSwitch() ? 1 : 0) + "|" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeMin) + "|" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin) + "||";
            AaronLiModelActivity.this.disturbanceModeTime = AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeMin) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin);
            SPUtils.setDisturbanceModleTime(AaronLiModelActivity.this.disturbanceModeTime);
            Log.i("hph", "end disturbanceModeTime=" + AaronLiModelActivity.this.disturbanceModeTime);
            Log.i("hph", "end disturbanceModeInfo=" + AaronLiModelActivity.this.disturbanceModeInfo);
        }
    }

    class C16844 implements OnTimePickedListener {
        C16844() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            AaronLiModelActivity.this.endTimeHour = hourIndex;
            AaronLiModelActivity.this.endTimeMin = minuteIndex;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_aaronli_model);
        initView();
        initData();
    }

    private void initView() {
        this.mStartTimeRl = (LinearLayout) findViewById(C1680R.id.disturbance_mode_start_time_rl);
        this.mEndTimeRl = (LinearLayout) findViewById(C1680R.id.disturbance_mode_end_time_rl);
        this.mStartTimeTv = (TextView) findViewById(C1680R.id.disturbance_mode_start_time_tv);
        this.mEndTimeTv = (TextView) findViewById(C1680R.id.disturbance_mode_end_time_tv);
        this.cbAaronliMode = (CheckBox) findViewById(C1680R.id.cb_disturbance_mode);
        this.mStartTimeRl.setOnClickListener(this);
        this.mEndTimeRl.setOnClickListener(this);
    }

    private void initData() {
        this.isOpen = SPUtils.getDisturbanceModleSwitch();
        this.disturbanceModeTime = SPUtils.getDisturbanceModleTime();
        if (!(this.disturbanceModeTime == null || TextUtils.isEmpty(this.disturbanceModeTime))) {
            int disturbanceTime = Integer.valueOf(this.disturbanceModeTime).intValue();
            this.endTimeMin = disturbanceTime % 100;
            this.endTimeHour = (disturbanceTime / 100) % 100;
            this.startTimeMin = (disturbanceTime / 10000) % 100;
            this.startTimeHour = disturbanceTime / TimeConstants.MICROSECONDS_PER_SECOND;
        }
        this.mStartTimeTv.setText(decimalFormat(this.startTimeHour) + ":" + decimalFormat(this.startTimeMin));
        if (this.endTimeHour > this.startTimeHour) {
            this.mEndTimeTv.setText(decimalFormat(this.endTimeHour) + ":" + decimalFormat(this.endTimeMin));
        } else if (this.endTimeMin > this.startTimeMin) {
            this.mEndTimeTv.setText(decimalFormat(this.endTimeHour) + ":" + decimalFormat(this.endTimeMin));
        } else {
            this.mEndTimeTv.setText(getString(C1680R.string.next_day) + decimalFormat(this.endTimeHour) + ":" + decimalFormat(this.endTimeMin));
        }
        this.cbAaronliMode.setChecked(this.isOpen);
        this.disturbanceModeInfo = "1|" + (SPUtils.getDisturbanceModleSwitch() ? 1 : 0) + "|" + decimalFormat(this.startTimeHour) + decimalFormat(this.startTimeMin) + "|" + decimalFormat(this.endTimeHour) + decimalFormat(this.endTimeMin) + "||";
    }

    public void onClick(View v) {
        int i = 1;
        switch (v.getId()) {
            case C1680R.id.cb_disturbance_mode:
                this.isOpen = this.cbAaronliMode.isChecked();
                if (this.isOpen) {
                    this.cbAaronliMode.setChecked(this.isOpen);
                    SPUtils.setDisturbanceModleSwitch(true);
                } else {
                    this.cbAaronliMode.setChecked(false);
                    SPUtils.setDisturbanceModleSwitch(false);
                }
                StringBuilder append = new StringBuilder().append("1|");
                if (!SPUtils.getDisturbanceModleSwitch()) {
                    i = 0;
                }
                this.disturbanceModeInfo = append.append(i).append("|").append(decimalFormat(this.startTimeHour)).append(decimalFormat(this.startTimeMin)).append("|").append(decimalFormat(this.endTimeHour)).append(decimalFormat(this.endTimeMin)).append("||").toString();
                return;
            case C1680R.id.disturbance_mode_start_time_rl:
                showTimePickerStartDialog();
                return;
            case C1680R.id.disturbance_mode_end_time_rl:
                showTimePickerEndDialog();
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
        dialog.getDialog().setOnDismissListener(new C16811());
        view.setOnTimePickedListener(new C16822());
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
        dialog.getDialog().setOnDismissListener(new C16833());
        view.setOnTimePickedListener(new C16844());
        view.show();
        dialog.show();
    }

    private String decimalFormat(int time) {
        return new DecimalFormat("#00").format((long) time);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.i("hph", "onBackPressed disturbanceModeInfo=" + this.disturbanceModeInfo);
        BtDevice device = BtManagerService.getConnectDevice(null);
        if (device != null) {
            device.setSedentary(this.disturbanceModeInfo);
        }
    }
}
