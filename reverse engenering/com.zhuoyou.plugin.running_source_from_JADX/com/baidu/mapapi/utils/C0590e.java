package com.baidu.mapapi.utils;

import android.content.Context;

final class C0590e implements Runnable {
    final /* synthetic */ Context f1850a;
    final /* synthetic */ int f1851b;

    C0590e(Context context, int i) {
        this.f1850a = context;
        this.f1851b = i;
    }

    public void run() {
        long currentTimeMillis = System.currentTimeMillis();
        do {
            if (System.currentTimeMillis() - currentTimeMillis > 3000) {
                C0586a.m1785a(this.f1850a);
                C0586a.m1784a(this.f1851b, this.f1850a);
            }
        } while (!C0586a.f1847v.isInterrupted());
    }
}
