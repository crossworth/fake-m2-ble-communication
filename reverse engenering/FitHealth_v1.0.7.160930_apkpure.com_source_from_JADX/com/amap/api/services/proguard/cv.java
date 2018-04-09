package com.amap.api.services.proguard;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.net.Proxy;

/* compiled from: NetManger */
public class cv extends cq {
    private static cv f4381a;
    private da f4382b;
    private Handler f4383c;

    /* compiled from: NetManger */
    static class C0387a extends Handler {
        private C0387a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 0:
                        ((cz) message.obj).f1533b.m1575a();
                        return;
                    case 1:
                        cz czVar = (cz) message.obj;
                        czVar.f1533b.m1576a(czVar.f1532a);
                        return;
                    default:
                        return;
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            th.printStackTrace();
        }
    }

    /* compiled from: NetManger */
    class C16111 extends db {
        final /* synthetic */ cw f4378a;
        final /* synthetic */ cx f4379b;
        final /* synthetic */ cv f4380c;

        public void mo1776a() {
            try {
                this.f4380c.m4498a(this.f4380c.m4499b(this.f4378a, false), this.f4379b);
            } catch (ar e) {
                this.f4380c.m4495a(e, this.f4379b);
            }
        }
    }

    public static cv m4493a(boolean z) {
        return m4494a(z, 5);
    }

    private static synchronized cv m4494a(boolean z, int i) {
        cv cvVar;
        synchronized (cv.class) {
            try {
                if (f4381a == null) {
                    f4381a = new cv(z, i);
                } else if (z) {
                    if (f4381a.f4382b == null) {
                        f4381a.f4382b = da.m1578a(i);
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            cvVar = f4381a;
        }
        return cvVar;
    }

    private cv(boolean z, int i) {
        if (z) {
            try {
                this.f4382b = da.m1578a(i);
            } catch (Throwable th) {
                bh.m4438b(th, "NetManger", "NetManger1");
                th.printStackTrace();
                return;
            }
        }
        if (Looper.myLooper() == null) {
            this.f4383c = new C0387a(Looper.getMainLooper());
        } else {
            this.f4383c = new C0387a();
        }
    }

    public byte[] mo1777b(cw cwVar) throws ar {
        ar e;
        try {
            cy a = m1546a(cwVar, false);
            if (a != null) {
                return a.f1530a;
            }
            return null;
        } catch (ar e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            bh.m4435a().m4442c(th, "NetManager", "makeSyncPostRequest");
            e2 = new ar("未知的错误");
        }
    }

    public byte[] m4501d(cw cwVar) throws ar {
        ar e;
        try {
            cy b = m4499b(cwVar, false);
            if (b != null) {
                return b.f1530a;
            }
            return null;
        } catch (ar e2) {
            throw e2;
        } catch (Throwable th) {
            e2 = new ar("未知的错误");
        }
    }

    public cy m4499b(cw cwVar, boolean z) throws ar {
        ar e;
        try {
            Proxy proxy;
            m1549c(cwVar);
            if (cwVar.f1529g == null) {
                proxy = null;
            } else {
                proxy = cwVar.f1529g;
            }
            return new ct(cwVar.f1527e, cwVar.f1528f, proxy, z).m1559a(cwVar.mo1759g(), cwVar.mo1757c(), cwVar.mo1756b());
        } catch (ar e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            e2 = new ar("未知的错误");
        }
    }

    private void m4495a(ar arVar, cx cxVar) {
        cz czVar = new cz();
        czVar.f1532a = arVar;
        czVar.f1533b = cxVar;
        Message obtain = Message.obtain();
        obtain.obj = czVar;
        obtain.what = 1;
        this.f4383c.sendMessage(obtain);
    }

    private void m4498a(cy cyVar, cx cxVar) {
        cxVar.m1577a(cyVar.f1531b, cyVar.f1530a);
        cz czVar = new cz();
        czVar.f1533b = cxVar;
        Message obtain = Message.obtain();
        obtain.obj = czVar;
        obtain.what = 0;
        this.f4383c.sendMessage(obtain);
    }
}
