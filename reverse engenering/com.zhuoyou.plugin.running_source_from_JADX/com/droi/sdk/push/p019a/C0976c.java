package com.droi.sdk.push.p019a;

import com.droi.sdk.push.utils.C1012g;

class C0976c implements Runnable {
    final /* synthetic */ C0975b f3218a;

    C0976c(C0975b c0975b) {
        this.f3218a = c0975b;
    }

    private void m2946a() {
        synchronized (this) {
            try {
                wait(1000);
            } catch (InterruptedException e) {
            } catch (Exception e2) {
                C1012g.m3137a(e2);
            }
        }
    }

    private void m2948b() {
        synchronized (this) {
            notifyAll();
        }
    }

    private void m2949c() {
        while (true) {
            C0974a d = this.f3218a.m2942d();
            if (d != null) {
                if (d.m2926f()) {
                    this.f3218a.mo1931a(d);
                }
            } else {
                return;
            }
        }
    }

    public void run() {
        synchronized (this.f3218a.f3217q) {
            this.f3218a.f3217q.notifyAll();
        }
        while (!this.f3218a.f3214n) {
            try {
                m2949c();
            } catch (Exception e) {
                C1012g.m3137a(e);
            } finally {
                m2946a();
            }
        }
    }
}
