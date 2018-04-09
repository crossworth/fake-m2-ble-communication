package com.umeng.socialize.shareboard.p026a;

import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/* compiled from: SNSPlatformAdapter */
class C0975c implements OnTouchListener {
    final /* synthetic */ View f3334a;
    final /* synthetic */ C1827a f3335b;

    C0975c(C1827a c1827a, View view) {
        this.f3335b = c1827a;
        this.f3334a = view;
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 0) {
            this.f3334a.setBackgroundColor(-3355444);
        } else if (motionEvent.getAction() == 1) {
            this.f3334a.setBackgroundColor(-1);
        }
        return false;
    }
}
