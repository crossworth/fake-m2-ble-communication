package com.amap.api.services.proguard;

/* compiled from: ThreadTask */
public abstract class db implements Runnable {
    C0388a f1538d;

    /* compiled from: ThreadTask */
    interface C0388a {
        void mo1778a(db dbVar);

        void mo1779b(db dbVar);
    }

    public abstract void mo1776a();

    public final void run() {
        try {
            if (this.f1538d != null) {
                this.f1538d.mo1778a(this);
            }
            if (!Thread.interrupted()) {
                mo1776a();
                if (!Thread.interrupted() && this.f1538d != null) {
                    this.f1538d.mo1779b(this);
                }
            }
        } catch (Throwable th) {
            bh.m4438b(th, "ThreadTask", "run");
            th.printStackTrace();
        }
    }
}
