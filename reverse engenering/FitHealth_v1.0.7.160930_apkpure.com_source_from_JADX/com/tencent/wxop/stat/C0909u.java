package com.tencent.wxop.stat;

import java.util.List;

final class C0909u implements Runnable {
    final /* synthetic */ boolean bR;
    final /* synthetic */ boolean ba;
    final /* synthetic */ List bc;
    final /* synthetic */ C0908t cg;
    final /* synthetic */ int f3077g = 1;

    C0909u(C0908t c0908t, List list, boolean z) {
        this.cg = c0908t;
        this.bc = list;
        this.bR = z;
        this.ba = true;
    }

    public final void run() {
        this.cg.m3037a(this.bc, this.f3077g, this.bR);
        if (this.ba) {
            this.bc.clear();
        }
    }
}
