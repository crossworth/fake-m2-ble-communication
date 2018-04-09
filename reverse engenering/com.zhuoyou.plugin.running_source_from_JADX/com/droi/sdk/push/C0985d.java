package com.droi.sdk.push;

import android.view.View;
import android.view.View.OnClickListener;

class C0985d implements OnClickListener {
    final /* synthetic */ DroiPushActivity f3262a;

    C0985d(DroiPushActivity droiPushActivity) {
        this.f3262a = droiPushActivity;
    }

    public void onClick(View view) {
        if (this.f3262a.f3183c > 0) {
            ag.m3007a(this.f3262a, this.f3262a.f3183c, "m01", 6, 1, -1, "DROIPUSH");
        }
        this.f3262a.finish();
    }
}
