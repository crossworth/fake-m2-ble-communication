package com.tencent.wxop.stat;

import android.content.Context;

final class ae implements Runnable {
    final /* synthetic */ Context f4654a;

    ae(Context context) {
        this.f4654a = context;
    }

    public final void run() {
        try {
            new Thread(new ap(this.f4654a, null, null), "NetworkMonitorTask").start();
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4654a, th);
        }
    }
}
