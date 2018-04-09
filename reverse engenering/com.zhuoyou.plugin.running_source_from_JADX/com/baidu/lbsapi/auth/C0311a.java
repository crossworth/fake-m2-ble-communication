package com.baidu.lbsapi.auth;

import android.util.Log;

class C0311a {
    public static boolean f70a = false;
    private static String f71b = "BaiduApiAuth";

    public static String m121a() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        return stackTraceElement.getFileName() + "[" + stackTraceElement.getLineNumber() + "]";
    }

    public static void m122a(String str) {
        if (f70a && Thread.currentThread().getStackTrace().length != 0) {
            Log.d(f71b, C0311a.m121a() + ";" + str);
        }
    }

    public static void m123b(String str) {
        if (f70a && Thread.currentThread().getStackTrace().length != 0) {
            Log.e(f71b, C0311a.m121a() + ";" + str);
        }
    }
}
