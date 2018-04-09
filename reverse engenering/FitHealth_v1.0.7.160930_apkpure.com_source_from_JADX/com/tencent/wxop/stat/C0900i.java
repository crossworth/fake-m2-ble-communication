package com.tencent.wxop.stat;

import android.content.Context;

final class C0900i implements Runnable {
    final /* synthetic */ Context f3071e;

    C0900i(Context context) {
        this.f3071e = context;
    }

    public final void run() {
        try {
            new Thread(new C0906o(this.f3071e), "NetworkMonitorTask").start();
        } catch (Throwable th) {
            C0896e.aV.m2852b(th);
            C0896e.m2985a(this.f3071e, th);
        }
    }
}
