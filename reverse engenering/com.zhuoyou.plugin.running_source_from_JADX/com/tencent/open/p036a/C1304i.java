package com.tencent.open.p036a;

import com.tencent.open.p036a.C1312d.C1308a;

/* compiled from: ProGuard */
public abstract class C1304i {
    private volatile int f4082a;
    private volatile boolean f4083b;
    private C1316h f4084c;

    protected abstract void mo2208a(int i, Thread thread, long j, String str, String str2, Throwable th);

    public C1304i() {
        this(C1307c.f4106a, true, C1316h.f4130a);
    }

    public C1304i(int i, boolean z, C1316h c1316h) {
        this.f4082a = C1307c.f4106a;
        this.f4083b = true;
        this.f4084c = C1316h.f4130a;
        m3816a(i);
        m3819a(z);
        m3818a(c1316h);
    }

    public void m3820b(int i, Thread thread, long j, String str, String str2, Throwable th) {
        if (m3821d() && C1308a.m3851a(this.f4082a, i)) {
            mo2208a(i, thread, j, str, str2, th);
        }
    }

    public void m3816a(int i) {
        this.f4082a = i;
    }

    public boolean m3821d() {
        return this.f4083b;
    }

    public void m3819a(boolean z) {
        this.f4083b = z;
    }

    public C1316h m3822e() {
        return this.f4084c;
    }

    public void m3818a(C1316h c1316h) {
        this.f4084c = c1316h;
    }
}
