package com.baidu.location.p005a;

import android.location.Location;

class C0341d implements Runnable {
    final /* synthetic */ Location f202a;
    final /* synthetic */ C0340c f203b;

    C0341d(C0340c c0340c, Location location) {
        this.f203b = c0340c;
        this.f202a = location;
    }

    public void run() {
        this.f203b.m232b(this.f202a);
    }
}
