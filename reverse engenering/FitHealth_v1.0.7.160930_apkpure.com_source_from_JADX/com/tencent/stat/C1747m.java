package com.tencent.stat;

class C1747m implements C0828c {
    final /* synthetic */ C0849k f4721a;

    C1747m(C0849k c0849k) {
        this.f4721a = c0849k;
    }

    public void mo2144a() {
        if (C0850n.m2785b().m2796a() >= StatConfig.getMaxBatchReportCount()) {
            C0850n.m2785b().m2797a(StatConfig.getMaxBatchReportCount());
        }
    }

    public void mo2145b() {
    }
}
