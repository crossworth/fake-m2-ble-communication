package com.tencent.wxop.stat;

import com.tencent.wxop.stat.p040a.C1420d;
import java.lang.Thread.UncaughtExceptionHandler;

class ao implements UncaughtExceptionHandler {
    ao() {
    }

    public void uncaughtException(Thread thread, Throwable th) {
        if (StatConfig.isEnableStatService() && StatServiceImpl.f4584t != null) {
            if (StatConfig.isAutoExceptionCaught()) {
                au.m4332a(StatServiceImpl.f4584t).m4361a(new C1420d(StatServiceImpl.f4584t, StatServiceImpl.m4238a(StatServiceImpl.f4584t, false, null), 2, th, thread, null), null, false, true);
                StatServiceImpl.f4581q.debug("MTA has caught the following uncaught exception:");
                StatServiceImpl.f4581q.error(th);
            }
            StatServiceImpl.flushDataToDB(StatServiceImpl.f4584t);
            if (StatServiceImpl.f4582r != null) {
                StatServiceImpl.f4581q.m4373d("Call the original uncaught exception handler.");
                if (!(StatServiceImpl.f4582r instanceof ao)) {
                    StatServiceImpl.f4582r.uncaughtException(thread, th);
                }
            }
        }
    }
}
