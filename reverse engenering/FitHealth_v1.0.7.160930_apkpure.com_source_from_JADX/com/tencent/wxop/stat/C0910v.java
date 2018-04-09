package com.tencent.wxop.stat;

import java.util.List;

final class C0910v implements Runnable {
    final /* synthetic */ boolean bR = true;
    final /* synthetic */ List bc;
    final /* synthetic */ boolean ch;
    final /* synthetic */ C0908t ci;

    C0910v(C0908t c0908t, List list, boolean z) {
        this.ci = c0908t;
        this.bc = list;
        this.ch = z;
    }

    public final void run() {
        this.ci.m3038a(this.bc, this.ch);
        if (this.bR) {
            this.bc.clear();
        }
    }
}
