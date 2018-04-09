package com.tencent.stat;

import java.util.List;

class C1407p implements Runnable {
    final /* synthetic */ List f4479a;
    final /* synthetic */ int f4480b;
    final /* synthetic */ C1405n f4481c;

    C1407p(C1405n c1405n, List list, int i) {
        this.f4481c = c1405n;
        this.f4479a = list;
        this.f4480b = i;
    }

    public void run() {
        this.f4481c.m4202b(this.f4479a, this.f4480b);
    }
}
