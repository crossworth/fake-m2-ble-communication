package com.tencent.wxop.stat;

import android.content.Context;

final class as implements Runnable {
    final /* synthetic */ String f3020a;
    final /* synthetic */ C0897f bM;
    final /* synthetic */ Context co;

    as(String str, Context context, C0897f c0897f) {
        this.f3020a = str;
        this.co = context;
        this.bM = c0897f;
    }

    public final void run() {
        try {
            synchronized (C0896e.aT) {
                if (C0896e.aT.size() >= C0894c.m2964v()) {
                    C0896e.aV.error("The number of page events exceeds the maximum value " + Integer.toString(C0894c.m2964v()));
                    return;
                }
                C0896e.aR = this.f3020a;
                if (C0896e.aT.containsKey(C0896e.aR)) {
                    C0896e.aV.m2854d("Duplicate PageID : " + C0896e.aR + ", onResume() repeated?");
                    return;
                }
                C0896e.aT.put(C0896e.aR, Long.valueOf(System.currentTimeMillis()));
                C0896e.m2981a(this.co, true, this.bM);
            }
        } catch (Throwable th) {
            C0896e.aV.m2852b(th);
            C0896e.m2985a(this.co, th);
        }
    }
}
