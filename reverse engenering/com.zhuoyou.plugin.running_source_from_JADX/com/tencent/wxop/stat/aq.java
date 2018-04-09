package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.common.C1442k;
import com.tencent.wxop.stat.common.C1447p;
import com.tencent.wxop.stat.p040a.C1416e;

class aq {
    private static volatile long f4680f = 0;
    private C1416e f4681a;
    private StatReportStrategy f4682b = null;
    private boolean f4683c = false;
    private Context f4684d = null;
    private long f4685e = System.currentTimeMillis();

    public aq(C1416e c1416e) {
        this.f4681a = c1416e;
        this.f4682b = StatConfig.getStatSendStrategy();
        this.f4683c = c1416e.m4272f();
        this.f4684d = c1416e.m4271e();
    }

    private void m4317a(C1429h c1429h) {
        C1454i.m4486b(StatServiceImpl.f4584t).m4487a(this.f4681a, c1429h);
    }

    private void m4319b() {
        if (this.f4681a.m4270d() != null && this.f4681a.m4270d().isSendImmediately()) {
            this.f4682b = StatReportStrategy.INSTANT;
        }
        if (StatConfig.f4543j && C1428a.m4298a(StatServiceImpl.f4584t).m4310e()) {
            this.f4682b = StatReportStrategy.INSTANT;
        }
        if (StatConfig.isDebugEnable()) {
            StatServiceImpl.f4581q.m4376i("strategy=" + this.f4682b.name());
        }
        switch (ag.f4658a[this.f4682b.ordinal()]) {
            case 1:
                m4320c();
                return;
            case 2:
                au.m4332a(this.f4684d).m4361a(this.f4681a, null, this.f4683c, false);
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.f4581q.m4376i("PERIOD currTime=" + this.f4685e + ",nextPeriodSendTs=" + StatServiceImpl.f4567c + ",difftime=" + (StatServiceImpl.f4567c - this.f4685e));
                }
                if (StatServiceImpl.f4567c == 0) {
                    StatServiceImpl.f4567c = C1447p.m4455a(this.f4684d, "last_period_ts", 0);
                    if (this.f4685e > StatServiceImpl.f4567c) {
                        StatServiceImpl.m4257e(this.f4684d);
                    }
                    long sendPeriodMinutes = this.f4685e + ((long) ((StatConfig.getSendPeriodMinutes() * 60) * 1000));
                    if (StatServiceImpl.f4567c > sendPeriodMinutes) {
                        StatServiceImpl.f4567c = sendPeriodMinutes;
                    }
                    C1450d.m4474a(this.f4684d).m4475a();
                }
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.f4581q.m4376i("PERIOD currTime=" + this.f4685e + ",nextPeriodSendTs=" + StatServiceImpl.f4567c + ",difftime=" + (StatServiceImpl.f4567c - this.f4685e));
                }
                if (this.f4685e > StatServiceImpl.f4567c) {
                    StatServiceImpl.m4257e(this.f4684d);
                    return;
                }
                return;
            case 3:
            case 4:
                au.m4332a(this.f4684d).m4361a(this.f4681a, null, this.f4683c, false);
                return;
            case 5:
                au.m4332a(this.f4684d).m4361a(this.f4681a, new ar(this), this.f4683c, true);
                return;
            case 6:
                if (C1428a.m4298a(StatServiceImpl.f4584t).m4308c() == 1) {
                    m4320c();
                    return;
                } else {
                    au.m4332a(this.f4684d).m4361a(this.f4681a, null, this.f4683c, false);
                    return;
                }
            case 7:
                if (C1442k.m4424e(this.f4684d)) {
                    m4317a(new as(this));
                    return;
                }
                return;
            default:
                StatServiceImpl.f4581q.error("Invalid stat strategy:" + StatConfig.getStatSendStrategy());
                return;
        }
    }

    private void m4320c() {
        if (au.m4346b().f4692a <= 0 || !StatConfig.f4545l) {
            m4317a(new at(this));
            return;
        }
        au.m4346b().m4361a(this.f4681a, null, this.f4683c, true);
        au.m4346b().m4360a(-1);
    }

    private boolean m4322d() {
        if (StatConfig.f4541h > 0) {
            if (this.f4685e > StatServiceImpl.f4572h) {
                StatServiceImpl.f4571g.clear();
                StatServiceImpl.f4572h = this.f4685e + StatConfig.f4542i;
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.f4581q.m4376i("clear methodsCalledLimitMap, nextLimitCallClearTime=" + StatServiceImpl.f4572h);
                }
            }
            Integer valueOf = Integer.valueOf(this.f4681a.mo2223a().m4284a());
            Integer num = (Integer) StatServiceImpl.f4571g.get(valueOf);
            if (num != null) {
                StatServiceImpl.f4571g.put(valueOf, Integer.valueOf(num.intValue() + 1));
                if (num.intValue() > StatConfig.f4541h) {
                    if (StatConfig.isDebugEnable()) {
                        StatServiceImpl.f4581q.m4374e("event " + this.f4681a.m4273g() + " was discard, cause of called limit, current:" + num + ", limit:" + StatConfig.f4541h + ", period:" + StatConfig.f4542i + " ms");
                    }
                    return true;
                }
            }
            StatServiceImpl.f4571g.put(valueOf, Integer.valueOf(1));
        }
        return false;
    }

    public void m4323a() {
        if (!m4322d()) {
            if (StatConfig.f4546m > 0 && this.f4685e >= f4680f) {
                StatServiceImpl.flushDataToDB(this.f4684d);
                f4680f = this.f4685e + StatConfig.f4547n;
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.f4581q.m4376i("nextFlushTime=" + f4680f);
                }
            }
            if (C1428a.m4298a(this.f4684d).m4311f()) {
                if (StatConfig.isDebugEnable()) {
                    StatServiceImpl.f4581q.m4376i("sendFailedCount=" + StatServiceImpl.f4565a);
                }
                if (StatServiceImpl.m4245a()) {
                    au.m4332a(this.f4684d).m4361a(this.f4681a, null, this.f4683c, false);
                    if (this.f4685e - StatServiceImpl.f4566b > 1800000) {
                        StatServiceImpl.m4255d(this.f4684d);
                        return;
                    }
                    return;
                }
                m4319b();
                return;
            }
            au.m4332a(this.f4684d).m4361a(this.f4681a, null, this.f4683c, false);
        }
    }
}
