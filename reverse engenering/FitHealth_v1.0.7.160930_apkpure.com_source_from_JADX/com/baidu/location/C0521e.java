package com.baidu.location;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import java.util.Calendar;

public class C0521e {
    public static void m2185a(Context context, PendingIntent pendingIntent) {
        ((AlarmManager) context.getSystemService("alarm")).cancel(pendingIntent);
    }

    public static void m2186a(Context context, PendingIntent pendingIntent, int i) {
        AlarmManager alarmManager = (AlarmManager) context.getSystemService("alarm");
        alarmManager.cancel(pendingIntent);
        Calendar instance = Calendar.getInstance();
        instance.setTimeInMillis(System.currentTimeMillis());
        instance.add(14, i);
        alarmManager.set(0, instance.getTimeInMillis(), pendingIntent);
    }
}
