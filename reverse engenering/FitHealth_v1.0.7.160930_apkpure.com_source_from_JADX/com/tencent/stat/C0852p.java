package com.tencent.stat;

import java.util.List;

class C0852p implements Runnable {
    final /* synthetic */ List f2941a;
    final /* synthetic */ int f2942b;
    final /* synthetic */ C0850n f2943c;

    C0852p(C0850n c0850n, List list, int i) {
        this.f2943c = c0850n;
        this.f2941a = list;
        this.f2942b = i;
    }

    public void run() {
        this.f2943c.m2791b(this.f2941a, this.f2942b);
    }
}
