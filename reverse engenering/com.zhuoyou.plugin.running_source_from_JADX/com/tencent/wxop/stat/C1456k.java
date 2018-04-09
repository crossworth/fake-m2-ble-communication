package com.tencent.wxop.stat;

import java.util.List;

class C1456k implements Runnable {
    final /* synthetic */ List f4829a;
    final /* synthetic */ C1429h f4830b;
    final /* synthetic */ C1454i f4831c;

    C1456k(C1454i c1454i, List list, C1429h c1429h) {
        this.f4831c = c1454i;
        this.f4829a = list;
        this.f4830b = c1429h;
    }

    public void run() {
        this.f4831c.m4488a(this.f4829a, this.f4830b);
    }
}
