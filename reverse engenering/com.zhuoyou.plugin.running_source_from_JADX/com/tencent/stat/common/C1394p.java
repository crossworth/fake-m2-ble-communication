package com.tencent.stat.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class C1394p {
    private static SharedPreferences f4450a = null;

    public static int m4166a(Context context, String str, int i) {
        return C1394p.m4168a(context).getInt(C1389k.m4127b(context, "" + str), i);
    }

    public static long m4167a(Context context, String str, long j) {
        return C1394p.m4168a(context).getLong(C1389k.m4127b(context, "" + str), j);
    }

    static synchronized SharedPreferences m4168a(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (C1394p.class) {
            if (f4450a == null) {
                f4450a = PreferenceManager.getDefaultSharedPreferences(context);
            }
            sharedPreferences = f4450a;
        }
        return sharedPreferences;
    }

    public static String m4169a(Context context, String str, String str2) {
        return C1394p.m4168a(context).getString(C1389k.m4127b(context, "" + str), str2);
    }

    public static void m4170b(Context context, String str, int i) {
        String b = C1389k.m4127b(context, "" + str);
        Editor edit = C1394p.m4168a(context).edit();
        edit.putInt(b, i);
        edit.commit();
    }

    public static void m4171b(Context context, String str, long j) {
        String b = C1389k.m4127b(context, "" + str);
        Editor edit = C1394p.m4168a(context).edit();
        edit.putLong(b, j);
        edit.commit();
    }

    public static void m4172b(Context context, String str, String str2) {
        String b = C1389k.m4127b(context, "" + str);
        Editor edit = C1394p.m4168a(context).edit();
        edit.putString(b, str2);
        edit.commit();
    }
}
