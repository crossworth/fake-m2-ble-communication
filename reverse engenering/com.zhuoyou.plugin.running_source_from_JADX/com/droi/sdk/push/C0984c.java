package com.droi.sdk.push;

import com.droi.sdk.push.p020b.C0979a;

class C0984c implements C0983e {
    final /* synthetic */ int f3258a;
    final /* synthetic */ String f3259b;
    final /* synthetic */ String f3260c;
    final /* synthetic */ DroiPushActivity f3261d;

    C0984c(DroiPushActivity droiPushActivity, int i, String str, String str2) {
        this.f3261d = droiPushActivity;
        this.f3258a = i;
        this.f3259b = str;
        this.f3260c = str2;
    }

    public void mo1925a() {
        if (this.f3261d.f3183c > 0) {
            ag.m3007a(this.f3261d, this.f3261d.f3183c, "m01", 5, 1, -1, "DROIPUSH");
        }
        C0979a.m3009a(this.f3261d).m3019a(this.f3261d.f3183c, this.f3258a, this.f3259b, this.f3260c);
        this.f3261d.finish();
    }
}
