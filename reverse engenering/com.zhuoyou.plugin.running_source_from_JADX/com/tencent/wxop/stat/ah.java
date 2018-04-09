package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1416e;
import com.tencent.wxop.stat.p040a.C1426k;

final class ah implements Runnable {
    final /* synthetic */ Context f4659a;
    final /* synthetic */ String f4660b;
    final /* synthetic */ StatSpecifyReportedInfo f4661c;

    ah(Context context, String str, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4659a = context;
        this.f4660b = str;
        this.f4661c = statSpecifyReportedInfo;
    }

    public final void run() {
        try {
            Long l;
            StatServiceImpl.flushDataToDB(this.f4659a);
            synchronized (StatServiceImpl.f4579o) {
                l = (Long) StatServiceImpl.f4579o.remove(this.f4660b);
            }
            if (l != null) {
                Long valueOf = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                if (valueOf.longValue() <= 0) {
                    valueOf = Long.valueOf(1);
                }
                String j = StatServiceImpl.f4578n;
                if (j != null && j.equals(this.f4660b)) {
                    j = "-";
                }
                C1416e c1426k = new C1426k(this.f4659a, j, this.f4660b, StatServiceImpl.m4238a(this.f4659a, false, this.f4661c), valueOf, this.f4661c);
                if (!this.f4660b.equals(StatServiceImpl.f4577m)) {
                    StatServiceImpl.f4581q.warn("Invalid invocation since previous onResume on diff page.");
                }
                new aq(c1426k).m4323a();
                StatServiceImpl.f4578n = this.f4660b;
                return;
            }
            StatServiceImpl.f4581q.m4374e("Starttime for PageID:" + this.f4660b + " not found, lost onResume()?");
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4659a, th);
        }
    }
}
