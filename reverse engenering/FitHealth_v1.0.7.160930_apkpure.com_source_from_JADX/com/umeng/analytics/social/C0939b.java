package com.umeng.analytics.social;

import android.util.Log;

/* compiled from: UMLog */
public class C0939b {
    public static void m3137a(String str, String str2) {
        if (C0942e.f3211v) {
            Log.i(str, str2);
        }
    }

    public static void m3138a(String str, String str2, Exception exception) {
        if (C0942e.f3211v) {
            Log.i(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m3139b(String str, String str2) {
        if (C0942e.f3211v) {
            Log.e(str, str2);
        }
    }

    public static void m3140b(String str, String str2, Exception exception) {
        if (C0942e.f3211v) {
            Log.e(str, exception.toString() + ":  [" + str2 + "]");
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                Log.e(str, "        at\t " + stackTraceElement.toString());
            }
        }
    }

    public static void m3141c(String str, String str2) {
        if (C0942e.f3211v) {
            Log.d(str, str2);
        }
    }

    public static void m3142c(String str, String str2, Exception exception) {
        if (C0942e.f3211v) {
            Log.d(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m3143d(String str, String str2) {
        if (C0942e.f3211v) {
            Log.v(str, str2);
        }
    }

    public static void m3144d(String str, String str2, Exception exception) {
        if (C0942e.f3211v) {
            Log.v(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m3145e(String str, String str2) {
        if (C0942e.f3211v) {
            Log.w(str, str2);
        }
    }

    public static void m3146e(String str, String str2, Exception exception) {
        if (C0942e.f3211v) {
            Log.w(str, exception.toString() + ":  [" + str2 + "]");
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                Log.w(str, "        at\t " + stackTraceElement.toString());
            }
        }
    }
}
