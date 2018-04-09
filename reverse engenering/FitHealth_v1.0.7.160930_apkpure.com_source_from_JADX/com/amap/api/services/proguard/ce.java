package com.amap.api.services.proguard;

import android.content.Context;
import android.text.TextUtils;
import java.lang.Thread.UncaughtExceptionHandler;

/* compiled from: DynamicExceptionHandler */
public class ce implements UncaughtExceptionHandler {
    private static ce f1444a;
    private UncaughtExceptionHandler f1445b = Thread.getDefaultUncaughtExceptionHandler();
    private Context f1446c;
    private ba f1447d;

    private ce(Context context, ba baVar) {
        this.f1446c = context.getApplicationContext();
        this.f1447d = baVar;
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    static synchronized ce m1462a(Context context, ba baVar) {
        ce ceVar;
        synchronized (ce.class) {
            if (f1444a == null) {
                f1444a = new ce(context, baVar);
            }
            ceVar = f1444a;
        }
        return ceVar;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        String a = bb.m1313a(th);
        try {
            if (!TextUtils.isEmpty(a) && ((a.contains("amapdynamic") || a.contains("admic")) && a.contains("com.amap.api"))) {
                cd.m1454a(new bn(this.f1446c, cf.m4469c()), this.f1446c, this.f1447d);
            }
        } catch (Throwable th2) {
            be.m1340a(th2, "DynamicExceptionHandler", "uncaughtException");
        }
        if (this.f1445b != null) {
            this.f1445b.uncaughtException(thread, th);
        }
    }
}
