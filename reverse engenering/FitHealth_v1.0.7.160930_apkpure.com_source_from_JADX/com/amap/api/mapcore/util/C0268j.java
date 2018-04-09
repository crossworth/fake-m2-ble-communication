package com.amap.api.mapcore.util;

/* compiled from: AMapDelegateImp */
class C0268j implements Runnable {
    final /* synthetic */ C1592c f681a;

    C0268j(C1592c c1592c) {
        this.f681a = c1592c;
    }

    public synchronized void run() {
        if (this.f681a.ba) {
            this.f681a.aZ = true;
            this.f681a.ba = false;
        }
    }
}
