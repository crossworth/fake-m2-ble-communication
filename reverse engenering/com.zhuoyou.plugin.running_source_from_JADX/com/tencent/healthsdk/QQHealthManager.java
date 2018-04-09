package com.tencent.healthsdk;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import java.lang.ref.SoftReference;

public class QQHealthManager {
    public static final int RET_HEALTH_DATA_IS_NULL = -20003;
    public static final int RET_HEALTH_DATA_IS_WRONG = -20004;
    public static final int RET_NETWORK_ERROR = -20002;
    public static final int RET_UNKNOWN_ERROR = -20001;
    static final String f3692a = "QQHealthManager";
    private static QQHealthManager f3693c = null;
    public static boolean debug = false;
    SoftReference f3694b;

    private QQHealthManager() {
    }

    public static QQHealthManager getInstance() {
        if (f3693c == null) {
            f3693c = new QQHealthManager();
        }
        return f3693c;
    }

    public void addHealthCallback(Context context, QQHealthCallback callback) {
        if (callback == null) {
            throw new RuntimeException("QQHealthCallback is null!");
        }
        Object obj = this.f3694b == null ? 1 : null;
        this.f3694b = new SoftReference(callback);
        if (obj != null) {
            m3475a(context);
        }
    }

    public void exitAndRemoveCallback(Context context) {
        this.f3694b = null;
        m3476b(context);
    }

    public void uploadHealthData(Context context, QQHealthCallback callback) {
        if (context == null || callback == null) {
            throw new RuntimeException("context or callback is null!");
        }
        C1188a.m3480a(context, callback);
    }

    void m3475a(Context context) {
        if (context != null) {
            ((AlarmManager) context.getSystemService("alarm")).setInexactRepeating(1, System.currentTimeMillis() + 60000, 3600000, PendingIntent.getBroadcast(context, 0, new Intent(context, QQHealthAlarmReceiver.class), 0));
        }
    }

    void m3476b(Context context) {
        if (context != null) {
            ((AlarmManager) context.getSystemService("alarm")).cancel(PendingIntent.getBroadcast(context, 0, new Intent(context, QQHealthAlarmReceiver.class), 0));
        }
    }

    static void m3472a(String str, String str2) {
        if (debug) {
            Log.i(str, str2);
        }
    }

    static void m3474b(String str, String str2) {
        if (debug) {
            Log.e(str, str2);
        }
    }

    static void m3473a(String str, String str2, Throwable th) {
        if (debug) {
            Log.e(str, str2, th);
        }
    }
}
