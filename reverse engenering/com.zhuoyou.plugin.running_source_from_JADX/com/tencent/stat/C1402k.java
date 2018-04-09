package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.common.C1389k;
import com.tencent.stat.common.C1394p;
import com.tencent.stat.p039a.C1365e;
import com.tencent.stat.p039a.C1370f;

class C1402k implements Runnable {
    private C1365e f4466a;
    private StatReportStrategy f4467b = null;
    private C1378c f4468c = new C1403l(this);

    public C1402k(C1365e c1365e) {
        this.f4466a = c1365e;
        this.f4467b = StatConfig.getStatSendStrategy();
    }

    private void m4183a() {
        if (C1405n.m4196b().m4207a() > 0) {
            C1405n.m4196b().m4209a(this.f4466a, null);
            C1405n.m4196b().m4208a(-1);
            return;
        }
        m4184a(true);
    }

    private void m4184a(boolean z) {
        C1395d.m4175b().m4177a(this.f4466a, this.f4468c);
    }

    public void run() {
        try {
            if (!StatConfig.isEnableStatService()) {
                return;
            }
            if (this.f4466a.mo2219a() == C1370f.ERROR || this.f4466a.m4046d().length() <= StatConfig.getMaxReportEventLength()) {
                if (StatConfig.getMaxSessionStatReportCount() > 0) {
                    if (StatConfig.getCurSessionStatReportCount() >= StatConfig.getMaxSessionStatReportCount()) {
                        StatService.f4336i.m4085e((Object) "Times for reporting events has reached the limit of StatConfig.getMaxSessionStatReportCount() in current session.");
                        return;
                    }
                    StatConfig.m4017c();
                }
                StatService.f4336i.m4086i("Lauch stat task in thread:" + Thread.currentThread().getName());
                Context c = this.f4466a.m4045c();
                if (C1389k.m4141h(c)) {
                    if (StatConfig.isEnableSmartReporting() && this.f4467b != StatReportStrategy.ONLY_WIFI_NO_CACHE && C1389k.m4139g(c)) {
                        this.f4467b = StatReportStrategy.INSTANT;
                    }
                    switch (C1399h.f4462a[this.f4467b.ordinal()]) {
                        case 1:
                            m4183a();
                            return;
                        case 2:
                            if (C1389k.m4135e(c)) {
                                m4183a();
                                return;
                            } else {
                                C1405n.m4189a(c).m4209a(this.f4466a, null);
                                return;
                            }
                        case 3:
                        case 4:
                            C1405n.m4189a(c).m4209a(this.f4466a, null);
                            return;
                        case 5:
                            if (C1405n.m4189a(this.f4466a.m4045c()) != null) {
                                C1405n.m4189a(c).m4209a(this.f4466a, new C1404m(this));
                                return;
                            }
                            return;
                        case 6:
                            C1405n.m4189a(c).m4209a(this.f4466a, null);
                            String str = "last_period_ts";
                            Long valueOf = Long.valueOf(C1394p.m4167a(c, str, 0));
                            Long valueOf2 = Long.valueOf(System.currentTimeMillis());
                            if (Long.valueOf(Long.valueOf(valueOf2.longValue() - valueOf.longValue()).longValue() / 60000).longValue() > ((long) StatConfig.getSendPeriodMinutes())) {
                                C1405n.m4189a(c).m4208a(-1);
                                C1394p.m4171b(c, str, valueOf2.longValue());
                                return;
                            }
                            return;
                        case 7:
                            if (C1389k.m4135e(c)) {
                                m4184a(false);
                                return;
                            }
                            return;
                        default:
                            StatService.f4336i.error("Invalid stat strategy:" + StatConfig.getStatSendStrategy());
                            return;
                    }
                    StatService.f4336i.m4085e(th);
                }
                C1405n.m4189a(c).m4209a(this.f4466a, null);
                return;
            }
            StatService.f4336i.m4085e("Event length exceed StatConfig.getMaxReportEventLength(): " + StatConfig.getMaxReportEventLength());
        } catch (Exception e) {
            StatService.f4336i.m4084e(e);
        } catch (Object th) {
            StatService.f4336i.m4085e(th);
        }
    }
}
