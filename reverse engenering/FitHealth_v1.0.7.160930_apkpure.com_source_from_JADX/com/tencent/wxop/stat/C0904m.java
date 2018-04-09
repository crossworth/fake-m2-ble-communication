package com.tencent.wxop.stat;

import android.content.Context;

final class C0904m implements Runnable {
    final /* synthetic */ C0897f bN = null;
    final /* synthetic */ Context f3075e;

    C0904m(Context context) {
        this.f3075e = context;
    }

    public final void run() {
        try {
            C0896e.m2981a(this.f3075e, false, this.bN);
        } catch (Throwable th) {
            C0896e.aV.m2852b(th);
        }
    }
}
