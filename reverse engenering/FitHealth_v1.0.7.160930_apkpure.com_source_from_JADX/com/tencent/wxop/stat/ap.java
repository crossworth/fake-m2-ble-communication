package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p022a.C1756c;
import com.tencent.wxop.stat.p022a.C1757f;

final class ap implements Runnable {
    final /* synthetic */ Throwable dn;
    final /* synthetic */ Context f3017e;

    ap(Context context, Throwable th) {
        this.f3017e = context;
        this.dn = th;
    }

    public final void run() {
        try {
            if (C0894c.m2951l()) {
                new C0907p(new C1756c(this.f3017e, C0896e.m2981a(this.f3017e, false, null), this.dn, C1757f.bw)).ah();
            }
        } catch (Throwable th) {
            C0896e.aV.m2854d("reportSdkSelfException error: " + th);
        }
    }
}
