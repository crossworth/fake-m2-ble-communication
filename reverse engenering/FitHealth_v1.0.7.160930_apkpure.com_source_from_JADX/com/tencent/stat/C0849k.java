package com.tencent.stat;

import android.content.Context;
import com.tencent.stat.common.C0837k;
import com.tencent.stat.common.C0842p;
import com.tencent.stat.p021a.C0824e;
import com.tencent.stat.p021a.C0825f;
import com.zhuoyou.plugin.download.Util_update.TimeManager;

class C0849k implements Runnable {
    private C0824e f2930a;
    private StatReportStrategy f2931b = null;
    private C0828c f2932c = new C1746l(this);

    public C0849k(C0824e c0824e) {
        this.f2930a = c0824e;
        this.f2931b = StatConfig.getStatSendStrategy();
    }

    private void m2776a() {
        if (C0850n.m2785b().m2796a() > 0) {
            C0850n.m2785b().m2798a(this.f2930a, null);
            C0850n.m2785b().m2797a(-1);
            return;
        }
        m2777a(true);
    }

    private void m2777a(boolean z) {
        C0843d.m2768b().m2770a(this.f2930a, this.f2932c);
    }

    public void run() {
        try {
            if (!StatConfig.isEnableStatService()) {
                return;
            }
            if (this.f2930a.mo2142a() == C0825f.ERROR || this.f2930a.m2662d().length() <= StatConfig.getMaxReportEventLength()) {
                if (StatConfig.getMaxSessionStatReportCount() > 0) {
                    if (StatConfig.getCurSessionStatReportCount() >= StatConfig.getMaxSessionStatReportCount()) {
                        StatService.f2834i.m2680e((Object) "Times for reporting events has reached the limit of StatConfig.getMaxSessionStatReportCount() in current session.");
                        return;
                    }
                    StatConfig.m2633c();
                }
                StatService.f2834i.m2681i("Lauch stat task in thread:" + Thread.currentThread().getName());
                Context c = this.f2930a.m2661c();
                if (C0837k.m2734h(c)) {
                    if (StatConfig.isEnableSmartReporting() && this.f2931b != StatReportStrategy.ONLY_WIFI_NO_CACHE && C0837k.m2732g(c)) {
                        this.f2931b = StatReportStrategy.INSTANT;
                    }
                    switch (C0846h.f2926a[this.f2931b.ordinal()]) {
                        case 1:
                            m2776a();
                            return;
                        case 2:
                            if (C0837k.m2728e(c)) {
                                m2776a();
                                return;
                            } else {
                                C0850n.m2778a(c).m2798a(this.f2930a, null);
                                return;
                            }
                        case 3:
                        case 4:
                            C0850n.m2778a(c).m2798a(this.f2930a, null);
                            return;
                        case 5:
                            if (C0850n.m2778a(this.f2930a.m2661c()) != null) {
                                C0850n.m2778a(c).m2798a(this.f2930a, new C1747m(this));
                                return;
                            }
                            return;
                        case 6:
                            C0850n.m2778a(c).m2798a(this.f2930a, null);
                            String str = "last_period_ts";
                            Long valueOf = Long.valueOf(C0842p.m2760a(c, str, 0));
                            Long valueOf2 = Long.valueOf(System.currentTimeMillis());
                            if (Long.valueOf(Long.valueOf(valueOf2.longValue() - valueOf.longValue()).longValue() / TimeManager.UNIT_MINUTE).longValue() > ((long) StatConfig.getSendPeriodMinutes())) {
                                C0850n.m2778a(c).m2797a(-1);
                                C0842p.m2764b(c, str, valueOf2.longValue());
                                return;
                            }
                            return;
                        case 7:
                            if (C0837k.m2728e(c)) {
                                m2777a(false);
                                return;
                            }
                            return;
                        default:
                            StatService.f2834i.error("Invalid stat strategy:" + StatConfig.getStatSendStrategy());
                            return;
                    }
                    StatService.f2834i.m2680e(th);
                }
                C0850n.m2778a(c).m2798a(this.f2930a, null);
                return;
            }
            StatService.f2834i.m2680e("Event length exceed StatConfig.getMaxReportEventLength(): " + StatConfig.getMaxReportEventLength());
        } catch (Exception e) {
            StatService.f2834i.m2679e(e);
        } catch (Object th) {
            StatService.f2834i.m2680e(th);
        }
    }
}
