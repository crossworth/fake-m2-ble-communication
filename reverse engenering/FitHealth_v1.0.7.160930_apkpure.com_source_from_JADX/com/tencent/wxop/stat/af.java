package com.tencent.wxop.stat;

import android.content.Context;
import com.tencent.wxop.stat.p023b.C0885l;
import java.util.Timer;
import java.util.TimerTask;

public class af {
    private static volatile af dd = null;
    private Timer dc = null;
    private Context f3012h = null;

    private af(Context context) {
        this.f3012h = context.getApplicationContext();
        this.dc = new Timer(false);
    }

    public static af m2841Y(Context context) {
        if (dd == null) {
            synchronized (af.class) {
                if (dd == null) {
                    dd = new af(context);
                }
            }
        }
        return dd;
    }

    public final void ah() {
        if (C0894c.m2948j() == C0895d.PERIOD) {
            long u = (long) ((C0894c.m2963u() * 60) * 1000);
            if (C0894c.m2949k()) {
                C0885l.av().m2851b("setupPeriodTimer delay:" + u);
            }
            TimerTask agVar = new ag(this);
            if (this.dc != null) {
                if (C0894c.m2949k()) {
                    C0885l.av().m2851b("setupPeriodTimer schedule delay:" + u);
                }
                this.dc.schedule(agVar, u);
            } else if (C0894c.m2949k()) {
                C0885l.av().m2853c("setupPeriodTimer schedule timer == null");
            }
        }
    }
}
