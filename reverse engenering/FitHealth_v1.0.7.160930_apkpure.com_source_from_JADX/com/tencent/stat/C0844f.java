package com.tencent.stat;

import java.util.List;

class C0844f implements Runnable {
    final /* synthetic */ List f2922a;
    final /* synthetic */ C0828c f2923b;
    final /* synthetic */ C0843d f2924c;

    C0844f(C0843d c0843d, List list, C0828c c0828c) {
        this.f2924c = c0843d;
        this.f2922a = list;
        this.f2923b = c0828c;
    }

    public void run() {
        try {
            this.f2924c.m2771a(this.f2922a, this.f2923b);
        } catch (Object th) {
            C0843d.f2916c.m2680e(th);
        }
    }
}
