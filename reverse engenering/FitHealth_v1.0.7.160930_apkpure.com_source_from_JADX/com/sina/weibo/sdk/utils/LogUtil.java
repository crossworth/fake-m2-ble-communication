package com.sina.weibo.sdk.utils;

import android.util.Log;
import com.umeng.socialize.common.SocializeConstants;

public class LogUtil {
    private static boolean sIsLogEnable = true;

    public static void enableLog() {
        sIsLogEnable = true;
    }

    public static void disableLog() {
        sIsLogEnable = false;
    }

    public static void m2214d(String tag, String msg) {
        if (sIsLogEnable) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            Log.d(tag, new StringBuilder(String.valueOf(stackTrace.getFileName() + SocializeConstants.OP_OPEN_PAREN + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName())).append(": ").append(msg).toString());
        }
    }

    public static void m2216i(String tag, String msg) {
        if (sIsLogEnable) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            Log.i(tag, new StringBuilder(String.valueOf(stackTrace.getFileName() + SocializeConstants.OP_OPEN_PAREN + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName())).append(": ").append(msg).toString());
        }
    }

    public static void m2215e(String tag, String msg) {
        if (sIsLogEnable) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            Log.e(tag, new StringBuilder(String.valueOf(stackTrace.getFileName() + SocializeConstants.OP_OPEN_PAREN + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName())).append(": ").append(msg).toString());
        }
    }

    public static void m2218w(String tag, String msg) {
        if (sIsLogEnable) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            Log.w(tag, new StringBuilder(String.valueOf(stackTrace.getFileName() + SocializeConstants.OP_OPEN_PAREN + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName())).append(": ").append(msg).toString());
        }
    }

    public static void m2217v(String tag, String msg) {
        if (sIsLogEnable) {
            StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
            Log.v(tag, new StringBuilder(String.valueOf(stackTrace.getFileName() + SocializeConstants.OP_OPEN_PAREN + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName())).append(": ").append(msg).toString());
        }
    }

    public static String getStackTraceMsg() {
        StackTraceElement stackTrace = Thread.currentThread().getStackTrace()[3];
        return stackTrace.getFileName() + SocializeConstants.OP_OPEN_PAREN + stackTrace.getLineNumber() + ") " + stackTrace.getMethodName();
    }
}
