package com.tencent.wxop.stat;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

final class C0914z extends BroadcastReceiver {
    final /* synthetic */ C0898g cm;

    C0914z(C0898g c0898g) {
        this.cm = c0898g;
    }

    public final void onReceive(Context context, Intent intent) {
        if (this.cm.be != null) {
            this.cm.be.m2861a(new ae(this));
        }
    }
}
