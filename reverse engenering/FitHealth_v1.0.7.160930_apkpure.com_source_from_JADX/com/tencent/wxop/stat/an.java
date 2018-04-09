package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p023b.C0885l;

final class an implements Runnable {
    final /* synthetic */ Context f3015e;

    an(Context context) {
        this.f3015e = context;
    }

    public final void run() {
        C0898g.m3012r(C0896e.aY).aa();
        C0885l.m2886a(this.f3015e, true);
        C0908t.m3043s(this.f3015e);
        ak.m2844Z(this.f3015e);
        C0896e.aW = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(new C0905n());
        if (C0894c.m2948j() == C0895d.APP_LAUNCH) {
            C0896e.m2997o(this.f3015e);
        }
        if (C0894c.m2949k()) {
            C0896e.aV.m2855e("Init MTA StatService success.");
        }
    }
}
