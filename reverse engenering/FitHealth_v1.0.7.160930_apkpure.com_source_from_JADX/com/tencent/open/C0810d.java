package com.tencent.open;

import android.content.Context;
import android.location.Location;
import com.tencent.map.p011a.p012a.C0714a;

/* compiled from: ProGuard */
public class C0810d {
    private C1729f f2748a;

    /* compiled from: ProGuard */
    public interface C0809a {
        void onLocationUpdate(Location location);
    }

    public boolean m2592a() {
        return C0714a.m2387a().m2389a("OpenSdk", "WQMPF-XMH66-ISQXP-OIGMM-BNL7M");
    }

    public void m2591a(Context context, C0809a c0809a) {
        this.f2748a = new C1729f(c0809a);
        C0714a.m2387a().m2388a(context, this.f2748a);
    }

    public void m2593b() {
        C0714a.m2387a().m2390b();
        this.f2748a = null;
    }
}
