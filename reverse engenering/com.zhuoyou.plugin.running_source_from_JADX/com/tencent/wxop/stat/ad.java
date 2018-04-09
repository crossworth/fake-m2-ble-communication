package com.tencent.wxop.stat;

import android.content.Context;

final class ad implements Runnable {
    final /* synthetic */ Context f4652a;
    final /* synthetic */ int f4653b;

    ad(Context context, int i) {
        this.f4652a = context;
        this.f4653b = i;
    }

    public final void run() {
        try {
            StatServiceImpl.flushDataToDB(this.f4652a);
            au.m4332a(this.f4652a).m4360a(this.f4653b);
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4652a, th);
        }
    }
}
