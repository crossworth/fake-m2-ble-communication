package com.tencent.wxop.stat.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class C1447p {
    private static SharedPreferences f4811a = null;

    public static int m4454a(Context context, String str, int i) {
        return C1447p.m4456a(context).getInt(C1442k.m4409a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString()), i);
    }

    public static long m4455a(Context context, String str, long j) {
        return C1447p.m4456a(context).getLong(C1442k.m4409a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString()), j);
    }

    static synchronized SharedPreferences m4456a(Context context) {
        SharedPreferences sharedPreferences;
        synchronized (C1447p.class) {
            sharedPreferences = context.getSharedPreferences(".mta-wxop", 0);
            f4811a = sharedPreferences;
            if (sharedPreferences == null) {
                f4811a = PreferenceManager.getDefaultSharedPreferences(context);
            }
            sharedPreferences = f4811a;
        }
        return sharedPreferences;
    }

    public static String m4457a(Context context, String str, String str2) {
        return C1447p.m4456a(context).getString(C1442k.m4409a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString()), str2);
    }

    public static void m4458b(Context context, String str, int i) {
        String a = C1442k.m4409a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString());
        Editor edit = C1447p.m4456a(context).edit();
        edit.putInt(a, i);
        edit.commit();
    }

    public static void m4459b(Context context, String str, long j) {
        String a = C1442k.m4409a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString());
        Editor edit = C1447p.m4456a(context).edit();
        edit.putLong(a, j);
        edit.commit();
    }

    public static void m4460b(Context context, String str, String str2) {
        String a = C1442k.m4409a(context, new StringBuilder(StatConstants.MTA_COOPERATION_TAG).append(str).toString());
        Editor edit = C1447p.m4456a(context).edit();
        edit.putString(a, str2);
        edit.commit();
    }
}
