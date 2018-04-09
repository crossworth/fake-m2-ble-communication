package com.baidu.location.p011e;

import com.baidu.location.C0455f;

class C0412b implements Runnable {
    final /* synthetic */ C0411a f588a;

    C0412b(C0411a c0411a) {
        this.f588a = c0411a;
    }

    public void run() {
        if (C0455f.isServing) {
            if (!this.f588a.f577a) {
                this.f588a.m652d();
            }
            this.f588a.m653e();
        }
    }
}
