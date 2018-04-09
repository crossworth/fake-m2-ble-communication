package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;

/* compiled from: InternalConfig */
public class C0922e {
    private static String[] f3131a = new String[2];

    public static void m3072a(Context context, String str, String str2) {
        f3131a[0] = str;
        f3131a[1] = str2;
        if (context != null) {
            C0934h.m3100a(context).m3112a(str, str2);
        }
    }

    public static String[] m3073a(Context context) {
        if (!TextUtils.isEmpty(f3131a[0]) && !TextUtils.isEmpty(f3131a[1])) {
            return f3131a;
        }
        if (context != null) {
            String[] a = C0934h.m3100a(context).m3115a();
            if (a != null) {
                f3131a[0] = a[0];
                f3131a[1] = a[1];
                return f3131a;
            }
        }
        return null;
    }

    public static void m3074b(Context context) {
        f3131a[0] = null;
        f3131a[1] = null;
        if (context != null) {
            C0934h.m3100a(context).m3116b();
        }
    }
}
