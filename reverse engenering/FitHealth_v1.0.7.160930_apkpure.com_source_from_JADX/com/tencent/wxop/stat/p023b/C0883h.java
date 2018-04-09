package com.tencent.wxop.stat.p023b;

public class C0883h {
    static final /* synthetic */ boolean cH = (!C0883h.class.desiredAssertionStatus());

    private C0883h() {
    }

    public static byte[] m2865d(byte[] bArr) {
        int length = bArr.length;
        C1761j c1761j = new C1761j(new byte[((length * 3) / 4)]);
        if (!c1761j.m4899a(bArr, length)) {
            throw new IllegalArgumentException("bad base-64");
        } else if (c1761j.g == c1761j.cI.length) {
            return c1761j.cI;
        } else {
            Object obj = new byte[c1761j.g];
            System.arraycopy(c1761j.cI, 0, obj, 0, c1761j.g);
            return obj;
        }
    }

    public static byte[] m2866e(byte[] bArr) {
        int length = bArr.length;
        C1762k c1762k = new C1762k();
        int i = (length / 3) * 4;
        if (!c1762k.ba) {
            switch (length % 3) {
                case 0:
                    break;
                case 1:
                    i += 2;
                    break;
                case 2:
                    i += 3;
                    break;
                default:
                    break;
            }
        } else if (length % 3 > 0) {
            i += 4;
        }
        if (c1762k.bb && length > 0) {
            i += (c1762k.cP ? 2 : 1) * (((length - 1) / 57) + 1);
        }
        c1762k.cI = new byte[i];
        c1762k.m4900a(bArr, length);
        if (cH || c1762k.g == i) {
            return c1762k.cI;
        }
        throw new AssertionError();
    }
}
