package com.amap.api.services.proguard;

import android.content.Context;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: BasicLogHandler */
public class be {
    protected static be f1397a;
    protected UncaughtExceptionHandler f1398b;
    protected boolean f1399c = true;

    public static void m1340a(Throwable th, String str, String str2) {
        th.printStackTrace();
        if (f1397a != null) {
            f1397a.mo1762a(th, 1, str, str2);
        }
    }

    protected void mo1762a(Throwable th, int i, String str, String str2) {
    }

    protected void mo1761a(Context context, ba baVar, boolean z) {
    }
}
