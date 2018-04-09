package com.baidu.platform.comjni.map.commonmemcache;

import android.os.Bundle;

public class C0675a {
    private long f2236a;
    private JNICommonMemCache f2237b;

    public C0675a() {
        this.f2236a = 0;
        this.f2237b = null;
        this.f2237b = new JNICommonMemCache();
    }

    public long m2257a() {
        this.f2236a = this.f2237b.Create();
        return this.f2236a;
    }

    public void m2258a(Bundle bundle) {
        if (this.f2236a != 0) {
            this.f2237b.Init(this.f2236a, bundle);
        }
    }

    public String m2259b() {
        return this.f2237b.GetPhoneInfoUrl(this.f2236a);
    }
}
