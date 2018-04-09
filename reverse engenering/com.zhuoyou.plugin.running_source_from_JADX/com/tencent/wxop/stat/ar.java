package com.tencent.wxop.stat;

class ar implements C1429h {
    final /* synthetic */ aq f4686a;

    ar(aq aqVar) {
        this.f4686a = aqVar;
    }

    public void mo2225a() {
        StatServiceImpl.m4253c();
        if (au.m4346b().m4359a() >= StatConfig.getMaxBatchReportCount()) {
            au.m4346b().m4360a(StatConfig.getMaxBatchReportCount());
        }
    }

    public void mo2226b() {
        StatServiceImpl.m4254d();
    }
}
