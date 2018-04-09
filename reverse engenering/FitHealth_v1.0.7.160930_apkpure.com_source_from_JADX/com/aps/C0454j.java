package com.aps;

/* compiled from: Fence */
public class C0454j {
    public double f1894a = 0.0d;
    public double f1895b = 0.0d;
    public float f1896c = 0.0f;
    int f1897d = -1;
    private long f1898e = -1;

    public long m1953a() {
        return this.f1898e;
    }

    public void m1954a(long j) {
        if (j >= 0) {
            this.f1898e = C0470t.m2006a() + j;
        } else {
            this.f1898e = j;
        }
    }

    public String m1955b() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.f1894a).append("#").append(this.f1895b).append("#").append(this.f1896c);
        return stringBuilder.toString();
    }
}
