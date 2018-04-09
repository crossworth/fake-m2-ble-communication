package com.tencent.stat.common;

public class C1385g {
    static final /* synthetic */ boolean f4420a = (!C1385g.class.desiredAssertionStatus());

    private C1385g() {
    }

    public static byte[] m4103a(byte[] bArr, int i) {
        return C1385g.m4104a(bArr, 0, bArr.length, i);
    }

    public static byte[] m4104a(byte[] bArr, int i, int i2, int i3) {
        C1387i c1387i = new C1387i(i3, new byte[((i2 * 3) / 4)]);
        if (!c1387i.m4107a(bArr, i, i2, true)) {
            throw new IllegalArgumentException("bad base-64");
        } else if (c1387i.b == c1387i.a.length) {
            return c1387i.a;
        } else {
            Object obj = new byte[c1387i.b];
            System.arraycopy(c1387i.a, 0, obj, 0, c1387i.b);
            return obj;
        }
    }

    public static byte[] m4105b(byte[] bArr, int i) {
        return C1385g.m4106b(bArr, 0, bArr.length, i);
    }

    public static byte[] m4106b(byte[] bArr, int i, int i2, int i3) {
        C1388j c1388j = new C1388j(i3, null);
        int i4 = (i2 / 3) * 4;
        if (!c1388j.f4432d) {
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
        if (c1388j.f4433e && i2 > 0) {
            i4 += (c1388j.f4434f ? 2 : 1) * (((i2 - 1) / 57) + 1);
        }
        c1388j.a = new byte[i4];
        c1388j.m4108a(bArr, i, i2, true);
        if (f4420a || c1388j.b == i4) {
            return c1388j.a;
        }
        throw new AssertionError();
    }
}
