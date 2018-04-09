package com.tencent.wxop.stat.p023b;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public final class C0890q {
    private static SharedPreferences db = null;

    private static synchronized SharedPreferences m2906S(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (C0890q.class) {
            sharedPreferences = context.getSharedPreferences(".mta-wxop", 0);
            db = sharedPreferences;
            if (sharedPreferences == null) {
                db = PreferenceManager.getDefaultSharedPreferences(context);
            }
            sharedPreferences = db;
        }
        return sharedPreferences;
    }

    public static int m2907a(Context context, String str, int i) {
        return C0890q.m2906S(context).getInt(C0885l.m2893e(context, "wxop_" + str), i);
    }

    public static void m2908a(Context context, String str, long j) {
        String e = C0885l.m2893e(context, "wxop_" + str);
        Editor edit = C0890q.m2906S(context).edit();
        edit.putLong(e, j);
        edit.commit();
    }

    public static String m2909b(Context context, String str, String str2) {
        return C0890q.m2906S(context).getString(C0885l.m2893e(context, "wxop_" + str), str2);
    }

    public static void m2910b(Context context, String str, int i) {
        String e = C0885l.m2893e(context, "wxop_" + str);
        Editor edit = C0890q.m2906S(context).edit();
        edit.putInt(e, i);
        edit.commit();
    }

    public static void m2911c(Context context, String str, String str2) {
        String e = C0885l.m2893e(context, "wxop_" + str);
        Editor edit = C0890q.m2906S(context).edit();
        edit.putString(e, str2);
        edit.commit();
    }

    public static long m2912f(Context context, String str) {
        return C0890q.m2906S(context).getLong(C0885l.m2893e(context, "wxop_" + str), 0);
    }
}
