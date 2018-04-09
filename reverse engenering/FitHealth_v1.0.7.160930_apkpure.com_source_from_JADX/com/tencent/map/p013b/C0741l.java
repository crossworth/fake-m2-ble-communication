package com.tencent.map.p013b;

import java.util.Collection;

class C0741l extends Thread {
    private /* synthetic */ C0745m f2581a;

    C0741l(C0745m c0745m) {
        this.f2581a = c0745m;
    }

    public final void run() {
        if (this.f2581a.f2605b != null) {
            Collection neighboringCellInfo = this.f2581a.f2605b.getNeighboringCellInfo();
            synchronized (this.f2581a.f2612i) {
                if (neighboringCellInfo != null) {
                    this.f2581a.f2610g.clear();
                    this.f2581a.f2610g.addAll(neighboringCellInfo);
                }
            }
        }
        this.f2581a.f2613j = false;
    }
}
