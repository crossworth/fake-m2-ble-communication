package com.aps;

import android.os.Looper;
import java.util.List;

final class aq extends Thread {
    final /* synthetic */ C0475y f1759a;

    aq(C0475y c0475y, String str) {
        this.f1759a = c0475y;
        super(str);
    }

    public final void run() {
        try {
            Looper.prepare();
            this.f1759a.f1985B = Looper.myLooper();
            this.f1759a.f1988E = new as(this.f1759a);
            try {
                this.f1759a.f2007s.addGpsStatusListener(this.f1759a.f1988E);
                this.f1759a.f2007s.addNmeaListener(this.f1759a.f1988E);
            } catch (Exception e) {
            }
            this.f1759a.f1989F = new ar(this);
            List allProviders = this.f1759a.f2007s.getAllProviders();
            if (allProviders != null && allProviders.contains("gps")) {
                allProviders.contains("passive");
            }
            try {
                this.f1759a.f2007s.requestLocationUpdates("passive", 1000, (float) C0475y.f1976c, this.f1759a.f1991H);
            } catch (Exception e2) {
            }
            Looper.loop();
        } catch (Exception e3) {
        }
    }
}
