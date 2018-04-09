package com.amap.api.mapcore.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: GLOverlayLayer */
class C0287w implements Runnable {
    final /* synthetic */ C0286v f749a;

    C0287w(C0286v c0286v) {
        this.f749a = c0286v;
    }

    public synchronized void run() {
        try {
            synchronized (this.f749a) {
                Collection arrayList = new ArrayList(this.f749a.f745d);
                Collections.sort(arrayList, this.f749a.f744b);
                this.f749a.f745d = new CopyOnWriteArrayList(arrayList);
            }
        } catch (Throwable th) {
            ee.m4243a(th, "MapOverlayImageView", "changeOverlayIndex");
        }
    }
}
