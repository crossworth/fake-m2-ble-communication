package com.baidu.location.p005a;

import com.baidu.location.C0455f;
import com.baidu.location.p012f.C0454h;

class C0355m implements Runnable {
    final /* synthetic */ C0354l f292a;

    C0355m(C0354l c0354l) {
        this.f292a = c0354l;
    }

    public void run() {
        if (C0454h.m952h() || this.f292a.m323a(C0455f.getServiceContext())) {
            this.f292a.m203d();
        }
    }
}
