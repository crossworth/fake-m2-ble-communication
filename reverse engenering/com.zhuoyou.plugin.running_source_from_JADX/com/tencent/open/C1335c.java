package com.tencent.open;

import android.content.Context;
import android.location.Location;
import com.tencent.map.p026a.p027a.C1190a;

/* compiled from: ProGuard */
public class C1335c {
    private C1336d f4177a;

    /* compiled from: ProGuard */
    public interface C1275a {
        void onLocationUpdate(Location location);
    }

    public boolean m3921a() {
        return C1190a.m3486a().m3488a("OpenSdk", "WQMPF-XMH66-ISQXP-OIGMM-BNL7M");
    }

    public void m3920a(Context context, C1275a c1275a) {
        this.f4177a = new C1336d(c1275a);
        C1190a.m3486a().m3487a(context, this.f4177a);
    }

    public void m3922b() {
        C1190a.m3486a().m3489b();
        this.f4177a = null;
    }
}
