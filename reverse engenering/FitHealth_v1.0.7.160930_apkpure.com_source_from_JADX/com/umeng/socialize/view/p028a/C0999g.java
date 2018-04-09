package com.umeng.socialize.view.p028a;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

/* compiled from: ACProgressFlower */
class C0999g implements OnDismissListener {
    final /* synthetic */ C1830f f3453a;

    C0999g(C1830f c1830f) {
        this.f3453a = c1830f;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (this.f3453a.f4859d != null) {
            this.f3453a.f4859d.cancel();
            this.f3453a.f4859d = null;
        }
        this.f3453a.f4858c = 0;
        this.f3453a.f4857b = null;
    }
}
