package com.tencent.map.p011a.p012a;

import com.tencent.map.p013b.C0751q;

public class C0715b {
    private int f2456a = 1;
    private int f2457b = 0;
    private int f2458c = 12;
    private int f2459d = 1;

    public C0715b(int i, int i2, int i3, int i4) {
        C0751q.m2483a("argument: " + this.f2456a + " " + this.f2459d + " " + this.f2457b);
        if (i >= 0 && i <= 1) {
            this.f2456a = i;
        }
        if (i2 >= 0 && i2 <= 1) {
            this.f2459d = i2;
        }
        if (i3 == 0 || i3 == 3 || i3 == 4 || i3 == 7) {
            this.f2457b = i3;
        }
        if (this.f2459d == 0) {
            this.f2457b = 0;
        }
        this.f2458c = i4;
    }

    public int m2391a() {
        return this.f2456a;
    }

    public void mo2138a(int i) {
    }

    public void mo2139a(C0717d c0717d) {
    }

    public void mo2140a(byte[] bArr, int i) {
    }

    public int m2395b() {
        return this.f2457b;
    }

    public int m2396c() {
        return this.f2459d;
    }
}
