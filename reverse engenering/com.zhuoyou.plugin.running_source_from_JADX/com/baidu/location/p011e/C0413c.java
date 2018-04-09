package com.baidu.location.p011e;

import java.util.List;
import java.util.concurrent.Callable;

class C0413c implements Callable<String> {
    final /* synthetic */ String f589a;
    final /* synthetic */ List f590b;
    final /* synthetic */ C0411a f591c;

    C0413c(C0411a c0411a, String str, List list) {
        this.f591c = c0411a;
        this.f589a = str;
        this.f590b = list;
    }

    public String m658a() {
        this.f591c.m646a(this.f589a, this.f590b);
        return this.f591c.m648b(true);
    }

    public /* synthetic */ Object call() throws Exception {
        return m658a();
    }
}
