package com.droi.sdk.push;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

class C1001p extends Handler {
    final /* synthetic */ DroiPushService f3319a;

    public C1001p(DroiPushService droiPushService, Looper looper) {
        this.f3319a = droiPushService;
        super(looper);
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                this.f3319a.m2893a(message.getData());
                return;
            case 2:
                this.f3319a.m2914e((C1004t) message.obj);
                return;
            case 3:
                this.f3319a.m2918a(false);
                removeMessages(3);
                if (aa.m2958a(this.f3319a.f3191g).m2983g()) {
                    this.f3319a.m2915f();
                }
                this.f3319a.f3194j.m3070a();
                return;
            default:
                return;
        }
    }
}
