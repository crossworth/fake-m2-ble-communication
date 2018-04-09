package com.tencent.wxop.stat;

final class ab implements Runnable {
    final /* synthetic */ int aI;
    final /* synthetic */ C0908t cl;

    ab(C0908t c0908t, int i) {
        this.cl = c0908t;
        this.aI = i;
    }

    public final void run() {
        C0908t.m3032a(this.cl, this.aI, true);
        C0908t.m3032a(this.cl, this.aI, false);
    }
}
