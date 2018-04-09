package com.nostra13.universalimageloader.utils;

import android.util.Log;
import com.nostra13.universalimageloader.core.ImageLoader;

public final class C1100L {
    private static final String LOG_FORMAT = "%1$s\n%2$s";
    private static volatile boolean writeDebugLogs = false;
    private static volatile boolean writeLogs = true;

    private C1100L() {
    }

    @Deprecated
    public static void enableLogging() {
        C1100L.writeLogs(true);
    }

    @Deprecated
    public static void disableLogging() {
        C1100L.writeLogs(false);
    }

    public static void writeDebugLogs(boolean writeDebugLogs) {
        writeDebugLogs = writeDebugLogs;
    }

    public static void writeLogs(boolean writeLogs) {
        writeLogs = writeLogs;
    }

    public static void m3301d(String message, Object... args) {
        if (writeDebugLogs) {
            C1100L.log(3, null, message, args);
        }
    }

    public static void m3305i(String message, Object... args) {
        C1100L.log(4, null, message, args);
    }

    public static void m3306w(String message, Object... args) {
        C1100L.log(5, null, message, args);
    }

    public static void m3303e(Throwable ex) {
        C1100L.log(6, ex, null, new Object[0]);
    }

    public static void m3302e(String message, Object... args) {
        C1100L.log(6, null, message, args);
    }

    public static void m3304e(Throwable ex, String message, Object... args) {
        C1100L.log(6, ex, message, args);
    }

    private static void log(int priority, Throwable ex, String message, Object... args) {
        if (writeLogs) {
            String log;
            if (args.length > 0) {
                message = String.format(message, args);
            }
            if (ex == null) {
                log = message;
            } else {
                String logMessage;
                if (message == null) {
                    logMessage = ex.getMessage();
                } else {
                    logMessage = message;
                }
                String logBody = Log.getStackTraceString(ex);
                log = String.format(LOG_FORMAT, new Object[]{logMessage, logBody});
            }
            Log.println(priority, ImageLoader.TAG, log);
        }
    }
}
