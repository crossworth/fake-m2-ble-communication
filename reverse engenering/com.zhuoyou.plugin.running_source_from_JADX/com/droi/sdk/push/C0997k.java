package com.droi.sdk.push;

import com.droi.sdk.push.utils.C1012g;
import org.json.JSONObject;

class C0997k implements C0994n {
    final /* synthetic */ C1004t f3312a;
    final /* synthetic */ DroiPushService f3313b;

    C0997k(DroiPushService droiPushService, C1004t c1004t) {
        this.f3313b = droiPushService;
        this.f3312a = c1004t;
    }

    public void mo1927a(boolean z, JSONObject jSONObject) {
        if (z) {
            C1012g.m3141c("repeat message is valid: " + this.f3312a.f3327a);
            this.f3313b.m2908c(this.f3312a);
            return;
        }
        C1012g.m3141c("repeat message is invalid: " + this.f3312a.f3327a);
        DroiPushService.f3185b.m3034a(this.f3312a.f3337k);
    }
}
