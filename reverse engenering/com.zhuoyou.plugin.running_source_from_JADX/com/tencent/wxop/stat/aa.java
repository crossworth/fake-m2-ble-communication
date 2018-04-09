package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1423h;

final class aa implements Runnable {
    final /* synthetic */ Context f4645a;
    final /* synthetic */ StatSpecifyReportedInfo f4646b;
    final /* synthetic */ StatAppMonitor f4647c;

    aa(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo, StatAppMonitor statAppMonitor) {
        this.f4645a = context;
        this.f4646b = statSpecifyReportedInfo;
        this.f4647c = statAppMonitor;
    }

    public final void run() {
        try {
            new aq(new C1423h(this.f4645a, StatServiceImpl.m4238a(this.f4645a, false, this.f4646b), this.f4647c, this.f4646b)).m4323a();
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4645a, th);
        }
    }
}
