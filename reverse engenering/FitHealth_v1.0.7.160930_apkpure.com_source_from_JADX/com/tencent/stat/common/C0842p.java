package com.tencent.stat.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class C0842p {
    private static SharedPreferences f2915a = null;

    public static int m2759a(Context context, String str, int i) {
        return C0842p.m2761a(context).getInt(C0837k.m2720b(context, "" + str), i);
    }

    public static long m2760a(Context context, String str, long j) {
        return C0842p.m2761a(context).getLong(C0837k.m2720b(context, "" + str), j);
    }

    static synchronized SharedPreferences m2761a(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (C0842p.class) {
            if (f2915a == null) {
                f2915a = PreferenceManager.getDefaultSharedPreferences(context);
            }
            sharedPreferences = f2915a;
        }
        return sharedPreferences;
    }

    public static String m2762a(Context context, String str, String str2) {
        return C0842p.m2761a(context).getString(C0837k.m2720b(context, "" + str), str2);
    }

    public static void m2763b(Context context, String str, int i) {
        String b = C0837k.m2720b(context, "" + str);
        Editor edit = C0842p.m2761a(context).edit();
        edit.putInt(b, i);
        edit.commit();
    }

    public static void m2764b(Context context, String str, long j) {
        String b = C0837k.m2720b(context, "" + str);
        Editor edit = C0842p.m2761a(context).edit();
        edit.putLong(b, j);
        edit.commit();
    }

    public static void m2765b(Context context, String str, String str2) {
        String b = C0837k.m2720b(context, "" + str);
        Editor edit = C0842p.m2761a(context).edit();
        edit.putString(b, str2);
        edit.commit();
    }
}
