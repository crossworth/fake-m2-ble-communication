package com.tencent.p004a.p005a;

import com.tencent.p004a.p009c.C0686c.C0685a;

/* compiled from: ProGuard */
public abstract class C0673g {
    private volatile int f2330a;
    private volatile boolean f2331b;
    private C0670b f2332c;

    protected abstract void mo2080a(int i, Thread thread, long j, String str, String str2, Throwable th);

    public C0673g() {
        this(63, true, C0670b.f2321a);
    }

    public C0673g(int i, boolean z, C0670b c0670b) {
        this.f2330a = 63;
        this.f2331b = true;
        this.f2332c = C0670b.f2321a;
        m2254a(i);
        m2257a(z);
        m2256a(c0670b);
    }

    public void m2258b(int i, Thread thread, long j, String str, String str2, Throwable th) {
        if (m2259d() && C0685a.m2305a(this.f2330a, i)) {
            mo2080a(i, thread, j, str, str2, th);
        }
    }

    public void m2254a(int i) {
        this.f2330a = i;
    }

    public boolean m2259d() {
        return this.f2331b;
    }

    public void m2257a(boolean z) {
        this.f2331b = z;
    }

    public C0670b m2260e() {
        return this.f2332c;
    }

    public void m2256a(C0670b c0670b) {
        this.f2332c = c0670b;
    }
}
