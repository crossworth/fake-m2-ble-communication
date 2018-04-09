package com.droi.sdk.push;

import android.content.Context;
import android.content.Intent;
import com.droi.sdk.push.utils.C1012g;
import com.droi.sdk.push.utils.C1015j;

class C1018v implements Runnable {
    final /* synthetic */ Context f3362a;
    final /* synthetic */ Intent f3363b;
    final /* synthetic */ C1005u f3364c;

    C1018v(C1005u c1005u, Context context, Intent intent) {
        this.f3364c = c1005u;
        this.f3362a = context;
        this.f3363b = intent;
    }

    public void run() {
        boolean e = aa.m2958a(this.f3362a).m2980e();
        if (e) {
            if ("com.droi.sdk.push.action.DATA".equals(this.f3363b.getAction())) {
                String stringExtra = this.f3363b.getStringExtra("app_id");
                String appId = DroiPush.getAppId(this.f3362a);
                if (appId != null && appId.equals(stringExtra)) {
                    stringExtra = this.f3363b.getStringExtra("msg");
                    if (C1015j.m3168d(stringExtra)) {
                        C1012g.m3141c("receive message from broadcast!");
                        this.f3364c.m3123a(new C0991f(stringExtra));
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        C1012g.m3141c(this.f3362a.getPackageName() + " isPushable: " + e);
    }
}
