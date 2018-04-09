package com.umeng.socialize.view.p028a;

import java.util.TimerTask;

/* compiled from: ACProgressCustom */
class C0997e extends TimerTask {
    final /* synthetic */ C1829c f3432a;

    C0997e(C1829c c1829c) {
        this.f3432a = c1829c;
    }

    public void run() {
        int c = this.f3432a.f4853g % this.f3432a.f4854h;
        this.f3432a.f4851e.m3286a(c);
        if (c == 0) {
            this.f3432a.f4853g = 1;
        } else {
            this.f3432a.f4853g = this.f3432a.f4853g + 1;
        }
    }
}
