package com.tencent.wxop.stat;

import java.util.List;

class ba implements C1429h {
    final /* synthetic */ List f4721a;
    final /* synthetic */ boolean f4722b;
    final /* synthetic */ au f4723c;

    ba(au auVar, List list, boolean z) {
        this.f4723c = auVar;
        this.f4721a = list;
        this.f4722b = z;
    }

    public void mo2225a() {
        StatServiceImpl.m4253c();
        this.f4723c.m4364a(this.f4721a, this.f4722b, true);
    }

    public void mo2226b() {
        StatServiceImpl.m4254d();
        this.f4723c.m4363a(this.f4721a, 1, this.f4722b, true);
    }
}
