package com.droi.sdk.push;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import java.lang.Thread.State;

class ac extends Handler {
    final /* synthetic */ aa f3233a;

    public ac(aa aaVar, Looper looper) {
        this.f3233a = aaVar;
        super(looper);
    }

    public void handleMessage(Message message) {
        if (message.what == 1) {
            removeMessages(1);
            String a = DroiPush.m2876a(this.f3233a.f3227b);
            if (!TextUtils.isEmpty(a)) {
                ad.m2994a(this.f3233a.f3227b, a);
            }
        } else if (message.what == 2) {
            removeMessages(2);
            Object a2 = DroiPush.m2876a(this.f3233a.f3227b);
            if (!TextUtils.isEmpty(a2)) {
                ad.m2999b(this.f3233a.f3227b, a2);
            }
        } else if (message.what != 3) {
        } else {
            if (this.f3233a.f3230e == null || this.f3233a.f3230e.getState() == State.TERMINATED) {
                this.f3233a.f3230e = new ab(this.f3233a, this.f3233a.f3227b);
                this.f3233a.f3230e.start();
            }
        }
    }
}
