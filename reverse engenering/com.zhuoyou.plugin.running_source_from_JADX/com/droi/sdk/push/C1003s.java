package com.droi.sdk.push;

class C1003s implements Comparable {
    public final long f3324a;
    public final long f3325b;
    final /* synthetic */ C1002q f3326c;

    public C1003s(C1002q c1002q, long j, long j2) {
        this.f3326c = c1002q;
        this.f3324a = j;
        this.f3325b = j2;
    }

    public int m3072a(C1003s c1003s) {
        return this.f3325b > c1003s.f3325b ? 1 : this.f3325b < c1003s.f3325b ? -1 : 0;
    }

    public /* synthetic */ int compareTo(Object obj) {
        return m3072a((C1003s) obj);
    }

    public boolean equals(Object obj) {
        C1003s c1003s = (C1003s) obj;
        return c1003s != null && this.f3324a == c1003s.f3324a;
    }
}
