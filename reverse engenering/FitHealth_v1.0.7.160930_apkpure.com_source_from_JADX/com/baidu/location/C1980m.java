package com.baidu.location;

import android.os.Handler;
import android.os.Message;

class C1980m implements an, C1619j {
    private static C1980m cr = null;
    private C1978h cq;
    private Handler cs;

    public class C0528a extends Handler {
        final /* synthetic */ C1980m f2268a;

        public C0528a(C1980m c1980m) {
            this.f2268a = c1980m;
        }

        public void handleMessage(Message message) {
            if (C1976f.isServing) {
                switch (message.what) {
                    case 92:
                        this.f2268a.m6001F();
                        break;
                }
                super.handleMessage(message);
            }
        }
    }

    private C1980m() {
        this.cq = null;
        this.cs = null;
        this.cs = new C0528a(this);
    }

    public static C1980m m6000D() {
        if (cr == null) {
            cr = new C1980m();
        }
        return cr;
    }

    private void m6001F() {
        try {
            if (C1985t.fb && C1974b.aY) {
                this.cq = new C1978h(C1976f.getServiceContext());
            }
        } catch (Exception e) {
        }
    }

    public void m6003B() {
    }

    public void m6004C() {
        if (this.cq != null) {
            this.cq.m5970q();
        }
        this.cq = null;
    }

    public Handler m6005E() {
        return this.cs;
    }

    public void m6006G() {
    }
}
