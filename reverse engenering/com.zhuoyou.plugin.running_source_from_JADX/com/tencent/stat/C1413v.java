package com.tencent.stat;

class C1413v implements Runnable {
    final /* synthetic */ int f4493a;
    final /* synthetic */ C1405n f4494b;

    C1413v(C1405n c1405n, int i) {
        this.f4494b = c1405n;
        this.f4493a = i;
    }

    public void run() {
        int a = StatConfig.m4003a();
        int i = this.f4493a == -1 ? this.f4494b.f4474b : this.f4493a;
        int i2 = i / a;
        int i3 = i % a;
        for (i = 0; i < i2 + 1; i++) {
            this.f4494b.m4198b(a);
        }
        if (i3 > 0) {
            this.f4494b.m4198b(i3);
        }
    }
}
