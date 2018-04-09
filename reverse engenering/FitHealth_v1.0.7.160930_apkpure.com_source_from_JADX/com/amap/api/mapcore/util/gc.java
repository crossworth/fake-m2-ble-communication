package com.amap.api.mapcore.util;

/* compiled from: ThreadTask */
public abstract class gc implements Runnable {
    C0266a f678d;

    /* compiled from: ThreadTask */
    interface C0266a {
        void mo1652a(gc gcVar);

        void mo1653b(gc gcVar);

        void mo1654c(gc gcVar);
    }

    public abstract void mo1473a();

    public final void run() {
        try {
            if (this.f678d != null) {
                this.f678d.mo1652a(this);
            }
            if (!Thread.interrupted()) {
                mo1473a();
                if (!Thread.interrupted() && this.f678d != null) {
                    this.f678d.mo1653b(this);
                }
            }
        } catch (Throwable th) {
            ee.m4243a(th, "ThreadTask", "run");
            th.printStackTrace();
        }
    }

    public final void m994e() {
        try {
            if (this.f678d != null) {
                this.f678d.mo1654c(this);
            }
        } catch (Throwable th) {
            ee.m4243a(th, "ThreadTask", "cancelTask");
            th.printStackTrace();
        }
    }
}
