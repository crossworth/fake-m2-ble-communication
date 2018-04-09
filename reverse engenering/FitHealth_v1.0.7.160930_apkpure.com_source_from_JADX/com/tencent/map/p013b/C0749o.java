package com.tencent.map.p013b;

class C0749o implements Runnable {
    private /* synthetic */ C1722n f2623a;

    C0749o(C1722n c1722n) {
        this.f2623a = c1722n;
    }

    public final void run() {
        if (System.currentTimeMillis() - this.f2623a.f4574M >= 8000) {
            if (this.f2623a.f4582e.m2461b() && this.f2623a.f4582e.m2462c()) {
                this.f2623a.f4582e.m2459a(0);
            } else {
                this.f2623a.m4702d();
            }
        }
    }
}
