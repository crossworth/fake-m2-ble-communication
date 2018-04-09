package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1416e;
import com.tencent.wxop.stat.p040a.C1418b;
import com.tencent.wxop.stat.p040a.C1419c;

final class C1469x implements Runnable {
    final /* synthetic */ String f4857a;
    final /* synthetic */ C1419c f4858b;
    final /* synthetic */ Context f4859c;
    final /* synthetic */ StatSpecifyReportedInfo f4860d;

    C1469x(String str, C1419c c1419c, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4857a = str;
        this.f4858b = c1419c;
        this.f4859c = context;
        this.f4860d = statSpecifyReportedInfo;
    }

    public final void run() {
        try {
            if (StatServiceImpl.m4246a(this.f4857a)) {
                StatServiceImpl.f4581q.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
                return;
            }
            Long l = (Long) StatServiceImpl.f4569e.remove(this.f4858b);
            if (l != null) {
                C1416e c1418b = new C1418b(this.f4859c, StatServiceImpl.m4238a(this.f4859c, false, this.f4860d), this.f4858b.f4605a, this.f4860d);
                c1418b.m4280b().f4606b = this.f4858b.f4606b;
                l = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                c1418b.m4278a(Long.valueOf(l.longValue() == 0 ? 1 : l.longValue()).longValue());
                new aq(c1418b).m4323a();
                return;
            }
            StatServiceImpl.f4581q.error("No start time found for custom event: " + this.f4858b.toString() + ", lost trackCustomBeginEvent()?");
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4859c, th);
        }
    }
}
