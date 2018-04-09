package com.autonavi.amap.mapcore;

import java.util.Hashtable;

/* compiled from: TilesProcessingCtrl */
class C0480d {
    int f2037a = 0;
    long f2038b;
    boolean f2039c = true;
    private Hashtable<String, C0479c> f2040d = new Hashtable();

    public void m2079a(String str) {
        this.f2040d.remove(str);
    }

    public boolean m2081b(String str) {
        return this.f2040d.get(str) != null;
    }

    public void m2082c(String str) {
        this.f2040d.put(str, new C0479c(str, 0));
    }

    public void m2078a() {
        this.f2040d.clear();
    }

    public C0480d() {
        m2080b();
    }

    public void m2080b() {
        this.f2038b = System.currentTimeMillis();
    }
}
