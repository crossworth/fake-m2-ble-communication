package com.droi.sdk.push;

import android.content.Context;
import com.droi.sdk.push.p019a.C0977d;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;

class C0998l extends C0977d {
    final /* synthetic */ DroiPushService f3314a;

    C0998l(DroiPushService droiPushService, Context context, String str) {
        this.f3314a = droiPushService;
        super(context, str);
    }

    public void mo1928a(C1004t c1004t) {
        C1012g.m3141c("WapClient: receive push msg from wap - " + c1004t.f3327a);
        ag.m3007a(this.f3314a.f3191g, c1004t.f3327a, "m01", 0, 1, -1, "DROIPUSH");
        this.f3314a.m2911d(c1004t);
    }

    public boolean mo1929a() {
        return C1015j.m3156a(this.f3314a);
    }
}
