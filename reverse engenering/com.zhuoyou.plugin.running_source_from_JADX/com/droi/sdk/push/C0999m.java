package com.droi.sdk.push;

import com.droi.sdk.push.p019a.C0974a;
import com.droi.sdk.push.p019a.C0975b;
import com.droi.sdk.push.utils.C1015j;

class C0999m extends C0975b {
    final /* synthetic */ DroiPushService f3315a;

    C0999m(DroiPushService droiPushService, byte[] bArr) {
        this.f3315a = droiPushService;
        super(bArr);
    }

    public void mo1930a() {
        this.f3315a.m2920c();
    }

    public void mo1931a(C0974a c0974a) {
        ag.m3007a(this.f3315a.f3191g, c0974a.m2928h(), "m01", 0, 1, -1, "DROIPUSH");
        this.f3315a.m2897a(c0974a);
    }

    public boolean mo1932b() {
        return C1015j.m3156a(this.f3315a);
    }

    public boolean mo1933b(C0974a c0974a) {
        return this.f3315a.f3194j.m3071a(c0974a.m2923c(), c0974a.m2928h());
    }
}
