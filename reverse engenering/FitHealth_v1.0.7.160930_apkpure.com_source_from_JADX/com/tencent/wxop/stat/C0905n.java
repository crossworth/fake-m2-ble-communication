package com.tencent.wxop.stat;

import com.tencent.wxop.stat.p022a.C1756c;
import java.lang.Thread.UncaughtExceptionHandler;

final class C0905n implements UncaughtExceptionHandler {
    C0905n() {
    }

    public final void uncaughtException(Thread thread, Throwable th) {
        if (C0894c.m2951l() && C0896e.aY != null) {
            if (C0894c.m2966x()) {
                C0908t.m3043s(C0896e.aY).m3046b(new C1756c(C0896e.aY, C0896e.m2981a(C0896e.aY, false, null), th, thread), null, false, true);
                C0896e.aV.debug("MTA has caught the following uncaught exception:");
                C0896e.aV.m2850a(th);
            }
            C0896e.m2999p(C0896e.aY);
            if (C0896e.aW != null) {
                C0896e.aV.m2855e("Call the original uncaught exception handler.");
                if (!(C0896e.aW instanceof C0905n)) {
                    C0896e.aW.uncaughtException(thread, th);
                }
            }
        }
    }
}
