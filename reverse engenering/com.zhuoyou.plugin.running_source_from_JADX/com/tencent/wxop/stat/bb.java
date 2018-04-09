package com.tencent.wxop.stat;

class bb implements Runnable {
    final /* synthetic */ int f4724a;
    final /* synthetic */ au f4725b;

    bb(au auVar, int i) {
        this.f4725b = auVar;
        this.f4724a = i;
    }

    public void run() {
        this.f4725b.m4347b(this.f4724a, true);
        this.f4725b.m4347b(this.f4724a, false);
    }
}
