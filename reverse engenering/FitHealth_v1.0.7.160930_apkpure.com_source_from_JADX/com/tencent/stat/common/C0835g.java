package com.tencent.stat.common;

public class C0835g {
    static final /* synthetic */ boolean f2900a = (!C0835g.class.desiredAssertionStatus());

    private C0835g() {
    }

    public static byte[] m2698a(byte[] bArr, int i) {
        return C0835g.m2699a(bArr, 0, bArr.length, i);
    }

    public static byte[] m2699a(byte[] bArr, int i, int i2, int i3) {
        C1744i c1744i = new C1744i(i3, new byte[((i2 * 3) / 4)]);
        if (!c1744i.m4877a(bArr, i, i2, true)) {
            throw new IllegalArgumentException("bad base-64");
        } else if (c1744i.b == c1744i.a.length) {
            return c1744i.a;
        } else {
            Object obj = new byte[c1744i.b];
            System.arraycopy(c1744i.a, 0, obj, 0, c1744i.b);
            return obj;
        }
    }

    public static byte[] m2700b(byte[] bArr, int i) {
        return C0835g.m2701b(bArr, 0, bArr.length, i);
    }

    public static byte[] m2701b(byte[] bArr, int i, int i2, int i3) {
        C1745j c1745j = new C1745j(i3, null);
        int i4 = (i2 / 3) * 4;
        if (!c1745j.f4714d) {
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
        if (c1745j.f4715e && i2 > 0) {
            i4 += (c1745j.f4716f ? 2 : 1) * (((i2 - 1) / 57) + 1);
        }
        c1745j.a = new byte[i4];
        c1745j.m4878a(bArr, i, i2, true);
        if (f2900a || c1745j.b == i4) {
            return c1745j.a;
        }
        throw new AssertionError();
    }
}
