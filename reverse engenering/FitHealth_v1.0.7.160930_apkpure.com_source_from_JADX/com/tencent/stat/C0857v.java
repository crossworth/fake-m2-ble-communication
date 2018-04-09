package com.tencent.stat;

class C0857v implements Runnable {
    final /* synthetic */ int f2952a;
    final /* synthetic */ C0850n f2953b;

    C0857v(C0850n c0850n, int i) {
        this.f2953b = c0850n;
        this.f2952a = i;
    }

    public void run() {
        int a = StatConfig.m2619a();
        int i = this.f2952a == -1 ? this.f2953b.f2936b : this.f2952a;
        int i2 = i / a;
        int i3 = i % a;
        for (i = 0; i < i2 + 1; i++) {
            this.f2953b.m2787b(a);
        }
        if (i3 > 0) {
            this.f2953b.m2787b(i3);
        }
    }
}
