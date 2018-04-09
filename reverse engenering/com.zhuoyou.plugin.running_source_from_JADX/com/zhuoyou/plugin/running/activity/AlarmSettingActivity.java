package com.zhuoyou.plugin.running.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CheckBox;
import android.widget.TextView;
import com.droi.library.pickerviews.picker.TimePickerView;
import com.droi.library.pickerviews.picker.TimePickerView.OnTimePickedListener;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.baas.Rank;
import com.zhuoyou.plugin.running.base.BaseActivity;
import com.zhuoyou.plugin.running.bean.AlarmData;
import com.zhuoyou.plugin.running.tools.SPUtils;
import com.zhuoyou.plugin.running.tools.Tools;
import com.zhuoyou.plugin.running.view.MyActionBar;
import java.util.Calendar;
import org.andengine.util.time.TimeConstants;

public class AlarmSettingActivity extends BaseActivity {
    private MyActionBar actionBar;
    private AlarmData data;
    private int id;
    private CheckBox imgFri;
    private CheckBox imgMon;
    private CheckBox imgSat;
    private CheckBox imgSun;
    private CheckBox imgThu;
    private CheckBox imgTue;
    private CheckBox imgWed;
    private TimePickerView timePickView;
    private TextView tvRepeatAlways;
    private TextView tvRepeatCustom;
    private TextView tvRepeatOnce;
    private TextView tvRepeatWork;

    class C16921 implements OnClickListener {
        C16921() {
        }

        public void onClick(View v) {
            AlarmSettingActivity.this.saveAlarmInfo();
            AlarmSettingActivity.this.finish();
        }
    }

    class C16932 implements OnTimePickedListener {
        C16932() {
        }

        public void onTimePicked(int hourIndex, int minuteIndex, String timeStr) {
            AlarmSettingActivity.this.data.setHour(hourIndex);
            AlarmSettingActivity.this.data.setMin(minuteIndex);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C1680R.layout.activity_alarm_setting);
        this.id = getIntent().getIntExtra("alarm_id", 0);
        this.data = SPUtils.getAlarmNoCheck(this.id);
        initView();
        initData();
    }

    private void initView() {
        this.actionBar = (MyActionBar) findViewById(C1680R.id.actionbar);
        this.actionBar.setOnBackTitleClickListener(new C16921());
        this.imgMon = (CheckBox) findViewById(C1680R.id.img_mon);
        this.imgTue = (CheckBox) findViewById(C1680R.id.img_tue);
        this.imgWed = (CheckBox) findViewById(C1680R.id.img_wed);
        this.imgThu = (CheckBox) findViewById(C1680R.id.img_thu);
        this.imgFri = (CheckBox) findViewById(C1680R.id.img_fri);
        this.imgSat = (CheckBox) findViewById(C1680R.id.img_sat);
        this.imgSun = (CheckBox) findViewById(C1680R.id.img_sun);
        this.tvRepeatOnce = (TextView) findViewById(C1680R.id.tv_repeat_once);
        this.tvRepeatAlways = (TextView) findViewById(C1680R.id.tv_repeat_always);
        this.tvRepeatWork = (TextView) findViewById(C1680R.id.tv_repeat_work);
        this.tvRepeatCustom = (TextView) findViewById(C1680R.id.tv_repeat_custom);
        this.timePickView = (TimePickerView) findViewById(C1680R.id.alarm_time_picker);
        int color = getResources().getColor(C1680R.color.main_picker_color);
        int colorout = getResources().getColor(C1680R.color.main_picker_color_out);
        this.timePickView.setTextColorCenter(color);
        this.timePickView.setTextColorOut(colorout);
        this.timePickView.setDividerColor(color);
        this.timePickView.setDividerWidth(1.0f);
        this.timePickView.setTextSize(32);
    }

    private void initData() {
        updateRepeat(this.data.getCustomData());
        updateMode();
        this.timePickView.setInitTimeIndex(this.data.getHour(), this.data.getMin());
        this.timePickView.setOnTimePickedListener(new C16932());
        this.timePickView.show();
    }

    private void updateRepeat(int custom) {
        boolean z;
        boolean z2 = true;
        Log.i("zhuqichao", "custom=" + custom);
        this.imgMon.setChecked(custom % 10 == 1);
        custom /= 10;
        CheckBox checkBox = this.imgTue;
        if (custom % 10 == 1) {
            z = true;
        } else {
            z = false;
        }
        checkBox.setChecked(z);
        custom /= 10;
        checkBox = this.imgWed;
        if (custom % 10 == 1) {
            z = true;
        } else {
            z = false;
        }
        checkBox.setChecked(z);
        custom /= 10;
        checkBox = this.imgThu;
        if (custom % 10 == 1) {
            z = true;
        } else {
            z = false;
        }
        checkBox.setChecked(z);
        custom /= 10;
        checkBox = this.imgFri;
        if (custom % 10 == 1) {
            z = true;
        } else {
            z = false;
        }
        checkBox.setChecked(z);
        custom /= 10;
        checkBox = this.imgSat;
        if (custom % 10 == 1) {
            z = true;
        } else {
            z = false;
        }
        checkBox.setChecked(z);
        custom /= 10;
        CheckBox checkBox2 = this.imgSun;
        if (custom % 10 != 1) {
            z2 = false;
        }
        checkBox2.setChecked(z2);
    }

    private void updateMode() {
        boolean z;
        boolean z2 = true;
        TextView textView = this.tvRepeatAlways;
        if (this.data.getOpenType() == AlarmData.OPEN_TYPE_REPEAT) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        textView = this.tvRepeatOnce;
        if (this.data.getOpenType() == AlarmData.OPEN_TYPE_ONCE) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        textView = this.tvRepeatWork;
        if (this.data.getOpenType() == AlarmData.OPEN_TYPE_WORK) {
            z = true;
        } else {
            z = false;
        }
        textView.setSelected(z);
        TextView textView2 = this.tvRepeatCustom;
        if (this.data.getOpenType() != AlarmData.OPEN_TYPE_CUSTOM) {
            z2 = false;
        }
        textView2.setSelected(z2);
    }

    private int getCustomData() {
        int i;
        int i2 = 1;
        int i3 = this.imgMon.isChecked() ? 1 : 0;
        if (this.imgTue.isChecked()) {
            i = 1;
        } else {
            i = 0;
        }
        i = (i * 10) + i3;
        if (this.imgWed.isChecked()) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        i += i3 * 100;
        if (this.imgThu.isChecked()) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        i += i3 * 1000;
        if (this.imgFri.isChecked()) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        i += i3 * 10000;
        if (this.imgSat.isChecked()) {
            i3 = 1;
        } else {
            i3 = 0;
        }
        i3 = (i3 * Rank.MIN) + i;
        if (!this.imgSun.isChecked()) {
            i2 = 0;
        }
        return i3 + (i2 * TimeConstants.MICROSECONDS_PER_SECOND);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case C1680R.id.img_mon:
            case C1680R.id.img_tue:
            case C1680R.id.img_wed:
            case C1680R.id.img_thu:
            case C1680R.id.img_fri:
            case C1680R.id.img_sat:
            case C1680R.id.img_sun:
                int custom = getCustomData();
                if (custom == 0) {
                    this.data.setOpenType(AlarmData.OPEN_TYPE_ONCE);
                } else if (custom == 1111111) {
                    this.data.setOpenType(AlarmData.OPEN_TYPE_REPEAT);
                } else if (custom == 11111) {
                    this.data.setOpenType(AlarmData.OPEN_TYPE_WORK);
                } else {
                    this.data.setOpenType(AlarmData.OPEN_TYPE_CUSTOM);
                    this.data.setCustomData(custom);
                }
                updateMode();
                return;
            case C1680R.id.tv_repeat_once:
                this.data.setOpenType(AlarmData.OPEN_TYPE_ONCE);
                updateMode();
                updateRepeat(0);
                return;
            case C1680R.id.tv_repeat_always:
                this.data.setOpenType(AlarmData.OPEN_TYPE_REPEAT);
                updateMode();
                updateRepeat(1111111);
                return;
            case C1680R.id.tv_repeat_work:
                this.data.setOpenType(AlarmData.OPEN_TYPE_WORK);
                updateMode();
                updateRepeat(11111);
                return;
            case C1680R.id.tv_repeat_custom:
                this.data.setOpenType(AlarmData.OPEN_TYPE_CUSTOM);
                updateMode();
                updateRepeat(this.data.getCustomData());
                return;
            default:
                return;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
        saveAlarmInfo();
    }

    private void saveAlarmInfo() {
        this.data.setCustomData(getCustomData());
        SPUtils.saveAlarm(this.data);
        Tools.makeToast((int) C1680R.string.alarm_save_success);
        if (this.data.getOpenType() == AlarmData.OPEN_TYPE_ONCE) {
            SPUtils.saveOnceAlarmTime(this.data.getId(), getAlarmTime());
        }
    }

    private long getAlarmTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(13, 0);
        calendar.set(14, 0);
        Calendar today = (Calendar) calendar.clone();
        calendar.set(11, this.data.getHour());
        calendar.set(12, this.data.getMin());
        if (calendar.before(today)) {
            calendar.add(6, 1);
        }
        return calendar.getTimeInMillis();
    }
}
