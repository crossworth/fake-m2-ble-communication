package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p022a.C0873d;
import com.tencent.wxop.stat.p023b.C0885l;
import com.tencent.wxop.stat.p023b.C0890q;

final class C0907p {
    private static volatile long bU = 0;
    private C0873d bP;
    private C0895d bQ = null;
    private boolean bR = false;
    private Context bS = null;
    private long bT = System.currentTimeMillis();

    public C0907p(C0873d c0873d) {
        this.bP = c0873d;
        this.bQ = C0894c.m2948j();
        this.bR = c0873d.m2836X();
        this.bS = c0873d.m2835J();
    }

    private void m3022H() {
        if (C0908t.ai().aI <= 0 || !C0894c.ax) {
            m3024a(new C1765s(this));
            return;
        }
        C0908t.ai().m3046b(this.bP, null, this.bR, true);
        C0908t.ai().m3045b(-1);
    }

    private void m3024a(aj ajVar) {
        ak.m2844Z(C0896e.aY).m2846a(this.bP, ajVar);
    }

    public final void ah() {
        boolean z;
        long u;
        if (C0894c.ae > 0) {
            if (this.bT > C0896e.aO) {
                C0896e.aN.clear();
                C0896e.aO = this.bT + C0894c.af;
                if (C0894c.m2949k()) {
                    C0896e.aV.m2851b("clear methodsCalledLimitMap, nextLimitCallClearTime=" + C0896e.aO);
                }
            }
            Integer valueOf = Integer.valueOf(this.bP.ac().m2838r());
            Integer num = (Integer) C0896e.aN.get(valueOf);
            if (num != null) {
                C0896e.aN.put(valueOf, Integer.valueOf(num.intValue() + 1));
                if (num.intValue() > C0894c.ae) {
                    if (C0894c.m2949k()) {
                        C0896e.aV.m2854d("event " + this.bP.af() + " was discard, cause of called limit, current:" + num + ", limit:" + C0894c.ae + ", period:" + C0894c.af + " ms");
                    }
                    z = true;
                    if (z) {
                        if (C0894c.ay > 0 && this.bT >= bU) {
                            C0896e.m2999p(this.bS);
                            bU = this.bT + C0894c.az;
                            if (C0894c.m2949k()) {
                                C0896e.aV.m2851b("nextFlushTime=" + bU);
                            }
                        }
                        if (C0898g.m3012r(this.bS).m3017X()) {
                            C0908t.m3043s(this.bS).m3046b(this.bP, null, this.bR, false);
                            return;
                        }
                        if (C0894c.m2949k()) {
                            C0896e.aV.m2851b("sendFailedCount=" + C0896e.aI);
                        }
                        if (C0896e.m2986a()) {
                            if (this.bP.ae() != null && this.bP.ae().m3003R()) {
                                this.bQ = C0895d.INSTANT;
                            }
                            if (C0894c.ah && C0898g.m3012r(C0896e.aY).m3016W()) {
                                this.bQ = C0895d.INSTANT;
                            }
                            if (C0894c.m2949k()) {
                                C0896e.aV.m2851b("strategy=" + this.bQ.name());
                            }
                            switch (C0901j.bL[this.bQ.ordinal()]) {
                                case 1:
                                    m3022H();
                                    return;
                                case 2:
                                    C0908t.m3043s(this.bS).m3046b(this.bP, null, this.bR, false);
                                    if (C0894c.m2949k()) {
                                        C0896e.aV.m2851b("PERIOD currTime=" + this.bT + ",nextPeriodSendTs=" + C0896e.aZ + ",difftime=" + (C0896e.aZ - this.bT));
                                    }
                                    if (C0896e.aZ == 0) {
                                        C0896e.aZ = C0890q.m2912f(this.bS, "last_period_ts");
                                        if (this.bT > C0896e.aZ) {
                                            C0896e.m3001q(this.bS);
                                        }
                                        u = this.bT + ((long) ((C0894c.m2963u() * 60) * 1000));
                                        if (C0896e.aZ > u) {
                                            C0896e.aZ = u;
                                        }
                                        af.m2841Y(this.bS).ah();
                                    }
                                    if (C0894c.m2949k()) {
                                        C0896e.aV.m2851b("PERIOD currTime=" + this.bT + ",nextPeriodSendTs=" + C0896e.aZ + ",difftime=" + (C0896e.aZ - this.bT));
                                    }
                                    if (this.bT > C0896e.aZ) {
                                        C0896e.m3001q(this.bS);
                                        return;
                                    }
                                    return;
                                case 3:
                                case 4:
                                    C0908t.m3043s(this.bS).m3046b(this.bP, null, this.bR, false);
                                    return;
                                case 5:
                                    C0908t.m3043s(this.bS).m3046b(this.bP, new C1763q(this), this.bR, true);
                                    return;
                                case 6:
                                    if (C0898g.m3012r(C0896e.aY).m3013D() != 1) {
                                        m3022H();
                                        return;
                                    } else {
                                        C0908t.m3043s(this.bS).m3046b(this.bP, null, this.bR, false);
                                        return;
                                    }
                                case 7:
                                    if (C0885l.m2901y(this.bS)) {
                                        m3024a(new C1764r(this));
                                        return;
                                    }
                                    return;
                                default:
                                    C0896e.aV.error("Invalid stat strategy:" + C0894c.m2948j());
                                    return;
                            }
                        }
                        C0908t.m3043s(this.bS).m3046b(this.bP, null, this.bR, false);
                        if (this.bT - C0896e.aX > 1800000) {
                            C0896e.m2996n(this.bS);
                            return;
                        }
                        return;
                    }
                }
            }
            C0896e.aN.put(valueOf, Integer.valueOf(1));
        }
        z = false;
        if (z) {
            C0896e.m2999p(this.bS);
            bU = this.bT + C0894c.az;
            if (C0894c.m2949k()) {
                C0896e.aV.m2851b("nextFlushTime=" + bU);
            }
            if (C0898g.m3012r(this.bS).m3017X()) {
                C0908t.m3043s(this.bS).m3046b(this.bP, null, this.bR, false);
                return;
            }
            if (C0894c.m2949k()) {
                C0896e.aV.m2851b("sendFailedCount=" + C0896e.aI);
            }
            if (C0896e.m2986a()) {
                C0908t.m3043s(this.bS).m3046b(this.bP, null, this.bR, false);
                if (this.bT - C0896e.aX > 1800000) {
                    C0896e.m2996n(this.bS);
                    return;
                }
                return;
            }
            this.bQ = C0895d.INSTANT;
            this.bQ = C0895d.INSTANT;
            if (C0894c.m2949k()) {
                C0896e.aV.m2851b("strategy=" + this.bQ.name());
            }
            switch (C0901j.bL[this.bQ.ordinal()]) {
                case 1:
                    m3022H();
                    return;
                case 2:
                    C0908t.m3043s(this.bS).m3046b(this.bP, null, this.bR, false);
                    if (C0894c.m2949k()) {
                        C0896e.aV.m2851b("PERIOD currTime=" + this.bT + ",nextPeriodSendTs=" + C0896e.aZ + ",difftime=" + (C0896e.aZ - this.bT));
                    }
                    if (C0896e.aZ == 0) {
                        C0896e.aZ = C0890q.m2912f(this.bS, "last_period_ts");
                        if (this.bT > C0896e.aZ) {
                            C0896e.m3001q(this.bS);
                        }
                        u = this.bT + ((long) ((C0894c.m2963u() * 60) * 1000));
                        if (C0896e.aZ > u) {
                            C0896e.aZ = u;
                        }
                        af.m2841Y(this.bS).ah();
                    }
                    if (C0894c.m2949k()) {
                        C0896e.aV.m2851b("PERIOD currTime=" + this.bT + ",nextPeriodSendTs=" + C0896e.aZ + ",difftime=" + (C0896e.aZ - this.bT));
                    }
                    if (this.bT > C0896e.aZ) {
                        C0896e.m3001q(this.bS);
                        return;
                    }
                    return;
                case 3:
                case 4:
                    C0908t.m3043s(this.bS).m3046b(this.bP, null, this.bR, false);
                    return;
                case 5:
                    C0908t.m3043s(this.bS).m3046b(this.bP, new C1763q(this), this.bR, true);
                    return;
                case 6:
                    if (C0898g.m3012r(C0896e.aY).m3013D() != 1) {
                        C0908t.m3043s(this.bS).m3046b(this.bP, null, this.bR, false);
                        return;
                    } else {
                        m3022H();
                        return;
                    }
                case 7:
                    if (C0885l.m2901y(this.bS)) {
                        m3024a(new C1764r(this));
                        return;
                    }
                    return;
                default:
                    C0896e.aV.error("Invalid stat strategy:" + C0894c.m2948j());
                    return;
            }
        }
    }
}
