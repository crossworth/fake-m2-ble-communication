package com.amap.api.mapcore.util;

/* compiled from: EarClippingTriangulator */
public class cz {
    private final di f412a = new di();
    private short[] f413b;
    private float[] f414c;
    private int f415d;
    private final de f416e = new de();
    private final di f417f = new di();

    public di m487a(float[] fArr) {
        return m488a(fArr, 0, fArr.length);
    }

    public di m488a(float[] fArr, int i, int i2) {
        int i3;
        this.f414c = fArr;
        int i4 = i2 / 2;
        this.f415d = i4;
        int i5 = i / 2;
        di diVar = this.f412a;
        diVar.m549a();
        diVar.m552c(i4);
        diVar.f453b = i4;
        short[] sArr = diVar.f452a;
        this.f413b = sArr;
        if (m483b(fArr, i, i2)) {
            for (i3 = 0; i3 < i4; i3 = (short) (i3 + 1)) {
                sArr[i3] = (short) (i5 + i3);
            }
        } else {
            int i6 = i4 - 1;
            for (i3 = 0; i3 < i4; i3++) {
                sArr[i3] = (short) ((i5 + i6) - i3);
            }
        }
        de deVar = this.f416e;
        deVar.m532a();
        deVar.m535c(i4);
        for (i3 = 0; i3 < i4; i3++) {
            deVar.m533a(m479a(i3));
        }
        diVar = this.f417f;
        diVar.m549a();
        diVar.m552c(Math.max(0, i4 - 2) * 3);
        m480a();
        return diVar;
    }

    private void m480a() {
        int[] iArr = this.f416e.f440a;
        while (this.f415d > 3) {
            int b = m481b();
            m484c(b);
            int d = m485d(b);
            if (b == this.f415d) {
                b = 0;
            }
            iArr[d] = m479a(d);
            iArr[b] = m479a(b);
        }
        if (this.f415d == 3) {
            di diVar = this.f417f;
            short[] sArr = this.f413b;
            diVar.m550a(sArr[0]);
            diVar.m550a(sArr[1]);
            diVar.m550a(sArr[2]);
        }
    }

    private int m479a(int i) {
        short[] sArr = this.f413b;
        int i2 = sArr[m485d(i)] * 2;
        int i3 = sArr[i] * 2;
        int i4 = sArr[m486e(i)] * 2;
        float[] fArr = this.f414c;
        return m478a(fArr[i2], fArr[i2 + 1], fArr[i3], fArr[i3 + 1], fArr[i4], fArr[i4 + 1]);
    }

    private int m481b() {
        int i;
        int i2 = this.f415d;
        for (i = 0; i < i2; i++) {
            if (m482b(i)) {
                return i;
            }
        }
        int[] iArr = this.f416e.f440a;
        for (i = 0; i < i2; i++) {
            if (iArr[i] != -1) {
                return i;
            }
        }
        return 0;
    }

    private boolean m482b(int i) {
        int[] iArr = this.f416e.f440a;
        if (iArr[i] == -1) {
            return false;
        }
        int d = m485d(i);
        int e = m486e(i);
        short[] sArr = this.f413b;
        int i2 = sArr[d] * 2;
        int i3 = sArr[i] * 2;
        int i4 = sArr[e] * 2;
        float[] fArr = this.f414c;
        float f = fArr[i2];
        float f2 = fArr[i2 + 1];
        float f3 = fArr[i3];
        float f4 = fArr[i3 + 1];
        float f5 = fArr[i4];
        float f6 = fArr[i4 + 1];
        int e2 = m486e(e);
        while (e2 != d) {
            if (iArr[e2] != 1) {
                i4 = sArr[e2] * 2;
                float f7 = fArr[i4];
                float f8 = fArr[i4 + 1];
                if (m478a(f5, f6, f, f2, f7, f8) >= 0 && m478a(f, f2, f3, f4, f7, f8) >= 0 && m478a(f3, f4, f5, f6, f7, f8) >= 0) {
                    return false;
                }
            }
            e2 = m486e(e2);
        }
        return true;
    }

    private void m484c(int i) {
        short[] sArr = this.f413b;
        di diVar = this.f417f;
        diVar.m550a(sArr[m485d(i)]);
        diVar.m550a(sArr[i]);
        diVar.m550a(sArr[m486e(i)]);
        this.f412a.m551b(i);
        this.f416e.m534b(i);
        this.f415d--;
    }

    private int m485d(int i) {
        if (i == 0) {
            i = this.f415d;
        }
        return i - 1;
    }

    private int m486e(int i) {
        return (i + 1) % this.f415d;
    }

    private static boolean m483b(float[] fArr, int i, int i2) {
        if (i2 <= 2) {
            return false;
        }
        int i3 = (i + i2) - 3;
        float f = 0.0f;
        for (int i4 = i; i4 < i3; i4 += 2) {
            f += (fArr[i4] * fArr[i4 + 3]) - (fArr[i4 + 1] * fArr[i4 + 2]);
        }
        float f2 = fArr[(i + i2) - 2];
        float f3 = fArr[(i + i2) - 1];
        if (((f2 * fArr[i + 1]) + f) - (fArr[i] * f3) < 0.0f) {
            return true;
        }
        return false;
    }

    private static int m478a(float f, float f2, float f3, float f4, float f5, float f6) {
        return (int) Math.signum((((f6 - f4) * f) + ((f2 - f6) * f3)) + ((f4 - f2) * f5));
    }
}
