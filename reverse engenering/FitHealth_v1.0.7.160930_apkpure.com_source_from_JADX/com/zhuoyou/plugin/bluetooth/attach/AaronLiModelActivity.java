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

public class AaronLiModelActivity extends Activity implements OnClickListener {
    private String disturbanceModeInfo = "";
    private String disturbanceModeTime = "";
    private int endTimeHour = 7;
    private int endTimeMin;
    private boolean isOpen;
    private RelativeLayout mActionBarBackRl;
    private TextView mActionBarLeftTitleTv;
    private ImageView mDisturbanceModeSwitchImg;
    private RelativeLayout mEndTimeRl;
    private TextView mEndTimeTv;
    private RelativeLayout mStartTimeRl;
    private TextView mStartTimeTv;
    private int startTimeHour = 22;
    private int startTimeMin;

    class C11641 implements OnClickListener {
        C11641() {
        }

        public void onClick(View v) {
            AaronLiModelActivity.this.broadDisturbanceTimeInfo(AaronLiModelActivity.this.disturbanceModeInfo);
            AaronLiModelActivity.this.finish();
        }
    }

    class C11652 implements OnDismissListener {
        C11652() {
        }

        public void onDismiss(DialogInterface dialog) {
            AaronLiModelActivity.this.disturbanceModeInfo = "1|" + (Tools.getDisturbanceModleSwitch() ? 1 : 0) + "|" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeMin) + "|" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin) + "||";
            AaronLiModelActivity.this.disturbanceModeTime = AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeMin) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin);
            Tools.setDisturbanceModleTime(AaronLiModelActivity.this.disturbanceModeTime);
            AaronLiModelActivity.this.mStartTimeTv.setText(AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeHour) + ":" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeMin));
            Log.i("hph", "start disturbanceModeTime=" + AaronLiModelActivity.this.disturbanceModeTime);
            Log.i("hph", "start disturbanceModeInfo=" + AaronLiModelActivity.this.disturbanceModeInfo);
        }
    }

    class C11664 implements OnDismissListener {
        C11664() {
        }

        public void onDismiss(DialogInterface dialog) {
            if (AaronLiModelActivity.this.endTimeHour > AaronLiModelActivity.this.startTimeHour) {
                AaronLiModelActivity.this.mEndTimeTv.setText(AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + ":" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin));
            } else if (AaronLiModelActivity.this.endTimeMin > AaronLiModelActivity.this.startTimeMin) {
                AaronLiModelActivity.this.mEndTimeTv.setText(AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + ":" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin));
            } else {
                AaronLiModelActivity.this.mEndTimeTv.setText(AaronLiModelActivity.this.getString(R.string.next_day) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + ":" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin));
            }
            AaronLiModelActivity.this.disturbanceModeInfo = "1|" + (Tools.getDisturbanceModleSwitch() ? 1 : 0) + "|" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeMin) + "|" + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin) + "||";
            AaronLiModelActivity.this.disturbanceModeTime = AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.startTimeMin) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeHour) + AaronLiModelActivity.this.decimalFormat(AaronLiModelActivity.this.endTimeMin);
            Tools.setDisturbanceModleTime(AaronLiModelActivity.this.disturbanceModeTime);
            Log.i("hph", "end disturbanceModeTime=" + AaronLiModelActivity.this.disturbanceModeTime);
            Log.i("hph", "end disturbanceModeInfo=" + AaronLiModelActivity.this.disturbanceModeInfo);
        }
    }

    class C18763 implements OnTimePickedListener {
        C18763() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            AaronLiModelActivity.this.startTimeHour = hourIndex;
            AaronLiModelActivity.this.startTimeMin = minuteIndex;
        }
    }

    class C18775 implements OnTimePickedListener {
        C18775() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            AaronLiModelActivity.this.endTimeHour = hourIndex;
            AaronLiModelActivity.this.endTimeMin = minuteIndex;
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aaronli_model);
        initView();
        initData();
    }

    private void initView() {
        this.mActionBarLeftTitleTv = (TextView) findViewById(R.id.title);
        this.mActionBarBackRl = (RelativeLayout) findViewById(R.id.back);
        this.mDisturbanceModeSwitchImg = (ImageView) findViewById(R.id.disturbance_mode_switch_img);
        this.mStartTimeRl = (RelativeLayout) findViewById(R.id.disturbance_mode_start_time_rl);
        this.mEndTimeRl = (RelativeLayout) findViewById(R.id.disturbance_mode_end_time_rl);
        this.mStartTimeTv = (TextView) findViewById(R.id.disturbance_mode_start_time_tv);
        this.mEndTimeTv = (TextView) findViewById(R.id.disturbance_mode_end_time_tv);
        this.mDisturbanceModeSwitchImg.setOnClickListener(this);
        this.mStartTimeRl.setOnClickListener(this);
        this.mEndTimeRl.setOnClickListener(this);
    }

    private void initData() {
        this.isOpen = Tools.getDisturbanceModleSwitch();
        this.disturbanceModeTime = Tools.getDisturbanceModleTime();
        if (!(this.disturbanceModeTime == null || TextUtils.isEmpty(this.disturbanceModeTime))) {
            int disturbanceTime = Integer.valueOf(this.disturbanceModeTime).intValue();
            this.endTimeMin = disturbanceTime % 100;
            this.endTimeHour = (disturbanceTime / 100) % 100;
            this.startTimeMin = (disturbanceTime / 10000) % 100;
            this.startTimeHour = disturbanceTime / 1000000;
        }
        this.mStartTimeTv.setText(decimalFormat(this.startTimeHour) + ":" + decimalFormat(this.startTimeMin));
        if (this.endTimeHour > this.startTimeHour) {
            this.mEndTimeTv.setText(decimalFormat(this.endTimeHour) + ":" + decimalFormat(this.endTimeMin));
        } else if (this.endTimeMin > this.startTimeMin) {
            this.mEndTimeTv.setText(decimalFormat(this.endTimeHour) + ":" + decimalFormat(this.endTimeMin));
        } else {
            this.mEndTimeTv.setText(getString(R.string.next_day) + decimalFormat(this.endTimeHour) + ":" + decimalFormat(this.endTimeMin));
        }
        this.mActionBarLeftTitleTv.setText(R.string.disturbance_mode);
        this.mActionBarBackRl.setOnClickListener(new C11641());
        if (this.isOpen) {
            this.mDisturbanceModeSwitchImg.setImageResource(R.drawable.warn_on);
        } else {
            this.mDisturbanceModeSwitchImg.setImageResource(R.drawable.warn_off);
        }
        this.disturbanceModeInfo = "1|" + (Tools.getDisturbanceModleSwitch() ? 1 : 0) + "|" + decimalFormat(this.startTimeHour) + decimalFormat(this.startTimeMin) + "|" + decimalFormat(this.endTimeHour) + decimalFormat(this.endTimeMin) + "||";
    }

    public void onClick(View v) {
        int i = 1;
        switch (v.getId()) {
            case R.id.disturbance_mode_switch_img:
                if (this.isOpen) {
                    this.mDisturbanceModeSwitchImg.setImageResource(R.drawable.warn_on);
                    this.isOpen = false;
                    Tools.setDisturbanceModleSwitch(true);
                } else {
                    this.mDisturbanceModeSwitchImg.setImageResource(R.drawable.warn_off);
                    this.isOpen = true;
                    Tools.setDisturbanceModleSwitch(false);
                }
                StringBuilder append = new StringBuilder().append("1|");
                if (!Tools.getDisturbanceModleSwitch()) {
                    i = 0;
                }
                this.disturbanceModeInfo = append.append(i).append("|").append(decimalFormat(this.startTimeHour)).append(decimalFormat(this.startTimeMin)).append("|").append(decimalFormat(this.endTimeHour)).append(decimalFormat(this.endTimeMin)).append("||").toString();
                return;
            case R.id.disturbance_mode_start_time_rl:
                showTimePickerStartDialog();
                return;
            case R.id.disturbance_mode_end_time_rl:
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
        dialog.setTitle(getString(R.string.start_time));
        dialog.getDialog().setOnDismissListener(new C11652());
        view.setOnTimePickedListener(new C18763());
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
        dialog.getDialog().setOnDismissListener(new C11664());
        view.setOnTimePickedListener(new C18775());
        view.show();
        dialog.show();
    }

    private String decimalFormat(int time) {
        return new DecimalFormat("#00").format((long) time);
    }

    private void broadDisturbanceTimeInfo(String info) {
        Intent intent = new Intent(BleManagerService.ACTION_UPDATE_SEDENTARY_INFO);
        intent.putExtra("sedentary_info", info);
        sendBroadcast(intent);
    }

    public void onBackPressed() {
        super.onBackPressed();
        Log.i("hph", "onBackPressed disturbanceModeInfo=" + this.disturbanceModeInfo);
        broadDisturbanceTimeInfo(this.disturbanceModeInfo);
    }
}
