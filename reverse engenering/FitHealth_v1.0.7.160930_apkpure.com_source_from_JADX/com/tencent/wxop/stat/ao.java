package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p023b.C0885l;

final class ao implements Runnable {
    final /* synthetic */ C0897f bN = null;
    final /* synthetic */ Context f3016e;

    ao(Context context) {
        this.f3016e = context;
    }

    public final void run() {
        if (this.f3016e == null) {
            C0896e.aV.error("The Context of StatService.onPause() can not be null!");
        } else {
            C0896e.m2988b(this.f3016e, C0885l.m2868B(this.f3016e), this.bN);
        }
    }
}
