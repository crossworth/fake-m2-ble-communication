package com.baidu.platform.comapi.search;

import android.os.Handler;
import android.os.Message;

class C0659e extends Handler {
    final /* synthetic */ C0658d f2157a;

    C0659e(C0658d c0658d) {
        this.f2157a = c0658d;
    }

    public void handleMessage(Message message) {
        if (this.f2157a.f2152c != 0 && this.f2157a.f2152c == ((Long) message.obj).longValue() && this.f2157a.f2153d != null) {
            this.f2157a.f2153d.m2103a(message);
        }
    }
}
