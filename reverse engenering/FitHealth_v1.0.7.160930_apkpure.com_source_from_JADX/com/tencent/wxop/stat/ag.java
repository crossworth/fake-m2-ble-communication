package com.tencent.wxop.stat;

import com.tencent.wxop.stat.p023b.C0885l;
import java.util.TimerTask;

final class ag extends TimerTask {
    final /* synthetic */ af de;

    ag(af afVar) {
        this.de = afVar;
    }

    public final void run() {
        if (C0894c.m2949k()) {
            C0885l.av().m2851b((Object) "TimerTask run");
        }
        C0896e.m3001q(this.de.f3012h);
        cancel();
        this.de.ah();
    }
}
