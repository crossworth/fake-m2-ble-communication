package com.umeng.analytics;

import android.content.Context;
import android.text.TextUtils;
import p031u.aly.bj;
import p031u.aly.bl;

public class AnalyticsConfig {
    public static boolean ACTIVITY_DURATION_OPEN = true;
    public static boolean CATCH_EXCEPTION = true;
    public static boolean COMPRESS_DATA = true;
    public static boolean ENABLE_MEMORY_BUFFER = true;
    public static final boolean FLAG_INTERNATIONAL = false;
    public static String GPU_RENDERER = "";
    public static String GPU_VENDER = "";
    static double[] f3085a = null;
    private static String f3086b = null;
    private static String f3087c = null;
    private static String f3088d = null;
    private static int f3089e = 0;
    public static long kContinueSessionMillis = 30000;
    public static String mWrapperType = null;
    public static String mWrapperVersion = null;
    public static boolean sEncrypt;
    public static int sLatentWindow;

    static {
        sEncrypt = false;
        sEncrypt = false;
    }

    static void m3055a(boolean z) {
        sEncrypt = z;
    }

    static void m3053a(Context context, String str) {
        if (context == null) {
            f3086b = str;
            return;
        }
        String s = bj.m3545s(context);
        if (TextUtils.isEmpty(s)) {
            Object c = C0934h.m3100a(context).m3119c();
            if (TextUtils.isEmpty(c)) {
                C0934h.m3100a(context).m3111a(str);
            } else if (!c.equals(str)) {
                bl.m3588d("Appkey和上次配置的不一致 ");
                C0934h.m3100a(context).m3111a(str);
            }
            f3086b = str;
            return;
        }
        f3086b = s;
        if (!s.equals(str)) {
            bl.m3588d("Appkey和AndroidManifest.xml中配置的不一致 ");
        }
    }

    static void m3054a(String str) {
        f3087c = str;
    }

    public static String getAppkey(Context context) {
        if (TextUtils.isEmpty(f3086b)) {
            f3086b = bj.m3545s(context);
            if (TextUtils.isEmpty(f3086b)) {
                f3086b = C0934h.m3100a(context).m3119c();
            }
        }
        return f3086b;
    }

    public static String getChannel(Context context) {
        if (TextUtils.isEmpty(f3087c)) {
            f3087c = bj.m3551y(context);
        }
        return f3087c;
    }

    public static double[] getLocation() {
        return f3085a;
    }

    static void m3056b(Context context, String str) {
        if (!TextUtils.isEmpty(str)) {
            f3088d = str;
            C0934h.m3100a(context).m3120c(f3088d);
        }
    }

    public static String getSecretKey(Context context) {
        if (TextUtils.isEmpty(f3088d)) {
            f3088d = C0934h.m3100a(context).m3122e();
        }
        return f3088d;
    }

    static void m3052a(Context context, int i) {
        f3089e = i;
        C0934h.m3100a(context).m3110a(f3089e);
    }

    public static int getVerticalType(Context context) {
        if (f3089e == 0) {
            f3089e = C0934h.m3100a(context).m3123f();
        }
        return f3089e;
    }

    public static String getSDKVersion(Context context) {
        return C0919a.f3106c;
    }
}
