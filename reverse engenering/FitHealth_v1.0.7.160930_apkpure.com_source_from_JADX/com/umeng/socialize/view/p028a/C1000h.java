package com.umeng.socialize.view.p028a;

import java.util.TimerTask;

/* compiled from: ACProgressFlower */
class C1000h extends TimerTask {
    final /* synthetic */ C1830f f3454a;

    C1000h(C1830f c1830f) {
        this.f3454a = c1830f;
    }

    public void run() {
        int b = this.f3454a.f4858c % this.f3454a.f4856a.f3440h;
        if (this.f3454a.f4856a.f3445m == 100) {
            this.f3454a.f4857b.m3288a(b);
        } else {
            this.f3454a.f4857b.m3288a((this.f3454a.f4856a.f3440h - 1) - b);
        }
        if (b == 0) {
            this.f3454a.f4858c = 1;
        } else {
            this.f3454a.f4858c = this.f3454a.f4858c + 1;
        }
    }
}
