package com.droi.sdk.analytics;

import android.util.Log;
import com.droi.sdk.internal.DroiLog;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

class C0753a {
    public static void m2311a(String str, Exception exception) {
        Writer stringWriter = new StringWriter();
        exception.printStackTrace(new PrintWriter(stringWriter));
        DroiLog.m2870e("DroiAnalytics", str + ":" + stringWriter.toString());
    }

    public static void m2312a(String str, String str2) {
        DroiLog.m2871i("DroiAnalytics", str + ":" + str2);
    }

    public static void m2313b(String str, String str2) {
        DroiLog.m2874w("DroiAnalytics", str + ":" + str2);
    }

    public static void m2314c(String str, String str2) {
        DroiLog.m2870e("DroiAnalytics", str + ":" + str2);
    }

    public static void m2315d(String str, String str2) {
        Log.e("DroiAnalytics", str + ":" + str2);
    }

    public static void m2316e(String str, String str2) {
        Log.i("DroiAnalytics", str + ":" + str2);
    }
}
