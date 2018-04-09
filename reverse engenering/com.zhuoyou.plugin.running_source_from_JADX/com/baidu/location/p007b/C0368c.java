package com.baidu.location.p007b;

import android.os.Handler;
import android.os.Message;

class C0368c extends Handler {
    final /* synthetic */ C0367b f353a;

    C0368c(C0367b c0367b) {
        this.f353a = c0367b;
    }

    public void handleMessage(Message message) {
        switch (message.what) {
            case 1:
                try {
                    this.f353a.m386f();
                    return;
                } catch (Exception e) {
                    return;
                }
            case 2:
                try {
                    this.f353a.m387g();
                    return;
                } catch (Exception e2) {
                    return;
                }
            default:
                return;
        }
    }
}
