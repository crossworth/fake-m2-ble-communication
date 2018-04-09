package com.tencent.wxop.stat;

import com.tencent.wxop.stat.p040a.C1416e;

class ay implements Runnable {
    final /* synthetic */ C1416e f4713a;
    final /* synthetic */ C1429h f4714b;
    final /* synthetic */ boolean f4715c;
    final /* synthetic */ boolean f4716d;
    final /* synthetic */ au f4717e;

    ay(au auVar, C1416e c1416e, C1429h c1429h, boolean z, boolean z2) {
        this.f4717e = auVar;
        this.f4713a = c1416e;
        this.f4714b = c1429h;
        this.f4715c = z;
        this.f4716d = z2;
    }

    public void run() {
        this.f4717e.m4348b(this.f4713a, this.f4714b, this.f4715c, this.f4716d);
    }
}
