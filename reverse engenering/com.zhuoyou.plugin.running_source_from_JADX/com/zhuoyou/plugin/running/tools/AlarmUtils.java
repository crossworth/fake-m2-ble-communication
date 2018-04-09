package com.zhuoyou.plugin.running.tools;

import android.content.res.Resources;
import android.util.Log;
import com.zhuoyou.plugin.running.C1680R;
import com.zhuoyou.plugin.running.app.TheApp;
import com.zhuoyou.plugin.running.bean.AlarmData;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AlarmUtils {
    private static final String TAG = "AlarmUtils";
    public static SimpleDateFormat dateFormat = new SimpleDateFormat(Tools.DEFAULT_FORMAT_DATE, Locale.CHINA);

    public static AlarmData dealAlarmString(String alarmStr) {
        boolean z = true;
        String[] alarmSlip = alarmStr.split("\\|");
        Log.i(TAG, "dealAlarmString:" + alarmStr + " size:" + alarmSlip.length + " open:" + (Integer.parseInt(alarmSlip[1]) % 10));
        AlarmData data = new AlarmData();
        data.setHour(Integer.parseInt(alarmSlip[0]) / 100);
        data.setMin(Integer.parseInt(alarmSlip[0]) % 100);
        data.setId(Integer.parseInt(alarmSlip[1]) / 100);
        data.setBrain((Integer.parseInt(alarmSlip[1]) % 100) / 10 == 1);
        if (Integer.parseInt(alarmSlip[1]) % 10 != 1) {
            z = false;
        }
        data.setOpen(z);
        data.setOpenType(Integer.parseInt(alarmSlip[2]));
        data.setCustomData(Integer.parseInt(alarmSlip[3]));
        data.setOpenDate(alarmSlip[4]);
        if (data.getOpenType() == AlarmData.OPEN_TYPE_ONCE && !data.getOpenDate().equals(dateFormat.format(Calendar.getInstance().getTime()))) {
            Log.i(TAG, "alarm save open false");
            data.setOpen(false);
        }
        return data;
    }

    public static String getDurationString(int duration) {
        Resources res = TheApp.getContext().getResources();
        if (duration == 11111) {
            return res.getString(C1680R.string.alarm_workday);
        }
        if (duration == 1100000) {
            return res.getString(C1680R.string.alarm_weekends);
        }
        if (duration == 1111111) {
            return res.getString(C1680R.string.alarm_everyday);
        }
        if (duration == 0) {
            return res.getString(C1680R.string.repeat_only_once);
        }
        String durationStr = new String();
        String[] weedStr = res.getStringArray(C1680R.array.week_str);
        for (int i = 0; i < 7; i++) {
            durationStr = durationStr + (duration % 10 == 1 ? weedStr[i] + " " : "");
            duration /= 10;
        }
        return durationStr;
    }
}
