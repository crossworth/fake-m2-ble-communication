package com.amap.api.mapcore.util;

import android.content.Context;
import android.text.TextUtils;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: DynamicExceptionHandler */
public class fb implements UncaughtExceptionHandler {
    private static fb f583a;
    private UncaughtExceptionHandler f584b = Thread.getDefaultUncaughtExceptionHandler();
    private Context f585c;
    private dv f586d;

    private fb(Context context, dv dvVar) {
        this.f585c = context.getApplicationContext();
        this.f586d = dvVar;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    static synchronized fb m865a(Context context, dv dvVar) {
        fb fbVar;
        synchronized (fb.class) {
            if (f583a == null) {
                f583a = new fb(context, dvVar);
            }
            fbVar = f583a;
        }
        return fbVar;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String a = dx.m713a(th);
        try {
            if (!TextUtils.isEmpty(a) && ((a.contains("amapdynamic") || a.contains("admic")) && a.contains("com.amap.api"))) {
                fa.m857a(new ek(this.f585c, fc.m4276a()), this.f585c, this.f586d);
            }
        } catch (Throwable th2) {
            eb.m742a(th2, "DynamicExceptionHandler", "uncaughtException");
        }
        if (this.f584b != null) {
            this.f584b.uncaughtException(thread, th);
        }
    }
}
