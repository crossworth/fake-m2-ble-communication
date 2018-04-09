package com.amap.api.services.proguard;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import com.amap.api.services.proguard.at.C0364a;
import com.amap.api.services.proguard.at.C0364a.C0363c;

/* compiled from: ManifestConfig */
public class C0394o {
    public static ba f1543a;
    private static C0394o f1544b;
    private static Context f1545c;
    private C0393a f1546d;
    private HandlerThread f1547e = new HandlerThread(this, "manifestThread") {
        final /* synthetic */ C0394o f1540a;

        public void run() {
            String str = "run";
            Thread.currentThread().setName("ManifestConfigThread");
            Message message = new Message();
            try {
                C0364a a = at.m1217a(C0394o.f1545c, C0389h.m1584a(false), "11K;001", null);
                if (!(a == null || a.f1340l == null)) {
                    message.obj = new C0395p(a.f1340l.f1323b, a.f1340l.f1322a);
                }
                if (!(a == null || a.f1341m == null)) {
                    C0363c c0363c = a.f1341m;
                    if (c0363c != null) {
                        Object obj = c0363c.f1327b;
                        Object obj2 = c0363c.f1326a;
                        Object obj3 = c0363c.f1328c;
                        if (TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2) || TextUtils.isEmpty(obj3)) {
                            new cb(C0394o.f1545c, null, C0389h.m1584a(false)).m4464a();
                        } else {
                            new cb(C0394o.f1545c, new cc(obj2, obj, obj3), C0389h.m1584a(false)).m4464a();
                        }
                    } else {
                        new cb(C0394o.f1545c, null, C0389h.m1584a(false)).m4464a();
                    }
                }
                message.what = 3;
                if (this.f1540a.f1546d != null) {
                    this.f1540a.f1546d.sendMessage(message);
                }
            } catch (Throwable th) {
                message.what = 3;
                if (this.f1540a.f1546d != null) {
                    this.f1540a.f1546d.sendMessage(message);
                }
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    /* compiled from: ManifestConfig */
    class C0393a extends Handler {
        String f1541a = "handleMessage";
        final /* synthetic */ C0394o f1542b;

        public C0393a(C0394o c0394o, Looper looper) {
            this.f1542b = c0394o;
            super(looper);
        }

        public void handleMessage(Message message) {
            if (message != null) {
                switch (message.what) {
                    case 3:
                        try {
                            C0395p c0395p = (C0395p) message.obj;
                            if (c0395p == null) {
                                c0395p = new C0395p(false, false);
                            }
                            bh.m4436a(C0394o.f1545c, C0389h.m1584a(c0395p.m1653a()));
                            C0394o.f1543a = C0389h.m1584a(c0395p.m1653a());
                            return;
                        } catch (Throwable th) {
                            C0390i.m1594a(th, "ManifestConfig", this.f1541a);
                            return;
                        }
                    default:
                        return;
                }
            }
        }
    }

    private C0394o(Context context) {
        f1545c = context;
        f1543a = C0389h.m1584a(false);
        try {
            this.f1546d = new C0393a(this, Looper.getMainLooper());
            this.f1547e.start();
        } catch (Throwable th) {
            C0390i.m1594a(th, "ManifestConfig", "ManifestConfig");
        }
    }

    public static C0394o m1652a(Context context) {
        if (f1544b == null) {
            f1544b = new C0394o(context);
        }
        return f1544b;
    }
}
