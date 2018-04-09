package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p022a.C0872b;
import com.tencent.wxop.stat.p022a.C0873d;
import com.tencent.wxop.stat.p022a.C1755a;

final class ar implements Runnable {
    final /* synthetic */ C0897f bN = null;
    final /* synthetic */ C0872b f3018do;
    final /* synthetic */ Context f3019e;

    ar(Context context, C0872b c0872b) {
        this.f3019e = context;
        this.f3018do = c0872b;
    }

    public final void run() {
        try {
            C0873d c1755a = new C1755a(this.f3019e, C0896e.m2981a(this.f3019e, false, this.bN), this.f3018do.f3005a, this.bN);
            c1755a.ab().bm = this.f3018do.bm;
            new C0907p(c1755a).ah();
        } catch (Throwable th) {
            C0896e.aV.m2852b(th);
            C0896e.m2985a(this.f3019e, th);
        }
    }
}
