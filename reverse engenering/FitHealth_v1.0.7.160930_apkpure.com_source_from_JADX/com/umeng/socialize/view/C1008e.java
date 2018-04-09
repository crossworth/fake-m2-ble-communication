package com.umeng.socialize.view;

import android.view.View;

/* compiled from: OauthDialog */
class C1008e implements Runnable {
    final /* synthetic */ View f3481a;
    final /* synthetic */ View f3482b;
    final /* synthetic */ C1006c f3483c;

    C1008e(C1006c c1006c, View view, View view2) {
        this.f3483c = c1006c;
        this.f3481a = view;
        this.f3482b = view2;
    }

    public void run() {
        this.f3481a.setVisibility(0);
        if (!(this.f3482b.getVisibility() == 0 || this.f3483c.f3477d.f3377m == null || this.f3483c.f3477d.f3377m.size() <= 0)) {
            this.f3482b.setVisibility(0);
        }
        this.f3483c.requestLayout();
    }
}
