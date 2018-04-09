package com.tencent.wxop.stat;

import android.content.Context;

final class an implements Runnable {
    final /* synthetic */ Context f4675a;
    final /* synthetic */ StatSpecifyReportedInfo f4676b;

    an(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4675a = context;
        this.f4676b = statSpecifyReportedInfo;
    }

    public final void run() {
        try {
            StatServiceImpl.m4238a(this.f4675a, false, this.f4676b);
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
        }
    }
}
