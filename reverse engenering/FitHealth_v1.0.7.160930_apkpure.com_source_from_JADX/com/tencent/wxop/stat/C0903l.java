package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p023b.C0885l;

final class C0903l implements Runnable {
    final /* synthetic */ C0897f bN = null;
    final /* synthetic */ Context f3074e;

    C0903l(Context context) {
        this.f3074e = context;
    }

    public final void run() {
        if (this.f3074e == null) {
            C0896e.aV.error("The Context of StatService.onResume() can not be null!");
        } else {
            C0896e.m2984a(this.f3074e, C0885l.m2868B(this.f3074e), this.bN);
        }
    }
}
