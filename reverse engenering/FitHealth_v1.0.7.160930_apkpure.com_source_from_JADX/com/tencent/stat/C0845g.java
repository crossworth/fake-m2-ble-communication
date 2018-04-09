package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.p021a.C1738d;
import java.lang.Thread.UncaughtExceptionHandler;

final class C0845g implements UncaughtExceptionHandler {
    final /* synthetic */ Context f2925a;

    C0845g(Context context) {
        this.f2925a = context;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (StatConfig.isEnableStatService()) {
            C0850n.m2778a(this.f2925a).m2798a(new C1738d(this.f2925a, StatService.m2645a(this.f2925a, false), 2, th), null);
            StatService.f2834i.debug("MTA has caught the following uncaught exception:");
            StatService.f2834i.error((Object) th);
            if (StatService.f2835j != null) {
                StatService.f2834i.debug("Call the original uncaught exception handler.");
                StatService.f2835j.uncaughtException(thread, th);
                return;
            }
            StatService.f2834i.debug("Original uncaught exception handler not set.");
        }
    }
}
