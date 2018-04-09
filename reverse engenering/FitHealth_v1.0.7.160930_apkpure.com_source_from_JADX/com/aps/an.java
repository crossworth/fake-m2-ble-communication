package com.aps;

import java.util.TimerTask;

final class an extends TimerTask {
    private /* synthetic */ am f1757a;

    an(am amVar) {
        this.f1757a = amVar;
    }

    public final void run() {
        try {
            if (this.f1757a.f1756a.f1731d != null) {
                this.f1757a.f1756a.f1731d.startScan();
            }
        } catch (Exception e) {
        }
    }
}
