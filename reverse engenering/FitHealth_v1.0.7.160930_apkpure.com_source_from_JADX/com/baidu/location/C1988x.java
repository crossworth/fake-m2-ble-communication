package com.baidu.location;

import android.os.Handler;
import android.os.Message;
import java.util.ArrayList;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

class C1988x implements C1619j, an {
    private static C1988x fu;
    private long fA;
    private aa fB;
    private volatile boolean fC;
    private int fD;
    private long fE;
    public C0541c fF;
    private boolean fv;
    private long fw;
    private C2064b fx;
    private C0540a fy;
    private boolean fz;

    class C0540a extends Thread {
        final /* synthetic */ C1988x f2296a;
        private volatile boolean f2297do = true;
        private long f2298if = 0;

        C0540a(C1988x c1988x) {
            this.f2296a = c1988x;
        }

        public void run() {
            while (this.f2297do) {
                C1974b.m5922if("IndoorSdk", "mm:" + this.f2296a.fB.a0());
                if ((((this.f2296a.fC && System.currentTimeMillis() - this.f2298if > this.f2296a.fA) || System.currentTimeMillis() - this.f2298if > 10000) && this.f2296a.fB.a0() == 1) || System.currentTimeMillis() - this.f2298if > 17500) {
                    ai.bb().bc();
                    this.f2296a.fB.aZ();
                    this.f2298if = System.currentTimeMillis();
                    this.f2296a.fC = false;
                }
                if (System.currentTimeMillis() - this.f2296a.fw > 22000) {
                    this.f2296a.fF.sendEmptyMessage(41);
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    this.f2297do = false;
                    return;
                }
            }
        }
    }

    public class C0541c extends Handler {
        final /* synthetic */ C1988x f2299a;

        public C0541c(C1988x c1988x) {
            this.f2299a = c1988x;
        }

        public void handleMessage(Message message) {
            if (C1976f.isServing) {
                switch (message.what) {
                    case 21:
                        this.f2299a.m6092long(message);
                        return;
                    case 41:
                        this.f2299a.aT();
                        return;
                    default:
                        super.dispatchMessage(message);
                        return;
                }
            }
        }
    }

    class C2064b extends C1982o {
        private String c0;
        private boolean c1;
        private boolean c2;
        private String c3;
        final /* synthetic */ C1988x cZ;

        public C2064b(C1988x c1988x) {
            this.cZ = c1988x;
            this.c2 = false;
            this.c1 = false;
            this.c0 = null;
            this.c3 = null;
            this.cP = new ArrayList();
        }

        synchronized void mo3704V() {
            this.cL = C1974b.m5924int();
            this.cO = 1;
            String f = Jni.m5811f(this.c0);
            ab.m2123a().m2131a(f);
            this.c0 = null;
            this.cP.add(new BasicNameValuePair("bloc", f));
        }

        public synchronized void m6272X() {
            if (!this.c2) {
                if (this.c1) {
                    this.c1 = false;
                    m6273Y();
                }
            }
        }

        public void m6273Y() {
            if (this.c2) {
                this.c1 = true;
                return;
            }
            this.c2 = true;
            StringBuffer stringBuffer = new StringBuffer(1024);
            String a = ai.bb().be().m2147a(32);
            if (a != null && a.length() >= 10) {
                if (this.c3 == null || !this.c3.equals(a)) {
                    this.c3 = a;
                    stringBuffer.append(a);
                    stringBuffer.append("&coor=gcj02");
                    stringBuffer.append("&rt=2");
                    stringBuffer.append(ap.bD().m5891try(false));
                    this.c0 = stringBuffer.toString();
                    m6032R();
                }
            }
        }

        void mo3705if(boolean z) {
            if (z && this.cM != null) {
                try {
                    String entityUtils = EntityUtils.toString(this.cM, "utf-8");
                    ab.m2123a().m2134if(entityUtils);
                    BDLocation bDLocation = new BDLocation(entityUtils);
                    Message obtainMessage = this.cZ.fF.obtainMessage(21);
                    obtainMessage.obj = bDLocation;
                    obtainMessage.sendToTarget();
                } catch (Exception e) {
                }
            }
            if (this.cP != null) {
                this.cP.clear();
            }
            this.c2 = false;
        }
    }

    private C1988x() {
        this.fA = 3000;
        this.fC = true;
        this.fF = null;
        this.fB = null;
        this.fy = null;
        this.fx = null;
        this.fw = 0;
        this.fz = false;
        this.fv = false;
        this.fE = 0;
        this.fD = 0;
        this.fF = new C0541c(this);
        this.fx = new C2064b(this);
    }

    private void aT() {
        if (this.fz) {
            this.fC = true;
            this.fx.m6273Y();
            this.fw = System.currentTimeMillis();
        }
    }

    public static C1988x aU() {
        if (fu == null) {
            fu = new C1988x();
        }
        return fu;
    }

    private void m6092long(Message message) {
        BDLocation bDLocation = (BDLocation) message.obj;
        if (bDLocation.getLocType() == BDLocation.TypeNetWorkLocation) {
            if (bDLocation.getFloor() == null) {
                this.fv = false;
            } else {
                this.fv = true;
            }
            this.fD = 0;
            C2065y.ag().m6283for(bDLocation);
        } else if (bDLocation.getLocType() == 63) {
            this.fD++;
            if (this.fD > 3) {
                this.fv = false;
            } else {
                return;
            }
        } else {
            this.fv = false;
        }
        if (this.fv || !C1984s.aH().ay()) {
            C1977g.m5942g().m5953if(bDLocation, 21);
        }
        this.fE = System.currentTimeMillis();
        this.fx.m6272X();
    }

    public synchronized void aV() {
        if (!this.fz) {
            this.fB = new aa(C1976f.getServiceContext());
            this.fB.a2();
            this.fy = new C0540a(this);
            this.fy.start();
            this.fv = false;
            this.fz = true;
        }
    }

    public boolean aW() {
        return this.fz && this.fv;
    }

    public synchronized void aX() {
        if (this.fB != null) {
            this.fB.a3();
            this.fB = null;
        }
        if (this.fy != null) {
            this.fy.f2297do = false;
            this.fy.interrupt();
            this.fy = null;
        }
        this.fv = false;
        this.fz = false;
    }

    public boolean aY() {
        return this.fz;
    }
}
