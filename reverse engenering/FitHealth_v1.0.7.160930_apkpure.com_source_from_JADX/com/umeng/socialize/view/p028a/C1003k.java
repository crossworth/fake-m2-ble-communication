package com.umeng.socialize.view.p028a;

import java.util.TimerTask;

/* compiled from: ACProgressPie */
class C1003k extends TimerTask {
    final /* synthetic */ C1831i f3471a;

    C1003k(C1831i c1831i) {
        this.f3471a = c1831i;
    }

    public void run() {
        int b = this.f3471a.f4863d % (this.f3471a.f4860a.f3468n + 1);
        this.f3471a.f4861b.m3289a((360.0f / ((float) this.f3471a.f4860a.f3468n)) * ((float) b));
        if (b == 0) {
            this.f3471a.f4863d = 1;
        } else {
            this.f3471a.f4863d = this.f3471a.f4863d + 1;
        }
    }
}
