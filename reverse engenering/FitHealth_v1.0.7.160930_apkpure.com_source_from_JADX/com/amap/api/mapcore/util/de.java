package com.amap.api.mapcore.util;

/* compiled from: IntArray */
public class de {
    public int[] f440a;
    public int f441b;
    public boolean f442c;

    public de() {
        this(true, 16);
    }

    public de(boolean z, int i) {
        this.f442c = z;
        this.f440a = new int[i];
    }

    public void m533a(int i) {
        int[] iArr = this.f440a;
        if (this.f441b == iArr.length) {
            iArr = m536d(Math.max(8, (int) (((float) this.f441b) * 1.75f)));
        }
        int i2 = this.f441b;
        this.f441b = i2 + 1;
        iArr[i2] = i;
    }

    public int m534b(int i) {
        if (i >= this.f441b) {
            throw new IndexOutOfBoundsException("index can't be >= size: " + i + " >= " + this.f441b);
        }
        Object obj = this.f440a;
        int i2 = obj[i];
        this.f441b--;
        if (this.f442c) {
            System.arraycopy(obj, i + 1, obj, i, this.f441b - i);
        } else {
            obj[i] = obj[this.f441b];
        }
        return i2;
    }

    public void m532a() {
        this.f441b = 0;
    }

    public int[] m535c(int i) {
        int i2 = this.f441b + i;
        if (i2 > this.f440a.length) {
            m536d(Math.max(8, i2));
        }
        return this.f440a;
    }

    protected int[] m536d(int i) {
        Object obj = new int[i];
        System.arraycopy(this.f440a, 0, obj, 0, Math.min(this.f441b, obj.length));
        this.f440a = obj;
        return obj;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof de)) {
            return false;
        }
        de deVar = (de) obj;
        int i = this.f441b;
        if (i != deVar.f441b) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (this.f440a[i2] != deVar.f440a[i2]) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        if (this.f441b == 0) {
            return "[]";
        }
        int[] iArr = this.f440a;
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append('[');
        stringBuilder.append(iArr[0]);
        for (int i = 1; i < this.f441b; i++) {
            stringBuilder.append(", ");
            stringBuilder.append(iArr[i]);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}
