package com.zhuoyou.plugin.running;

import android.content.Context;
import android.text.format.DateFormat;
import android.util.Log;
import java.util.Calendar;

public class WaterIntakeUtils {
    public static Long[] warnTime(Context context, Long[] times, Long current) {
        for (int i = 0; i < times.length; i++) {
            if (getTimeString(context, times[i].longValue()).compareTo(getTimeString(context, current.longValue())) <= 0) {
                Log.i("zhaojunhui", "before change time is =" + getTimeString(context, times[i].longValue()));
                Calendar ca = Calendar.getInstance();
                ca.setTimeInMillis(times[i].longValue());
                Log.i("zhaojunhui", "before change is =" + ca.getTimeInMillis() + "and Time is" + 5);
                ca.add(5, 1);
                times[i] = Long.valueOf(ca.getTimeInMillis());
                Log.i("zhaojunhui", "之前");
                Log.i("zhaojunhui", "after change is =" + ca.getTimeInMillis() + "and i is" + i);
            } else {
                Log.i("zhaojunhui", "之后——大于等于0");
            }
            Log.i("zhaojunhui", "almert time is =" + getTimeString(context, times[i].longValue()));
            Log.i("zhaojunhui", "current time is =" + getTimeString(context, current.longValue()));
        }
        return times;
    }

    public static boolean isInWarnTime(Context context, long paramLong) {
        String str1 = getTimeString(context, paramLong);
        int n = 1;
        if (8 >= 22 && 0 >= 0) {
            n = 0;
        }
        String str2 = "08:00";
        String str3 = "22:00";
        Log.i(" liuzhiying  136  ", "n = " + n + " i = " + 8 + " j = " + 0);
        if (n != 0) {
            if (str1.compareTo(str2) < 0 || str1.compareTo(str3) > 0) {
                Log.i(" liuzhiying  143 ", str1);
            } else {
                Log.i(" liuzhiying  140 ", str1);
                return true;
            }
        }
        Log.i(" liuzhiying  145  ", str1);
        return false;
    }

    public static String getTimeString(Context paramContext, long paramLong) {
        String str = "";
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTimeInMillis(paramLong);
        int i;
        if (DateFormat.is24HourFormat(paramContext)) {
            i = mCalendar.get(11);
            if (i < 10) {
                str = str + "0";
            }
            str = str + i + ":";
        } else {
            i = mCalendar.get(10);
            if (mCalendar.get(9) != 0) {
                i += 12;
            } else if (i < 10) {
                str = str + "0";
            }
            str = str + i + ":";
        }
        int u = mCalendar.get(12);
        if (u < 10) {
            str = str + "0";
        }
        return str + u;
    }

    public static boolean isWarnTime(Context context, long paramLong) {
        String str1 = getTimeString(context, paramLong);
        int n = 1;
        if (8 >= 22 && 0 >= 0) {
            n = 0;
        }
        String str2 = "08:00";
        String str3 = "22:00";
        Log.i(" liuzhiying  136  ", "n = " + n + " i = " + 8 + " j = " + 0);
        if (n != 0) {
            if (str1.compareTo(str3) < 0) {
                Log.i(" liuzhiying  140 ", str1);
                return true;
            }
            Log.i(" liuzhiying  143 ", str1);
        }
        Log.i(" liuzhiying  145  ", str1);
        return false;
    }
}
