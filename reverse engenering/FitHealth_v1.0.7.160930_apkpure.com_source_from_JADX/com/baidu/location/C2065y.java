package com.baidu.location;

import android.os.Handler;
import android.os.Message;
import com.baidu.location.C1981n.C0529a;
import com.baidu.location.C1989z.C0544b;
import com.baidu.location.C1989z.C2066a;
import com.baidu.location.ai.C0503b;

class C2065y extends C1989z implements an, C1619j {
    private static C2065y dE = null;
    final int dD;
    private aj dF;
    private boolean dG;
    private BDLocation dH;
    public C2066a dI;
    private boolean dJ;
    private String dK;
    private long dL;
    final int dM;
    private C0529a dN;
    private boolean dO;
    private volatile boolean dP;
    private C0503b dQ;
    final Handler dy;

    private class C0543a implements Runnable {
        final /* synthetic */ C2065y f2300a;

        private C0543a(C2065y c2065y) {
            this.f2300a = c2065y;
        }

        public void run() {
            if (this.f2300a.dO) {
                this.f2300a.dO = false;
                this.f2300a.al();
            }
        }
    }

    private C2065y() {
        this.dD = 2000;
        this.dM = 1000;
        this.dJ = true;
        this.dF = null;
        this.dI = null;
        this.dK = null;
        this.dH = null;
        this.dQ = null;
        this.dN = null;
        this.dG = true;
        this.dP = false;
        this.dO = false;
        this.dL = 0;
        this.dy = new C0544b(this);
        this.dF = new aj();
        this.dI = new C2066a(this);
    }

    private boolean ac() {
        this.dC = ai.bb().ba();
        return !this.dF.mo3051do(this.dC);
    }

    private void ad() {
        this.dP = false;
        ak();
    }

    public static C2065y ag() {
        if (dE == null) {
            dE = new C2065y();
        }
        return dE;
    }

    private void ak() {
        if (this.dH != null) {
            C1979l.m5994u().m5999z();
        }
    }

    private void al() {
        if (!this.dP) {
            if (System.currentTimeMillis() - this.dL >= 1000 || this.dH == null) {
                C1974b.m5914do(an.f2222l, "start network locating ...");
                this.dP = true;
                this.dJ = m6279if(this.dN);
                if (ac() || this.dJ || this.dH == null) {
                    String str = m6095void(null);
                    if (str == null) {
                        BDLocation bDLocation = new BDLocation();
                        bDLocation.setLocType(62);
                        C1977g.m5942g().m5947do(bDLocation);
                        ad();
                        return;
                    }
                    if (this.dK != null) {
                        str = str + this.dK;
                        this.dK = null;
                    }
                    this.dI.m6286long(str);
                    this.dN = this.dz;
                    this.dQ = this.dC;
                    if (this.dG) {
                        this.dG = false;
                    }
                    this.dL = System.currentTimeMillis();
                    return;
                }
                C1977g.m5942g().m5947do(this.dH);
                ad();
                return;
            }
            C1977g.m5942g().m5947do(this.dH);
            ad();
        }
    }

    private void m6275char(Message message) {
        C1974b.m5914do(an.f2222l, "on request location ...");
        if (C1977g.m5942g().m5950for(message) == 1 && C1984s.aH().ay()) {
            String aw = C1984s.aH().aw();
            C1977g.m5942g().m5954if(new BDLocation(aw), message);
            ab.m2123a().m2131a(null);
            ab.m2123a().m2134if(aw);
        } else if (this.dG) {
            al();
        } else if (!this.dP) {
            if (ai.bb().a8()) {
                this.dO = true;
                this.dy.postDelayed(new C0543a(), 2000);
                return;
            }
            al();
        }
    }

    private boolean m6278if(C0503b c0503b) {
        this.dC = ai.bb().ba();
        return c0503b == this.dC ? false : this.dC == null || c0503b == null || !c0503b.m2148a(this.dC);
    }

    private boolean m6279if(C0529a c0529a) {
        this.dz = C1981n.m6008K().m6018H();
        return this.dz == c0529a ? false : this.dz == null || c0529a == null || !c0529a.m2200a(this.dz);
    }

    void ab() {
        C1974b.m5914do(an.f2222l, "on network exception");
        this.dH = null;
        this.dF.bh();
        C1977g.m5942g().m5953if(ao.bz().m5889new(false), 21);
        ad();
    }

    public void ae() {
        if (this.dO) {
            al();
            this.dO = false;
        }
    }

    void af() {
        this.dP = false;
        ah();
    }

    void ah() {
        this.dH = null;
        this.dF.bh();
    }

    public boolean ai() {
        return this.dJ;
    }

    void aj() {
        this.dG = true;
        this.dP = false;
    }

    void mo3707byte(Message message) {
        C1974b.m5914do(an.f2222l, "on network success");
        BDLocation bDLocation = (BDLocation) message.obj;
        Object obj = null;
        if (this.dF.mo3052new(bDLocation) == 3) {
            obj = 1;
        }
        if (obj != null) {
            C1977g.m5942g().m5953if(this.dH, 21);
        } else {
            C1977g.m5942g().m5953if(bDLocation, 21);
        }
        if (!C1974b.m5923if(bDLocation)) {
            this.dH = null;
            this.dF.bh();
        } else if (obj == null) {
            this.dH = bDLocation;
        }
        int i = C1974b.m5911do(dw, "ssid\":\"", "\"");
        if (i == Integer.MIN_VALUE || this.dQ == null) {
            this.dK = null;
        } else {
            this.dK = this.dQ.m2157if(i);
        }
        ao.bz().m5888if(dw, this.dN);
        ad();
    }

    public void m6282case(Message message) {
        m6275char(message);
    }

    public void m6283for(BDLocation bDLocation) {
        ah();
        this.dH = bDLocation;
    }
}
