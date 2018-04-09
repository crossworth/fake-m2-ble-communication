package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1416e;
import com.tencent.wxop.stat.p040a.C1418b;
import com.tencent.wxop.stat.p040a.C1419c;

final class C1466u implements Runnable {
    final /* synthetic */ Context f4848a;
    final /* synthetic */ StatSpecifyReportedInfo f4849b;
    final /* synthetic */ C1419c f4850c;

    C1466u(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo, C1419c c1419c) {
        this.f4848a = context;
        this.f4849b = statSpecifyReportedInfo;
        this.f4850c = c1419c;
    }

    public final void run() {
        try {
            C1416e c1418b = new C1418b(this.f4848a, StatServiceImpl.m4238a(this.f4848a, false, this.f4849b), this.f4850c.f4605a, this.f4849b);
            c1418b.m4280b().f4607c = this.f4850c.f4607c;
            new aq(c1418b).m4323a();
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4848a, th);
        }
    }
}
