package com.aps;

public final class at extends Thread {
    private /* synthetic */ C0475y f1769a;

    public final void run() {
        while (this.f1769a.f1999k) {
            try {
                C0475y.m2037a(this.f1769a, this.f1769a.f1987D, 1, System.currentTimeMillis());
                try {
                    Thread.sleep((long) this.f1769a.f2004p);
                } catch (Exception e) {
                }
            } catch (Exception e2) {
                return;
            }
        }
    }
}
