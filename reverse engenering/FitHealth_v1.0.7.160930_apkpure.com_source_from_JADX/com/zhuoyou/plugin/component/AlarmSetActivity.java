package com.zhuoyou.plugin.component;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import com.droi.library.pickerviews.picker.TimePickerView;
import com.droi.library.pickerviews.picker.TimePickerView.OnTimePickedListener;
import com.facebook.internal.NativeProtocol;
import com.fithealth.running.R;
import com.zhuoyou.plugin.info.WheelTextAdapter;
import com.zhuoyou.plugin.view.WheelView;

public class AlarmSetActivity extends Activity implements OnClickListener {
    private Button bt_alarmCustoms;
    private Button bt_alarmEveryday;
    private Button bt_alarmOnce;
    private Button bt_alarmWorkdays;
    private Button bt_brainOff;
    private WheelTextAdapter hourAdaptor;
    private AlarmBean mBean;
    private WheelTextAdapter minAdaptor;
    private final int requestCode = NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY;
    private int selectPostion;
    private final String tag = "AlarmSetActivity";
    private TimePickerView timePickerView;
    private WheelView wv_hour;
    private WheelView wv_min;

    class C18801 implements OnTimePickedListener {
        C18801() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            AlarmSetActivity.this.mBean.setHour(hourIndex);
            AlarmSetActivity.this.mBean.setMin(minuteIndex);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("AlarmSetActivity", "onCreate");
        setContentView(R.layout.alarm_set_activity);
        findView();
        initTimePickerView();
        initView();
    }

    private void findView() {
        this.mBean = (AlarmBean) getIntent().getSerializableExtra("alarmbean");
        if (this.mBean == null) {
            this.mBean = new AlarmBean();
        }
        this.bt_brainOff = (Button) findViewById(R.id.button_alarm_switch);
        this.bt_alarmOnce = (Button) findViewById(R.id.alarm_once);
        this.bt_alarmEveryday = (Button) findViewById(R.id.alarm_everyday);
        this.bt_alarmWorkdays = (Button) findViewById(R.id.alarm_workdays);
        this.bt_alarmCustoms = (Button) findViewById(R.id.alarm_cumtoms);
        this.timePickerView = (TimePickerView) findViewById(R.id.time_picker_view);
        this.bt_brainOff.setOnClickListener(this);
        this.bt_alarmOnce.setOnClickListener(this);
        this.bt_alarmEveryday.setOnClickListener(this);
        this.bt_alarmWorkdays.setOnClickListener(this);
        this.bt_alarmCustoms.setOnClickListener(this);
    }

    private void initView() {
        if (this.mBean.isBrain()) {
            this.bt_brainOff.setBackgroundResource(R.drawable.alarm_button_openon);
        } else {
            this.bt_brainOff.setBackgroundResource(R.drawable.alarm_button_closeoff);
        }
        this.bt_alarmOnce.setBackgroundResource(R.drawable.alarm_button_unselected);
        this.bt_alarmEveryday.setBackgroundResource(R.drawable.alarm_button_unselected);
        this.bt_alarmWorkdays.setBackgroundResource(R.drawable.alarm_button_unselected);
        this.bt_alarmCustoms.setBackgroundResource(R.drawable.alarm_button_unselected);
        int resColor = Color.parseColor("#c7c7c7");
        this.bt_alarmOnce.setTextColor(resColor);
        this.bt_alarmEveryday.setTextColor(resColor);
        this.bt_alarmWorkdays.setTextColor(resColor);
        this.bt_alarmCustoms.setTextColor(resColor);
        int type = this.mBean.getOpenType();
        if (type == 0) {
            this.bt_alarmOnce.setTextColor(-1);
            this.bt_alarmOnce.setBackgroundResource(R.drawable.alarm_button_selected);
        } else if (type == 1) {
            this.bt_alarmEveryday.setTextColor(-1);
            this.bt_alarmEveryday.setBackgroundResource(R.drawable.alarm_button_selected);
        } else if (type == 2) {
            this.bt_alarmWorkdays.setTextColor(-1);
            this.bt_alarmWorkdays.setBackgroundResource(R.drawable.alarm_button_selected);
        } else if (type == 3) {
            this.bt_alarmCustoms.setTextColor(-1);
            this.bt_alarmCustoms.setBackgroundResource(R.drawable.alarm_button_selected);
        }
    }

    private void initTimePickerView() {
        this.timePickerView.setInitTimeIndex(this.mBean.getHour(), this.mBean.getMin());
        this.timePickerView.showLabel(true);
        this.timePickerView.show();
        this.timePickerView.setOnTimePickedListener(new C18801());
    }

    public void onClick(View view) {
        boolean z = true;
        switch (view.getId()) {
            case R.id.back:
                finishActivity();
                break;
            case R.id.button_alarm_switch:
                AlarmBean alarmBean = this.mBean;
                if (this.mBean.isBrain()) {
                    z = false;
                }
                alarmBean.setBrain(z);
                break;
            case R.id.alarm_once:
                this.mBean.setOpenType(0);
                break;
            case R.id.alarm_everyday:
                this.mBean.setOpenType(1);
                break;
            case R.id.alarm_workdays:
                this.mBean.setOpenType(2);
                break;
            case R.id.alarm_cumtoms:
                this.mBean.setOpenType(3);
                Intent intent = new Intent(this, AlarmDateActivity.class);
                intent.putExtra("alarmbean", this.mBean);
                startActivityForResult(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY);
                break;
        }
        initView();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("AlarmSetActivity", "onActivityResult");
        getClass();
        if (requestCode == NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REPLY) {
            this.mBean = (AlarmBean) data.getSerializableExtra("alarmReturn");
            initView();
        }
    }

    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity() {
        Intent intent = new Intent();
        intent.putExtra("alarmReturn", this.mBean);
        setResult(100, intent);
        finish();
    }

    private String[] getHourArray() {
        return getResources().getStringArray(R.array.hour);
    }

    private String[] getMinArray() {
        return getResources().getStringArray(R.array.minute);
    }

    protected void onResume() {
        super.onResume();
        Log.i("AlarmSetActivity", "onResume");
    }
}
