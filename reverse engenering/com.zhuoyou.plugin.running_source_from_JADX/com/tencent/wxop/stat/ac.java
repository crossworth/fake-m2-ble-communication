package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1416e;
import com.tencent.wxop.stat.p040a.C1418b;
import com.tencent.wxop.stat.p040a.C1419c;

final class ac implements Runnable {
    final /* synthetic */ Context f4648a;
    final /* synthetic */ StatSpecifyReportedInfo f4649b;
    final /* synthetic */ C1419c f4650c;
    final /* synthetic */ int f4651d;

    ac(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo, C1419c c1419c, int i) {
        this.f4648a = context;
        this.f4649b = statSpecifyReportedInfo;
        this.f4650c = c1419c;
        this.f4651d = i;
    }

    public final void run() {
        try {
            C1416e c1418b = new C1418b(this.f4648a, StatServiceImpl.m4238a(this.f4648a, false, this.f4649b), this.f4650c.f4605a, this.f4649b);
            c1418b.m4280b().f4607c = this.f4650c.f4607c;
            Long valueOf = Long.valueOf((long) this.f4651d);
            c1418b.m4278a(Long.valueOf(valueOf.longValue() <= 0 ? 1 : valueOf.longValue()).longValue());
            new aq(c1418b).m4323a();
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4648a, th);
        }
    }
}
