package com.baidu.location;

import android.os.Message;
import com.baidu.location.C1989z.C2066a;

class C2062p extends C1989z implements an, C1619j {
    private static C2062p dS = null;
    static final int dT = 3000;
    private long dU;
    private BDLocation dV;
    public C2066a dW;

    private C2062p() {
        this.dV = null;
        this.dU = 0;
        this.dW = null;
        this.dW = new C2066a(this);
    }

    private void am() {
        C1979l.m5994u().m5999z();
    }

    public static C2062p an() {
        if (dS == null) {
            dS = new C2062p();
        }
        return dS;
    }

    private void m6265goto(Message message) {
        if (System.currentTimeMillis() - this.dU >= 3000 || this.dV == null) {
            this.dW.m6286long(m6095void(C1977g.m5942g().m5952if(message)));
            this.dU = System.currentTimeMillis();
            return;
        }
        C1977g.m5942g().m5953if(this.dV, 26);
    }

    void ab() {
        C1974b.m5914do(an.f2222l, "on network exception");
        this.dV = null;
        C1977g.m5942g().m5953if(ao.bz().m5889new(false), 26);
        am();
    }

    void mo3707byte(Message message) {
        C1974b.m5914do(an.f2222l, "on network success");
        BDLocation bDLocation = (BDLocation) message.obj;
        C1977g.m5942g().m5953if(bDLocation, 26);
        if (C1974b.m5923if(bDLocation)) {
            this.dV = bDLocation;
        } else {
            this.dV = null;
        }
        am();
    }

    public void m6267else(Message message) {
        m6265goto(message);
    }
}
