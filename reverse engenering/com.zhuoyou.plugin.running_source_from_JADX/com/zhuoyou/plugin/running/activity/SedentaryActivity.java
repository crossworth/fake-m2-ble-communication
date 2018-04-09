package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;
import com.droi.library.pickerviews.picker.NumberPickerView;
import com.droi.library.pickerviews.picker.NumberPickerView.OnNumberPickedListener;
import com.droi.library.pickerviews.picker.TimePickerView;
import com.droi.library.pickerviews.picker.TimePickerView.OnTimePickedListener;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.base.BaseActivity;
import java.text.DecimalFormat;

public class SedentaryActivity extends BaseActivity {
    private static final int TP_TEXT_COLOR_CENTER = -15817730;
    private static final int TP_TEXT_COLOR_OUT = -1290886146;
    private static final int TP_TEXT_SIZE = 32;
    private DecimalFormat intFormat = new DecimalFormat("#00");
    private NumberPickerView npDuration;
    private TimePickerView tpEndTime;
    private TimePickerView tpStartTime;
    private TextView tvDurationTime;
    private TextView tvEndTime;
    private TextView tvStartTime;

    class C17981 implements OnTimePickedListener {
        C17981() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            SedentaryActivity.this.tvStartTime.setText(SedentaryActivity.this.intFormat.format((long) hourIndex) + ":" + SedentaryActivity.this.intFormat.format((long) minuteIndex));
        }
    }

    class C17992 implements OnTimePickedListener {
        C17992() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            SedentaryActivity.this.tvEndTime.setText(SedentaryActivity.this.intFormat.format((long) hourIndex) + ":" + SedentaryActivity.this.intFormat.format((long) minuteIndex));
        }
    }

    class C18003 implements OnNumberPickedListener {
        C18003() {
        }

        public void onNumberPicked(int index, int number) {
            SedentaryActivity.this.tvDurationTime.setText(number + "");
        }
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_sedentary);
        initView();
        initData();
    }

    private void initView() {
        this.tpStartTime = (TimePickerView) findViewById(C1680R.id.tp_start_time);
        this.tpEndTime = (TimePickerView) findViewById(C1680R.id.tp_end_time);
        this.npDuration = (NumberPickerView) findViewById(C1680R.id.np_duration_num);
        this.tvStartTime = (TextView) findViewById(C1680R.id.tv_start_time);
        this.tvEndTime = (TextView) findViewById(C1680R.id.tv_end_time);
        this.tvDurationTime = (TextView) findViewById(C1680R.id.tv_interval_time);
        this.tpStartTime.setTextColorCenter(TP_TEXT_COLOR_CENTER);
        this.tpStartTime.setTextColorOut(TP_TEXT_COLOR_OUT);
        this.tpStartTime.setTextSize(32);
        this.tpStartTime.setOnTimePickedListener(new C17981());
        this.tpEndTime.setTextColorCenter(TP_TEXT_COLOR_CENTER);
        this.tpEndTime.setTextColorOut(TP_TEXT_COLOR_OUT);
        this.tpEndTime.setTextSize(32);
        this.tpEndTime.setOnTimePickedListener(new C17992());
        this.npDuration.setTextColorCenter(TP_TEXT_COLOR_CENTER);
        this.npDuration.setTextColorOut(TP_TEXT_COLOR_OUT);
        this.npDuration.setTextSize(32);
        this.npDuration.setInitNumberPicked(30);
        this.npDuration.setNumberRange(30, 120);
        this.npDuration.setOnNumberPickedListener(new C18003());
    }

    private void initData() {
        this.tpStartTime.show();
        this.tpEndTime.show();
        this.npDuration.show();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.rl_start_time:
                this.tpStartTime.setVisibility(0);
                this.tpEndTime.setVisibility(8);
                this.npDuration.setVisibility(8);
                return;
            case C1680R.id.rl_end_time:
                this.tpStartTime.setVisibility(8);
                this.tpEndTime.setVisibility(0);
                this.npDuration.setVisibility(8);
                return;
            case C1680R.id.rl_duration:
                this.tpStartTime.setVisibility(8);
                this.tpEndTime.setVisibility(8);
                this.npDuration.setVisibility(0);
                return;
            default:
                return;
        }
    }
}
