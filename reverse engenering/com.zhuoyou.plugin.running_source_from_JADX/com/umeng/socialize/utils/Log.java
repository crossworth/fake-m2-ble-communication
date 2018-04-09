package com.umeng.socialize.utils;

public class Log {
    public static final boolean ENCRYPT_LOG = true;
    public static boolean LOG = true;
    public static final String TAG = "umengsocial";

    public static void m4552i(String str, String str2) {
        if (LOG) {
            android.util.Log.i(str, str2);
        }
    }

    public static void m4553i(String str, String str2, Exception exception) {
        if (LOG) {
            android.util.Log.i(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m4549e(String str, String str2) {
        if (LOG) {
            android.util.Log.e(str, str2);
        }
    }

    public static void m4550e(String str, String str2, Exception exception) {
        android.util.Log.e(str, exception.toString() + ":  [" + str2 + "]");
        for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
            android.util.Log.e(str, "        at\t " + stackTraceElement.toString());
        }
    }

    public static void m4546d(String str, String str2) {
        if (LOG) {
            android.util.Log.d(str, str2);
        }
    }

    public static void m4547d(String str, String str2, Exception exception) {
        if (LOG) {
            android.util.Log.d(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m4555v(String str, String str2) {
        if (LOG) {
            android.util.Log.v(str, str2);
        }
    }

    public static void m4556v(String str, String str2, Exception exception) {
        if (LOG) {
            android.util.Log.v(str, exception.toString() + ":  [" + str2 + "]");
        }
    }

    public static void m4558w(String str, String str2) {
        if (LOG) {
            android.util.Log.w(str, str2);
        }
    }

    public static void m4559w(String str, String str2, Exception exception) {
        if (LOG) {
            android.util.Log.w(str, exception.toString() + ":  [" + str2 + "]");
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                android.util.Log.w(str, "        at\t " + stackTraceElement.toString());
            }
        }
    }

    public static void m4551i(String str) {
        if (LOG) {
            android.util.Log.i(TAG, str);
        }
    }

    public static void m4548e(String str) {
        if (LOG) {
            android.util.Log.e(TAG, str);
        }
    }

    public static void m4545d(String str) {
        if (LOG) {
            android.util.Log.d(TAG, str);
        }
    }

    public static void m4554v(String str) {
        if (LOG) {
            android.util.Log.v(TAG, str);
        }
    }

    public static void m4557w(String str) {
        if (LOG) {
            android.util.Log.w(TAG, str);
        }
    }
}
