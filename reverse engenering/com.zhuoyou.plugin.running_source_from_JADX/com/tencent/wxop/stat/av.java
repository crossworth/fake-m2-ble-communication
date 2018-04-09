package com.tencent.wxop.stat;

import java.util.List;

class av implements Runnable {
    final /* synthetic */ List f4703a;
    final /* synthetic */ int f4704b;
    final /* synthetic */ boolean f4705c;
    final /* synthetic */ boolean f4706d;
    final /* synthetic */ au f4707e;

    av(au auVar, List list, int i, boolean z, boolean z2) {
        this.f4707e = auVar;
        this.f4703a = list;
        this.f4704b = i;
        this.f4705c = z;
        this.f4706d = z2;
    }

    public void run() {
        this.f4707e.m4342a(this.f4703a, this.f4704b, this.f4705c);
        if (this.f4706d) {
            this.f4703a.clear();
        }
    }
}
