package com.droi.sdk.internal;

import android.util.Log;
import com.droi.sdk.core.priv.PersistSettings;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

public class DroiLog {
    public static void m2867d(String str, Exception exception) {
        Writer stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        m2868d(str, stringWriter.toString());
    }

    public static void m2868d(String str, String str2) {
        if (isLevelPrint(3)) {
            Log.d(str, str2);
        }
    }

    public static void m2869e(String str, Exception exception) {
        Writer stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        m2870e(str, stringWriter.toString());
    }

    public static void m2870e(String str, String str2) {
        if (isLevelPrint(6)) {
            Log.e(str, str2);
        }
    }

    public static void m2871i(String str, String str2) {
        if (isLevelPrint(4)) {
            Log.i(str, str2);
        }
    }

    private static boolean isLevelPrint(int i) {
        try {
            PersistSettings instance = PersistSettings.instance(PersistSettings.DEV_CONFIG);
            if (instance == null) {
                return false;
            }
            int i2 = 5;
            if (instance.getBoolean(PersistSettings.KEY_DEBUG_MODE, false)) {
                i2 = 2;
            }
            return i2 <= i;
        } catch (Exception e) {
            return true;
        }
    }

    public static void m2872v(String str, String str2) {
        if (isLevelPrint(2)) {
            Log.v(str, str2);
        }
    }

    public static void m2873w(String str, Exception exception) {
        Writer stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        m2874w(str, stringWriter.toString());
    }

    public static void m2874w(String str, String str2) {
        if (isLevelPrint(5)) {
            Log.w(str, str2);
        }
    }
}
