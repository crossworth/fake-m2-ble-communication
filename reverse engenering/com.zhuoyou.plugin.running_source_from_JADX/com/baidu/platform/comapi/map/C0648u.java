package com.baidu.platform.comapi.map;

import android.os.Handler;
import android.os.Message;

class C0648u extends Handler {
    final /* synthetic */ C0647t f2119a;

    C0648u(C0647t c0647t) {
        this.f2119a = c0647t;
    }

    public void handleMessage(Message message) {
        super.handleMessage(message);
        if (C0647t.f2115c != null) {
            this.f2119a.f2117d.m2084a(message);
        }
    }
}
