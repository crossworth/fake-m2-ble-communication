package com.tencent.wxop.stat;

import android.content.Context;

final class ai implements Runnable {
    final /* synthetic */ Context f4662a;
    final /* synthetic */ StatSpecifyReportedInfo f4663b;

    ai(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4662a = context;
        this.f4663b = statSpecifyReportedInfo;
    }

    public final void run() {
        try {
            StatServiceImpl.stopSession();
            StatServiceImpl.m4238a(this.f4662a, true, this.f4663b);
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4662a, th);
        }
    }
}
