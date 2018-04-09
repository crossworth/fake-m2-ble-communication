package com.droi.sdk.push.data;

import java.io.Serializable;

public class C0990e implements Serializable {
    private String[] f3279a;
    private boolean f3280b = false;

    public C0990e(String[] strArr) {
        this.f3279a = strArr;
    }

    public void m3045a(boolean z) {
        this.f3280b = z;
    }

    public String[] m3046a() {
        return this.f3279a;
    }

    public boolean m3047b() {
        return this.f3280b;
    }

    public String toString() {
        if (this.f3279a == null || this.f3279a.length == 0) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.f3279a.length; i++) {
            if (i == 0) {
                stringBuffer.append(this.f3279a[0].trim());
            } else {
                stringBuffer.append("," + this.f3279a[i].trim());
            }
        }
        return stringBuffer.toString();
    }
}
