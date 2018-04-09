package com.isseiaoki.simplecropview.util;

import android.util.Log;

public class Logger {
    private static final String TAG = "SimpleCropView";
    public static boolean enabled = false;

    public static void m3299e(String msg) {
        if (enabled) {
            Log.e(TAG, msg);
        }
    }

    public static void m3300e(String msg, Throwable e) {
        if (enabled) {
            Log.e(TAG, msg, e);
        }
    }
}
