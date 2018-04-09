package com.tencent.wxop.stat.common;

public class C1438g {
    static final /* synthetic */ boolean f4768a = (!C1438g.class.desiredAssertionStatus());

    private C1438g() {
    }

    public static byte[] m4394a(byte[] bArr, int i) {
        return C1438g.m4395a(bArr, 0, bArr.length, i);
    }

    public static byte[] m4395a(byte[] bArr, int i, int i2, int i3) {
        C1440i c1440i = new C1440i(i3, new byte[((i2 * 3) / 4)]);
        if (!c1440i.m4398a(bArr, i, i2, true)) {
            throw new IllegalArgumentException("bad base-64");
        } else if (c1440i.b == c1440i.a.length) {
            return c1440i.a;
        } else {
            Object obj = new byte[c1440i.b];
            System.arraycopy(c1440i.a, 0, obj, 0, c1440i.b);
            return obj;
        }
    }

    public static byte[] m4396b(byte[] bArr, int i) {
        return C1438g.m4397b(bArr, 0, bArr.length, i);
    }

    public static byte[] m4397b(byte[] bArr, int i, int i2, int i3) {
        C1441j c1441j = new C1441j(i3, null);
        int i4 = (i2 / 3) * 4;
        if (!c1441j.f4780d) {
            switch (i2 % 3) {
                case 0:
                    break;
                case 1:
                    i4 += 2;
                    break;
                case 2:
                    i4 += 3;
                    break;
                default:
                    break;
            }
        } else if (i2 % 3 > 0) {
            i4 += 4;
        }
        if (c1441j.f4781e && i2 > 0) {
            i4 += (c1441j.f4782f ? 2 : 1) * (((i2 - 1) / 57) + 1);
        }
        c1441j.a = new byte[i4];
        c1441j.m4399a(bArr, i, i2, true);
        if (f4768a || c1441j.b == i4) {
            return c1441j.a;
        }
        throw new AssertionError();
    }
}
