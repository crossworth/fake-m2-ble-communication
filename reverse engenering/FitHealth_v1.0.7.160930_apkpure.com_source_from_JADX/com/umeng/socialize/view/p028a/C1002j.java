package com.umeng.socialize.view.p028a;

import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;

/* compiled from: ACProgressPie */
class C1002j implements OnDismissListener {
    final /* synthetic */ C1831i f3470a;

    C1002j(C1831i c1831i) {
        this.f3470a = c1831i;
    }

    public void onDismiss(DialogInterface dialogInterface) {
        if (this.f3470a.f4862c != null) {
            this.f3470a.f4862c.cancel();
            this.f3470a.f4862c = null;
        }
        this.f3470a.f4863d = 0;
        this.f3470a.f4861b = null;
    }
}
