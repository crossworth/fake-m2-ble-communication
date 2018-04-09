package com.tencent.connect.avatar;

import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: ProGuard */
class C0701f implements OnClickListener {
    final /* synthetic */ ImageActivity f2434a;

    C0701f(ImageActivity imageActivity) {
        this.f2434a = imageActivity;
    }

    public void onClick(View view) {
        this.f2434a.m2364a("10656", System.currentTimeMillis() - this.f2434a.f2401m);
        this.f2434a.setResult(0);
        this.f2434a.m2352d();
    }
}
