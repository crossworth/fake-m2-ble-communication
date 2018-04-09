package com.amap.api.mapcore.util;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import java.net.Proxy;

/* compiled from: NetManger */
public class fv extends fq {
    private static fv f4216a;
    private ga f4217b;
    private Handler f4218c;

    /* compiled from: NetManger */
    static class C0264a extends Handler {
        private C0264a(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            try {
                switch (message.what) {
                    case 0:
                        ((fz) message.obj).f672b.m979a();
                        return;
                    case 1:
                        fz fzVar = (fz) message.obj;
                        fzVar.f672b.m980a(fzVar.f671a);
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
    class C16021 extends gc {
        final /* synthetic */ fw f4213a;
        final /* synthetic */ fx f4214b;
        final /* synthetic */ fv f4215c;

        public void mo1473a() {
            try {
                this.f4215c.m4295a(this.f4215c.m4296b(this.f4213a, false), this.f4214b);
            } catch (dk e) {
                this.f4215c.m4292a(e, this.f4214b);
            }
        }
    }

    public static fv m4290a(boolean z) {
        return m4291a(z, 5);
    }

    private static synchronized fv m4291a(boolean z, int i) {
        fv fvVar;
        synchronized (fv.class) {
            try {
                if (f4216a == null) {
                    f4216a = new fv(z, i);
                } else if (z) {
                    if (f4216a.f4217b == null) {
                        f4216a.f4217b = ga.m982a(i);
                    }
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            fvVar = f4216a;
        }
        return fvVar;
    }

    private fv(boolean z, int i) {
        if (z) {
            try {
                this.f4217b = ga.m982a(i);
            } catch (Throwable th) {
                ee.m4243a(th, "NetManger", "NetManger1");
                th.printStackTrace();
                return;
            }
        }
        if (Looper.myLooper() == null) {
            this.f4218c = new C0264a(Looper.getMainLooper());
        } else {
            this.f4218c = new C0264a();
        }
    }

    public byte[] mo1651b(fw fwVar) throws dk {
        dk e;
        try {
            fy a = m949a(fwVar, false);
            if (a != null) {
                return a.f669a;
            }
            return null;
        } catch (dk e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            ee.m4241a().m4249b(th, "NetManager", "makeSyncPostRequest");
            e2 = new dk("未知的错误");
        }
    }

    public byte[] m4298d(fw fwVar) throws dk {
        dk e;
        try {
            fy b = m4296b(fwVar, false);
            if (b != null) {
                return b.f669a;
            }
            return null;
        } catch (dk e2) {
            throw e2;
        } catch (Throwable th) {
            e2 = new dk("未知的错误");
        }
    }

    public fy m4296b(fw fwVar, boolean z) throws dk {
        dk e;
        try {
            Proxy proxy;
            m952c(fwVar);
            if (fwVar.f668i == null) {
                proxy = null;
            } else {
                proxy = fwVar.f668i;
            }
            return new fs(fwVar.f666g, fwVar.f667h, proxy, z).m963a(fwVar.mo1630a(), fwVar.mo1632c(), fwVar.mo1631b());
        } catch (dk e2) {
            throw e2;
        } catch (Throwable th) {
            th.printStackTrace();
            e2 = new dk("未知的错误");
        }
    }

    private void m4292a(dk dkVar, fx fxVar) {
        fz fzVar = new fz();
        fzVar.f671a = dkVar;
        fzVar.f672b = fxVar;
        Message obtain = Message.obtain();
        obtain.obj = fzVar;
        obtain.what = 1;
        this.f4218c.sendMessage(obtain);
    }

    private void m4295a(fy fyVar, fx fxVar) {
        fxVar.m981a(fyVar.f670b, fyVar.f669a);
        fz fzVar = new fz();
        fzVar.f672b = fxVar;
        Message obtain = Message.obtain();
        obtain.obj = fzVar;
        obtain.what = 0;
        this.f4218c.sendMessage(obtain);
    }
}
