package com.tencent.map.p026a.p027a;

import com.tencent.map.p028b.C1222h;

public class C1191b {
    private int f3711a = 1;
    private int f3712b = 0;
    private int f3713c = 12;
    private int f3714d = 1;

    public C1191b(int i, int i2, int i3, int i4) {
        C1222h.m3619a("argument: " + this.f3711a + " " + this.f3714d + " " + this.f3712b);
        if (i >= 0 && i <= 1) {
            this.f3711a = i;
        }
        if (i2 >= 0 && i2 <= 1) {
            this.f3714d = i2;
        }
        if (i3 == 0 || i3 == 3 || i3 == 4 || i3 == 7) {
            this.f3712b = i3;
        }
        if (this.f3714d == 0) {
            this.f3712b = 0;
        }
        this.f3713c = i4;
    }

    public int m3490a() {
        return this.f3711a;
    }

    public void mo2209a(int i) {
    }

    public void mo2210a(C1193d c1193d) {
    }

    public void mo2211a(byte[] bArr, int i) {
    }

    public int m3494b() {
        return this.f3712b;
    }

    public int m3495c() {
        return this.f3714d;
    }
}
