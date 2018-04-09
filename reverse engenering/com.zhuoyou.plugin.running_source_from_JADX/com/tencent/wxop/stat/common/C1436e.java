package com.tencent.wxop.stat.common;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class C1436e {
    ExecutorService f4767a;

    public C1436e() {
        this.f4767a = null;
        this.f4767a = Executors.newSingleThreadExecutor();
    }

    public void m4388a(Runnable runnable) {
        this.f4767a.execute(runnable);
    }
}
