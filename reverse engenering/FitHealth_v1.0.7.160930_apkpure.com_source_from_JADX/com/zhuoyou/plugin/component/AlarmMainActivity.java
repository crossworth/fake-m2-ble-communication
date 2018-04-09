package com.zhuoyou.plugin.component;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import com.fithealth.running.R;
import com.zhuoyou.plugin.ble.BleManagerService;
import com.zhuoyou.plugin.receiver.PlugSendMessage;
import com.zhuoyou.plugin.running.Tools;
import java.text.DecimalFormat;
import java.util.Calendar;

public class AlarmMainActivity extends Activity implements OnClickListener {
    public static final String ACTION_ALARM_VIEW_CHANGE = "com.tyd.plugin.alarm.view.change";
    private Button bt_AlarmOff0;
    private Button bt_AlarmOff1;
    private Button bt_AlarmOff2;
    private DecimalFormat intFormat = new DecimalFormat("#00");
    private AlarmBean mBean0;
    private AlarmBean mBean1;
    private AlarmBean mBean2;
    private Calendar mCalender;
    private BroadcastReceiver mReceiver = new C12241();
    private final int requestCode = 65536;
    private TextView tv_AlarmDate0;
    private TextView tv_AlarmDate1;
    private TextView tv_AlarmDate2;
    private TextView tv_AlarmNofy0;
    private TextView tv_AlarmNofy1;
    private TextView tv_AlarmNofy2;
    private TextView tv_AlarmTime0;
    private TextView tv_AlarmTime1;
    private TextView tv_AlarmTime2;

    class C12241 extends BroadcastReceiver {
        C12241() {
        }

        public void onReceive(Context context, Intent intent) {
            Log.i("chenxin", "get receiver action:com.tyd.plugin.alarm.view.change");
            AlarmMainActivity.this.getData();
            AlarmMainActivity.this.setView();
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alarm_activity);
        findView();
        getData();
        initReceiver();
        setView();
    }

    private void findView() {
        this.tv_AlarmTime0 = (TextView) findViewById(R.id.text_alarm0_time);
        this.tv_AlarmTime1 = (TextView) findViewById(R.id.text_alarm1_time);
        this.tv_AlarmTime2 = (TextView) findViewById(R.id.text_alarm2_time);
        this.tv_AlarmDate0 = (TextView) findViewById(R.id.text_alarm0_date);
        this.tv_AlarmDate1 = (TextView) findViewById(R.id.text_alarm1_date);
        this.tv_AlarmDate2 = (TextView) findViewById(R.id.text_alarm2_date);
        this.tv_AlarmNofy0 = (TextView) findViewById(R.id.text_alarm0_notify);
        this.tv_AlarmNofy1 = (TextView) findViewById(R.id.text_alarm1_notify);
        this.tv_AlarmNofy2 = (TextView) findViewById(R.id.text_alarm2_notify);
        this.bt_AlarmOff0 = (Button) findViewById(R.id.button_alarm0_off);
        this.bt_AlarmOff1 = (Button) findViewById(R.id.button_alarm1_off);
        this.bt_AlarmOff2 = (Button) findViewById(R.id.button_alarm2_off);
    }

    private void getData() {
        boolean z = true;
        this.mBean0 = new AlarmBean();
        this.mBean0.setId(0);
        this.mBean1 = new AlarmBean();
        this.mBean1.setId(1);
        this.mBean2 = new AlarmBean();
        this.mBean2.setId(2);
        String alarmSt = Tools.getAlarmBrain();
        Log.i("hepenghui", "alarmSt:" + alarmSt);
        Log.i("hepenghui", "alarmSt:" + alarmSt.length());
        if (alarmSt.length() == 63) {
            boolean z2;
            this.mBean0.setHour(Integer.valueOf(alarmSt.substring(0, 2)).intValue());
            this.mBean1.setHour(Integer.valueOf(alarmSt.substring(21, 23)).intValue());
            this.mBean2.setHour(Integer.valueOf(alarmSt.substring(42, 44)).intValue());
            this.mBean0.setMin(Integer.valueOf(alarmSt.substring(2, 4)).intValue());
            this.mBean1.setMin(Integer.valueOf(alarmSt.substring(23, 25)).intValue());
            this.mBean2.setMin(Integer.valueOf(alarmSt.substring(44, 46)).intValue());
            AlarmBean alarmBean = this.mBean0;
            if (alarmSt.substring(7, 8).equals("1")) {
                z2 = true;
            } else {
                z2 = false;
            }
            alarmBean.setBrain(z2);
            alarmBean = this.mBean1;
            if (alarmSt.substring(28, 29).equals("1")) {
                z2 = true;
            } else {
                z2 = false;
            }
            alarmBean.setBrain(z2);
            alarmBean = this.mBean2;
            if (alarmSt.substring(49, 50).equals("1")) {
                z2 = true;
            } else {
                z2 = false;
            }
            alarmBean.setBrain(z2);
            alarmBean = this.mBean0;
            if (alarmSt.substring(9, 10).equals("1")) {
                z2 = true;
            } else {
                z2 = false;
            }
            alarmBean.setOpen(z2);
            alarmBean = this.mBean1;
            if (alarmSt.substring(30, 31).equals("1")) {
                z2 = true;
            } else {
                z2 = false;
            }
            alarmBean.setOpen(z2);
            AlarmBean alarmBean2 = this.mBean2;
            if (!alarmSt.substring(51, 52).equals("1")) {
                z = false;
            }
            alarmBean2.setOpen(z);
            this.mBean0.setOpenType(Integer.valueOf(alarmSt.substring(11, 12)).intValue());
            this.mBean1.setOpenType(Integer.valueOf(alarmSt.substring(32, 33)).intValue());
            this.mBean2.setOpenType(Integer.valueOf(alarmSt.substring(53, 54)).intValue());
            this.mBean0.setCustomData(Integer.valueOf(alarmSt.substring(13, 20)).intValue());
            this.mBean1.setCustomData(Integer.valueOf(alarmSt.substring(34, 41)).intValue());
            this.mBean2.setCustomData(Integer.valueOf(alarmSt.substring(55, 62)).intValue());
        }
    }

    private void initReceiver() {
        IntentFilter filter = new IntentFilter();
        filter.addAction(ACTION_ALARM_VIEW_CHANGE);
        registerReceiver(this.mReceiver, filter);
    }

    private void setView() {
        this.tv_AlarmTime0.setText(this.intFormat.format((long) this.mBean0.getHour()) + ":" + this.intFormat.format((long) this.mBean0.getMin()));
        this.tv_AlarmTime1.setText(this.intFormat.format((long) this.mBean1.getHour()) + ":" + this.intFormat.format((long) this.mBean1.getMin()));
        this.tv_AlarmTime2.setText(this.intFormat.format((long) this.mBean2.getHour()) + ":" + this.intFormat.format((long) this.mBean2.getMin()));
        this.tv_AlarmDate0.setText(getText(this.mBean0));
        this.tv_AlarmDate1.setText(getText(this.mBean1));
        this.tv_AlarmDate2.setText(getText(this.mBean2));
        if (this.mBean0.isBrain()) {
            this.tv_AlarmNofy0.setVisibility(0);
        } else {
            this.tv_AlarmNofy0.setVisibility(4);
        }
        if (this.mBean1.isBrain()) {
            this.tv_AlarmNofy1.setVisibility(0);
        } else {
            this.tv_AlarmNofy1.setVisibility(4);
        }
        if (this.mBean2.isBrain()) {
            this.tv_AlarmNofy2.setVisibility(0);
        } else {
            this.tv_AlarmNofy2.setVisibility(4);
        }
        if (this.mBean0.isOpen()) {
            this.bt_AlarmOff0.setBackgroundResource(R.drawable.alarm_button_openon);
        } else {
            this.bt_AlarmOff0.setBackgroundResource(R.drawable.alarm_button_closeoff);
        }
        if (this.mBean1.isOpen()) {
            this.bt_AlarmOff1.setBackgroundResource(R.drawable.alarm_button_openon);
        } else {
            this.bt_AlarmOff1.setBackgroundResource(R.drawable.alarm_button_closeoff);
        }
        if (this.mBean2.isOpen()) {
            this.bt_AlarmOff2.setBackgroundResource(R.drawable.alarm_button_openon);
        } else {
            this.bt_AlarmOff2.setBackgroundResource(R.drawable.alarm_button_closeoff);
        }
    }

    private int getText(AlarmBean bean) {
        int type = bean.getOpenType();
        if (type == 0) {
            return R.string.alarm_once;
        }
        if (type == 1) {
            return R.string.alarm_everyday;
        }
        if (type == 2) {
            return R.string.alarm_workdays;
        }
        return R.string.alarm_customs;
    }

    public void changeTimeSet(int hourOfDay, int minute, int id) {
        this.mCalender = Calendar.getInstance();
        this.mCalender.setTimeInMillis(System.currentTimeMillis());
        int hour_now = this.mCalender.get(11);
        int minute_now = this.mCalender.get(12);
        if (hour_now > hourOfDay || (hour_now == hourOfDay && minute_now >= minute)) {
            this.mCalender.setTimeInMillis(System.currentTimeMillis() + 86400000);
        }
        this.mCalender.set(11, hourOfDay);
        this.mCalender.set(12, minute);
        Intent intent = new Intent(this, PlugSendMessage.class);
        intent.setAction(PlugSendMessage.ACTION_CLOCK_TIME_UP);
        intent.putExtra("id", id);
        ((AlarmManager) getSystemService("alarm")).set(0, this.mCalender.getTimeInMillis(), PendingIntent.getBroadcast(getApplicationContext(), 0, intent, 0));
    }

    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        unregisterReceiver(this.mReceiver);
        super.onDestroy();
    }

    public void onClick(View view) {
        boolean z = true;
        boolean z2 = false;
        AlarmBean alarmBean;
        switch (view.getId()) {
            case R.id.back:
                finishActivity();
                break;
            case R.id.layout_alarm_0:
                Intent intent0 = new Intent(this, AlarmSetActivity.class);
                intent0.putExtra("alarmbean", this.mBean0);
                startActivityForResult(intent0, 65536);
                break;
            case R.id.button_alarm0_off:
                alarmBean = this.mBean0;
                if (this.mBean0.isOpen()) {
                    z = false;
                }
                alarmBean.setOpen(z);
                broadAlarmInfo(this.mBean0.toString());
                if (this.mBean0.isOpen() && this.mBean0.getOpenType() == 0) {
                    changeTimeSet(this.mBean0.getHour(), this.mBean0.getMin(), 0);
                    break;
                }
            case R.id.layout_alarm_1:
                Intent intent1 = new Intent(this, AlarmSetActivity.class);
                intent1.putExtra("alarmbean", this.mBean1);
                startActivityForResult(intent1, 65536);
                break;
            case R.id.button_alarm1_off:
                alarmBean = this.mBean1;
                if (!this.mBean1.isOpen()) {
                    z2 = true;
                }
                alarmBean.setOpen(z2);
                broadAlarmInfo(this.mBean1.toString());
                if (this.mBean1.isOpen() && this.mBean1.getOpenType() == 0) {
                    changeTimeSet(this.mBean1.getHour(), this.mBean1.getMin(), 1);
                    break;
                }
            case R.id.layout_alarm_2:
                Intent intent2 = new Intent(this, AlarmSetActivity.class);
                intent2.putExtra("alarmbean", this.mBean2);
                startActivityForResult(intent2, 65536);
                break;
            case R.id.button_alarm2_off:
                alarmBean = this.mBean2;
                if (this.mBean2.isOpen()) {
                    z = false;
                }
                alarmBean.setOpen(z);
                broadAlarmInfo(this.mBean2.toString());
                if (this.mBean2.isOpen() && this.mBean2.getOpenType() == 0) {
                    changeTimeSet(this.mBean2.getHour(), this.mBean2.getMin(), 2);
                    break;
                }
        }
        setView();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        getClass();
        if (requestCode == 65536) {
            AlarmBean mBean = (AlarmBean) data.getSerializableExtra("alarmReturn");
            Log.i("chenxin", "alarm before:" + this.mBean0.saveShareP() + this.mBean1.saveShareP() + this.mBean2.saveShareP());
            int id = mBean.getId();
            if (id == 0) {
                this.mBean0 = mBean;
                Tools.saveUpdateAlarmBrain(this.mBean0.saveShareP(), 0);
            } else if (id == 1) {
                this.mBean1 = mBean;
                Tools.saveUpdateAlarmBrain(this.mBean1.saveShareP(), 1);
            } else if (id == 2) {
                this.mBean2 = mBean;
                Tools.saveUpdateAlarmBrain(this.mBean2.saveShareP(), 2);
            }
            Tools.saveAlarmBrain(this.mBean0.saveShareP() + this.mBean1.saveShareP() + this.mBean2.saveShareP());
            Log.i("chenxin", "alarm after:" + this.mBean0.saveShareP() + this.mBean1.saveShareP() + this.mBean2.saveShareP());
            if (mBean.isOpen() && mBean.getOpenType() == 0) {
                changeTimeSet(mBean.getHour(), mBean.getMin(), id);
            }
            setView();
            broadAlarmInfo(mBean.toString());
        }
    }

    private void broadAlarmInfo(String info) {
        Intent bluetoothBroadIntent = new Intent(BleManagerService.ACTION_UPDATE_ALARM_INFO);
        bluetoothBroadIntent.putExtra("alarmInfo", info);
        sendBroadcast(bluetoothBroadIntent);
        Intent intent = new Intent("com.tyd.plugin.receiver.sendmsg");
        intent.putExtra("plugin_cmd", 149);
        intent.putExtra("plugin_content", info);
        sendBroadcast(intent);
    }

    public void onBackPressed() {
        finishActivity();
    }

    private void finishActivity() {
        Tools.saveAlarmBrain(this.mBean0.saveShareP() + this.mBean1.saveShareP() + this.mBean2.saveShareP());
        Tools.saveUpdateAlarmBrain(this.mBean0.saveShareP(), 0);
        Tools.saveUpdateAlarmBrain(this.mBean1.saveShareP(), 1);
        Tools.saveUpdateAlarmBrain(this.mBean2.saveShareP(), 2);
        finish();
    }
}
