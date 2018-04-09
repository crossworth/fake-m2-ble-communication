package com.zhuoyou.plugin.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import com.zhuoyou.plugin.bluetooth.connection.CustomCmd;
import com.zhuoyou.plugin.bluetooth.service.BluetoothService;
import com.zhuoyou.plugin.component.AlarmBean;
import com.zhuoyou.plugin.component.AlarmMainActivity;
import com.zhuoyou.plugin.running.Tools;

public class PlugSendMessage extends BroadcastReceiver {
    public static final String ACTION_CLOCK_TIME_UP = "com.tyd.plugin.receiver.clock.time.up";
    private static final String ACTION_SEND_MSG = "com.tyd.plugin.receiver.sendmsg";
    private AlarmBean mBean0;
    private AlarmBean mBean1;
    private AlarmBean mBean2;

    public void onReceive(Context arg0, Intent arg1) {
        String action = arg1.getAction();
        Log.i("gchk", "receiver msg action = " + action);
        if (action.equals(ACTION_SEND_MSG)) {
            int cmd = arg1.getIntExtra("plugin_cmd", 0);
            String s = arg1.getStringExtra("plugin_content");
            char[] c_tag = arg1.getCharArrayExtra("plugin_tag");
            Log.i("gchk", "receiver msg cmd = " + cmd);
            Log.i("gchk", "receiver msg s = " + s);
            if (BluetoothService.getInstance() != null && cmd != 0) {
                if (c_tag != null) {
                    Log.i("gchk", "receiver msg tag =_" + c_tag[0] + "_" + c_tag[1] + "_" + c_tag[2] + "_" + c_tag[3]);
                    CustomCmd.sendCustomCmd(cmd, s, c_tag);
                    return;
                }
                CustomCmd.sendCustomCmd(cmd, s);
            }
        } else if (action.equals(ACTION_CLOCK_TIME_UP)) {
            int id = arg1.getIntExtra("id", -1);
            getData();
            if (id == 0) {
                this.mBean0.setOpen(false);
            } else if (id == 1) {
                this.mBean1.setOpen(false);
            } else if (id == 2) {
                this.mBean2.setOpen(false);
            }
            Tools.saveAlarmBrain(this.mBean0.saveShareP() + this.mBean1.saveShareP() + this.mBean2.saveShareP());
            Intent mIntent = new Intent();
            mIntent.setAction(AlarmMainActivity.ACTION_ALARM_VIEW_CHANGE);
            arg0.sendBroadcast(mIntent);
        }
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
}
