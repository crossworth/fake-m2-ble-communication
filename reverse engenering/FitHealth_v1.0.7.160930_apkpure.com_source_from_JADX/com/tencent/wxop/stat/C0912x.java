package com.tencent.wxop.stat;

import com.tencent.wxop.stat.p022a.C0873d;

final class C0912x implements Runnable {
    final /* synthetic */ C0873d bP;
    final /* synthetic */ boolean bR;
    final /* synthetic */ boolean ba;
    final /* synthetic */ C0908t cg;
    final /* synthetic */ aj ck;

    C0912x(C0908t c0908t, C0873d c0873d, aj ajVar, boolean z, boolean z2) {
        this.cg = c0908t;
        this.bP = c0873d;
        this.ck = ajVar;
        this.bR = z;
        this.ba = z2;
    }

    public final void run() {
        this.cg.m3029a(this.bP, this.ck, this.bR, this.ba);
    }
}
