package com.tencent.wxop.stat;

import android.content.Context;

final class C0899h implements Runnable {
    final /* synthetic */ Context f3069e;
    final /* synthetic */ int f3070g = -1;

    C0899h(Context context) {
        this.f3069e = context;
    }

    public final void run() {
        try {
            C0896e.m2999p(this.f3069e);
            C0908t.m3043s(this.f3069e).m3045b(this.f3070g);
        } catch (Throwable th) {
            C0896e.aV.m2852b(th);
            C0896e.m2985a(this.f3069e, th);
        }
    }
}
