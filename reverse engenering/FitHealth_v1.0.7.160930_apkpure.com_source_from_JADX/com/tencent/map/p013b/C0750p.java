package com.tencent.map.p013b;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

class C0750p extends BroadcastReceiver {
    private /* synthetic */ C1722n f2624a;

    C0750p(C1722n c1722n) {
        this.f2624a = c1722n;
    }

    public final void onReceive(Context context, Intent intent) {
        if (!intent.getBooleanExtra("noConnectivity", false) && this.f2624a.f4594q != null) {
            this.f2624a.f4594q.sendEmptyMessage(256);
        }
    }
}
