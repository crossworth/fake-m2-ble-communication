package com.tencent.wxop.stat;

import java.util.List;

class aw implements Runnable {
    final /* synthetic */ List f4708a;
    final /* synthetic */ boolean f4709b;
    final /* synthetic */ boolean f4710c;
    final /* synthetic */ au f4711d;

    aw(au auVar, List list, boolean z, boolean z2) {
        this.f4711d = auVar;
        this.f4708a = list;
        this.f4709b = z;
        this.f4710c = z2;
    }

    public void run() {
        this.f4711d.m4343a(this.f4708a, this.f4709b);
        if (this.f4710c) {
            this.f4708a.clear();
        }
    }
}
