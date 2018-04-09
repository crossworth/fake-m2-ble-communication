package com.umeng.socialize.utils;

public class Log {
    public static final boolean ENCRYPT_LOG = true;
    public static boolean LOG = true;
    public static final String TAG = "umengsocial";

    public static void m3254i(String str, String str2) {
        if (LOG) {
            android.util.Log.i(str, str2);
        }
    }

    public static void m3255i(String str, String str2, Exception exception) {
        if (LOG) {
            android.util.Log.i(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m3251e(String str, String str2) {
        if (LOG) {
            android.util.Log.e(str, str2);
        }
    }

    public static void m3252e(String str, String str2, Exception exception) {
        android.util.Log.e(str, exception.toString() + ":  [" + str2 + "]");
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            android.util.Log.e(str, "        at\t " + stackTraceElement.toString());
        }
    }

    public static void m3248d(String str, String str2) {
        if (LOG) {
            android.util.Log.d(str, str2);
        }
    }

    public static void m3249d(String str, String str2, Exception exception) {
        if (LOG) {
            android.util.Log.d(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m3257v(String str, String str2) {
        if (LOG) {
            android.util.Log.v(str, str2);
        }
    }

    public static void m3258v(String str, String str2, Exception exception) {
        if (LOG) {
            android.util.Log.v(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m3260w(String str, String str2) {
        if (LOG) {
            android.util.Log.w(str, str2);
        }
    }

    public static void m3261w(String str, String str2, Exception exception) {
        if (LOG) {
            android.util.Log.w(str, exception.toString() + ":  [" + str2 + "]");
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                android.util.Log.w(str, "        at\t " + stackTraceElement.toString());
            }
        }
    }

    public static void m3253i(String str) {
        if (LOG) {
            android.util.Log.i(TAG, str);
        }
    }

    public static void m3250e(String str) {
        if (LOG) {
            android.util.Log.e(TAG, str);
        }
    }

    public static void m3247d(String str) {
        if (LOG) {
            android.util.Log.d(TAG, str);
        }
    }

    public static void m3256v(String str) {
        if (LOG) {
            android.util.Log.v(TAG, str);
        }
    }

    public static void m3259w(String str) {
        if (LOG) {
            android.util.Log.w(TAG, str);
        }
    }
}
