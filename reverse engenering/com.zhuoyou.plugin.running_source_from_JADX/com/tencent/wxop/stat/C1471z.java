package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p040a.C1416e;
import com.tencent.wxop.stat.p040a.C1418b;
import com.tencent.wxop.stat.p040a.C1419c;

final class C1471z implements Runnable {
    final /* synthetic */ String f4864a;
    final /* synthetic */ C1419c f4865b;
    final /* synthetic */ Context f4866c;
    final /* synthetic */ StatSpecifyReportedInfo f4867d;

    C1471z(String str, C1419c c1419c, Context context, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        this.f4864a = str;
        this.f4865b = c1419c;
        this.f4866c = context;
        this.f4867d = statSpecifyReportedInfo;
    }

    public final void run() {
        try {
            if (StatServiceImpl.m4246a(this.f4864a)) {
                StatServiceImpl.f4581q.error((Object) "The event_id of StatService.trackCustomEndEvent() can not be null or empty.");
                return;
            }
            Long l = (Long) StatServiceImpl.f4569e.remove(this.f4865b);
            if (l != null) {
                C1416e c1418b = new C1418b(this.f4866c, StatServiceImpl.m4238a(this.f4866c, false, this.f4867d), this.f4865b.f4605a, this.f4867d);
                c1418b.m4280b().f4607c = this.f4865b.f4607c;
                l = Long.valueOf((System.currentTimeMillis() - l.longValue()) / 1000);
                c1418b.m4278a(Long.valueOf(l.longValue() <= 0 ? 1 : l.longValue()).longValue());
                new aq(c1418b).m4323a();
                return;
            }
            StatServiceImpl.f4581q.warn("No start time found for custom event: " + this.f4865b.toString() + ", lost trackCustomBeginKVEvent()?");
        } catch (Throwable th) {
            StatServiceImpl.f4581q.m4375e(th);
            StatServiceImpl.m4244a(this.f4866c, th);
        }
    }
}
