package com.tencent.connect.avatar;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;

/* compiled from: ProGuard */
class C0700d implements OnClickListener {
    final /* synthetic */ ImageActivity f2433a;

    /* compiled from: ProGuard */
    class C06991 implements Runnable {
        final /* synthetic */ C0700d f2432a;

        C06991(C0700d c0700d) {
            this.f2432a = c0700d;
        }

        public void run() {
            this.f2432a.f2433a.m2348c();
        }
    }

    C0700d(ImageActivity imageActivity) {
        this.f2433a = imageActivity;
    }

    public void onClick(View view) {
        this.f2433a.f2398j.setVisibility(0);
        this.f2433a.f2395g.setEnabled(false);
        this.f2433a.f2395g.setTextColor(Color.rgb(21, 21, 21));
        this.f2433a.f2394f.setEnabled(false);
        this.f2433a.f2394f.setTextColor(Color.rgb(36, 94, 134));
        new Thread(new C06991(this)).start();
        if (this.f2433a.f2400l) {
            this.f2433a.m2364a("10657", 0);
            return;
        }
        this.f2433a.m2364a("10655", System.currentTimeMillis() - this.f2433a.f2401m);
        if (this.f2433a.f2393e.f2417b) {
            this.f2433a.m2364a("10654", 0);
        }
    }
}
