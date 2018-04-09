package com.tencent.wxop.stat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class C1430b extends BroadcastReceiver {
    final /* synthetic */ C1428a f4720a;

    C1430b(C1428a c1428a) {
        this.f4720a = c1428a;
    }

    public void onReceive(Context context, Intent intent) {
        if (this.f4720a.f4641e != null) {
            this.f4720a.f4641e.m4388a(new C1431c(this));
        }
    }
}
