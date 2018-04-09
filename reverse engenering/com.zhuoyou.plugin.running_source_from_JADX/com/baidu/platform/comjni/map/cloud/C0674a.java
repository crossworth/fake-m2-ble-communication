package com.baidu.platform.comjni.map.cloud;

import android.os.Bundle;

public class C0674a {
    private static final String f2233a = C0674a.class.getSimpleName();
    private long f2234b;
    private JniCloud f2235c = new JniCloud();

    public long m2251a() {
        this.f2234b = this.f2235c.create();
        return this.f2234b;
    }

    public void m2252a(Bundle bundle) {
        this.f2235c.cloudSearch(this.f2234b, bundle);
    }

    public byte[] m2253a(int i) {
        return this.f2235c.getSearchResult(this.f2234b, i);
    }

    public int m2254b() {
        return this.f2235c.release(this.f2234b);
    }

    public void m2255b(Bundle bundle) {
        this.f2235c.cloudDetailSearch(this.f2234b, bundle);
    }

    public void m2256c(Bundle bundle) {
        this.f2235c.cloudRgcSearch(this.f2234b, bundle);
    }
}
