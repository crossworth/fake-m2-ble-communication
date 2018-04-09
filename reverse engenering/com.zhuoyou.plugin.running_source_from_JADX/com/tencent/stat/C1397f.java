package com.tencent.stat;

import java.util.List;

class C1397f implements Runnable {
    final /* synthetic */ List f4458a;
    final /* synthetic */ C1378c f4459b;
    final /* synthetic */ C1395d f4460c;

    C1397f(C1395d c1395d, List list, C1378c c1378c) {
        this.f4460c = c1395d;
        this.f4458a = list;
        this.f4459b = c1378c;
    }

    public void run() {
        try {
            this.f4460c.m4178a(this.f4458a, this.f4459b);
        } catch (Object th) {
            C1395d.f4451c.m4085e(th);
        }
    }
}
