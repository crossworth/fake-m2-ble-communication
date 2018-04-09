package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.p039a.C1369d;
import java.lang.Thread.UncaughtExceptionHandler;

final class C1398g implements UncaughtExceptionHandler {
    final /* synthetic */ Context f4461a;

    C1398g(Context context) {
        this.f4461a = context;
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (StatConfig.isEnableStatService()) {
            C1405n.m4189a(this.f4461a).m4209a(new C1369d(this.f4461a, StatService.m4029a(this.f4461a, false), 2, th), null);
            StatService.f4336i.debug("MTA has caught the following uncaught exception:");
            StatService.f4336i.error((Object) th);
            if (StatService.f4337j != null) {
                StatService.f4336i.debug("Call the original uncaught exception handler.");
                StatService.f4337j.uncaughtException(thread, th);
                return;
            }
            StatService.f4336i.debug("Original uncaught exception handler not set.");
        }
    }
}
