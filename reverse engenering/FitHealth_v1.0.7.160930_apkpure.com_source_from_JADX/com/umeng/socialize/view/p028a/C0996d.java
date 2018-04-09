package com.umeng.socialize.view.p028a;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

/* compiled from: ACProgressCustom */
class C0996d implements OnDismissListener {
    final /* synthetic */ C1829c f3431a;

    C0996d(C1829c c1829c) {
        this.f3431a = c1829c;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (this.f3431a.f4852f != null) {
            this.f3431a.f4852f.cancel();
            this.f3431a.f4852f = null;
        }
        if (this.f3431a.f4855i != null) {
            this.f3431a.f4855i.clear();
            this.f3431a.f4855i = null;
        }
        this.f3431a.f4853g = 0;
        this.f3431a.f4854h = 0;
        this.f3431a.f4851e = null;
    }
}
