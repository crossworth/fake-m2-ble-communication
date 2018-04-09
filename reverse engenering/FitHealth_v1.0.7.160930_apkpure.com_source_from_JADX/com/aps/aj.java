package com.aps;

import android.os.Looper;
import java.util.Timer;

final class aj extends Thread {
    private /* synthetic */ ai f1753a;

    aj(ai aiVar, String str) {
        this.f1753a = aiVar;
        super(str);
    }

    public final void run() {
        try {
            Looper.prepare();
            this.f1753a.f1727B = Looper.myLooper();
            this.f1753a.f1752z = new Timer();
            this.f1753a.f1747u = new ak(this.f1753a);
            ai.m1765a(this.f1753a, this.f1753a.f1747u);
            this.f1753a.f1748v = new al(this.f1753a);
            try {
                ai.m1764a(this.f1753a, this.f1753a.f1748v);
            } catch (Exception e) {
            }
            Looper.loop();
        } catch (Exception e2) {
        }
    }
}
