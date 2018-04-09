package com.tencent.wxop.stat.p023b;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public final class C0881f {
    ExecutorService cG;

    public C0881f() {
        this.cG = null;
        this.cG = Executors.newSingleThreadExecutor();
    }

    public final void m2861a(Runnable runnable) {
        this.cG.execute(runnable);
    }
}
