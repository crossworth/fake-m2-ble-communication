package com.tencent.stat;

class C1404m implements C1378c {
    final /* synthetic */ C1402k f4470a;

    C1404m(C1402k c1402k) {
        this.f4470a = c1402k;
    }

    public void mo2221a() {
        if (C1405n.m4196b().m4207a() >= StatConfig.getMaxBatchReportCount()) {
            C1405n.m4196b().m4208a(StatConfig.getMaxBatchReportCount());
        }
    }

    public void mo2222b() {
    }
}
