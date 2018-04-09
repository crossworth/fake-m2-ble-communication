package com.aps;

import java.util.concurrent.Callable;

/* compiled from: DiskLruCache */
class C0452h implements Callable<Void> {
    final /* synthetic */ C0451g f1893a;

    C0452h(C0451g c0451g) {
        this.f1893a = c0451g;
    }

    public /* synthetic */ Object call() throws Exception {
        return m1952a();
    }

    public Void m1952a() throws Exception {
        synchronized (this.f1893a) {
            if (this.f1893a.f1888k == null) {
            } else {
                this.f1893a.m1947h();
                if (this.f1893a.m1945f()) {
                    this.f1893a.m1942e();
                    this.f1893a.f1890m = 0;
                }
            }
        }
        return null;
    }
}
