package com.amap.api.mapcore.util;

import android.content.Context;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: BasicLogHandler */
public class eb {
    protected static eb f535a;
    protected UncaughtExceptionHandler f536b;
    protected boolean f537c = true;

    public static void m742a(Throwable th, String str, String str2) {
        th.printStackTrace();
        if (f535a != null) {
            f535a.mo1645a(th, 1, str, str2);
        }
    }

    protected void mo1645a(Throwable th, int i, String str, String str2) {
    }

    protected void mo1644a(Context context, dv dvVar, boolean z) {
    }
}
