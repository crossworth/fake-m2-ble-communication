package com.autonavi.amap.mapcore;

/* compiled from: VTMCDataCache */
class C0482f {
    byte[] f2045a;
    String f2046b;
    int f2047c;
    String f2048d;
    int f2049e;

    public C0482f(byte[] bArr) {
        try {
            this.f2047c = (int) (System.currentTimeMillis() / 1000);
            byte b = bArr[4];
            this.f2046b = new String(bArr, 5, b);
            int i = b + 5;
            int i2 = i + 1;
            b = bArr[i];
            this.f2048d = new String(bArr, i2, b);
            i = b + i2;
            this.f2049e = Convert.getInt(bArr, i);
            i += 4;
            this.f2045a = bArr;
        } catch (Exception e) {
            this.f2045a = null;
        }
    }

    public void m2083a(int i) {
        if (this.f2045a != null) {
            this.f2047c = (int) (System.currentTimeMillis() / 1000);
            int i2 = this.f2045a[4] + 5;
            Convert.writeInt(this.f2045a, this.f2045a[i2] + (i2 + 1), i);
            this.f2049e = i;
        }
    }
}
