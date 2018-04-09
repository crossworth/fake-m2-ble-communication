package com.amap.api.mapcore.util;

import android.text.TextUtils;
import com.amap.api.mapcore.util.dp.C0246a;
import com.amap.api.mapcore.util.dp.C0246a.C0245c;
import com.amap.api.mapcore.util.dv.C0248a;
import com.amap.api.maps.MapsInitializer;

/* compiled from: AMapDelegateImp */
class C0239d extends Thread {
    final /* synthetic */ C1592c f418a;

    C0239d(C1592c c1592c) {
        this.f418a = c1592c;
    }

    public void run() {
        try {
            if (MapsInitializer.getNetWorkEnable()) {
                dv a = new C0248a(C0273r.f695b, "3.3.2", C0273r.f697d).m700a(new String[]{"com.amap.api.maps", "com.amap.api.mapcore", "com.autonavi.amap.mapcore"}).m701a();
                if (!TextUtils.isEmpty(MapsInitializer.KEY)) {
                    dm.m611a(MapsInitializer.KEY);
                }
                dm.m612a(this.f418a.f4094H, a);
                if (dm.f465a == 0) {
                    this.f418a.f4124l.sendEmptyMessage(2);
                }
                C0246a a2 = dp.m630a(this.f418a.f4094H, a, "common;exception;sdkcoordinate;sdkupdate");
                if (a2 != null) {
                    if (a2.f503g != null) {
                        a.m707a(a2.f503g.f490a);
                    }
                    if (a2.f505i != null) {
                        new du(this.f418a.f4094H, C0273r.f695b, a2.f505i.f492a, a2.f505i.f493b).m4224a();
                    }
                    if (a2.f504h != null) {
                        C0245c c0245c = a2.f504h;
                        if (c0245c != null) {
                            Object obj = c0245c.f495b;
                            Object obj2 = c0245c.f494a;
                            Object obj3 = c0245c.f496c;
                            if (TextUtils.isEmpty(obj) || TextUtils.isEmpty(obj2) || TextUtils.isEmpty(obj3)) {
                                new ey(this.f418a.f4094H, null, a).m4271a();
                            } else {
                                new ey(this.f418a.f4094H, new ez(obj2, obj, obj3), a).m4271a();
                            }
                        } else {
                            new ey(this.f418a.f4094H, null, a).m4271a();
                        }
                    }
                }
                C0273r.f701h = a;
                ee.m4242a(this.f418a.f4094H, a);
                interrupt();
                this.f418a.setRunLowFrame(false);
            }
        } catch (Throwable th) {
            interrupt();
            ee.m4243a(th, "AMapDelegateImpGLSurfaceView", "mVerfy");
            th.printStackTrace();
        }
    }
}
