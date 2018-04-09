package com.umeng.socialize.view;

import android.view.View;

/* compiled from: OauthDialog */
class C1007d implements Runnable {
    final /* synthetic */ View f3478a;
    final /* synthetic */ View f3479b;
    final /* synthetic */ C1006c f3480c;

    C1007d(C1006c c1006c, View view, View view2) {
        this.f3480c = c1006c;
        this.f3478a = view;
        this.f3479b = view2;
    }

    public void run() {
        this.f3478a.setVisibility(8);
        if (this.f3479b.getVisibility() == 0) {
            this.f3479b.setVisibility(8);
        }
        this.f3480c.requestLayout();
    }
}
