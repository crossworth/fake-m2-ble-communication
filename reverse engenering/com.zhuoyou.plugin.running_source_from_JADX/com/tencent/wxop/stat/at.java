package com.tencent.wxop.stat;

class at implements C1429h {
    final /* synthetic */ aq f4688a;

    at(aq aqVar) {
        this.f4688a = aqVar;
    }

    public void mo2225a() {
        StatServiceImpl.m4253c();
        if (au.m4346b().f4692a > 0) {
            StatServiceImpl.commitEvents(this.f4688a.f4684d, -1);
        }
    }

    public void mo2226b() {
        au.m4346b().m4361a(this.f4688a.f4681a, null, this.f4688a.f4683c, true);
        StatServiceImpl.m4254d();
    }
}
