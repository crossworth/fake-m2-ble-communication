package com.zhuoyou.plugin.cloud;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.text.format.Time;
import com.zhuoyou.plugin.running.Tools;

public class AlarmUtils {
    private static int Time_Interval_Change = 120;

    public static void setAutoSyncAlarm(Context context) {
        long millis = Tools.getAutoSyncTime(context);
        Time time;
        if (millis <= 0) {
            time = new Time();
            time.setToNow();
            millis = getNextAutoSyncTime(time);
        } else {
            time = new Time();
            time.set(millis);
            millis = getNextAutoSyncTime(time);
            if (millis < System.currentTimeMillis()) {
                time.setToNow();
                millis = getNextAutoSyncTime(time);
            }
        }
        cancelAutoSyncAlarm(context);
        ((AlarmManager) context.getSystemService("alarm")).set(0, millis, PendingIntent.getBroadcast(context, 0, new Intent("com.zhuoyou.running.autosync.alarm"), 268435456));
    }

    public static void cancelAutoSyncAlarm(Context context) {
        ((AlarmManager) context.getSystemService("alarm")).cancel(PendingIntent.getBroadcast(context, 0, new Intent("com.zhuoyou.running.autosync.alarm"), 268435456));
    }

    public static long getNextAutoSyncTime(Time time) {
        time.minute += Time_Interval_Change;
        if (time.minute >= 60) {
            time.hour += time.minute / 60;
            time.minute %= 60;
        }
        if (time.hour >= 24) {
            time.monthDay += time.hour / 24;
            time.hour %= 24;
        }
        int daysofmonth = getDaysofMonth(time.year, time.month);
        if (time.monthDay > daysofmonth) {
            time.monthDay -= daysofmonth;
            time.month++;
            if (time.month >= 12) {
                time.year++;
                time.month %= 12;
            }
        }
        return time.toMillis(true);
    }

    public static int getDaysofMonth(int year, int month) {
        switch (month + 1) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 2:
                if (year / 4 == 0 || (year / 100 == 0 && year / 400 != 0)) {
                    return 29;
                }
                return 28;
            default:
                return 30;
        }
    }
}
