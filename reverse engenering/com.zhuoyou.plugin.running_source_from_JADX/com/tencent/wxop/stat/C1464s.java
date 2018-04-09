package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1416e;
import com.tencent.wxop.stat.p040a.C1418b;
import com.tencent.wxop.stat.p040a.C1419c;

final class C1464s implements Runnable {
    final /* synthetic */ Context f4845a;
    final /* synthetic */ StatSpecifyReportedInfo f4846b;
    final /* synthetic */ C1419c f4847c;

    C1464s(Context context, StatSpecifyReportedInfo statSpecifyReportedInfo, C1419c c1419c) {
        this.f4845a = context;
        this.f4846b = statSpecifyReportedInfo;
        this.f4847c = c1419c;
    }

    public final void run() {
        try {
            C1416e c1418b = new C1418b(this.f4845a, StatServiceImpl.m4238a(this.f4845a, false, this.f4846b), this.f4847c.f4605a, this.f4846b);
            c1418b.m4280b().f4606b = this.f4847c.f4606b;
            new aq(c1418b).m4323a();
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4845a, th);
        }
    }
}
