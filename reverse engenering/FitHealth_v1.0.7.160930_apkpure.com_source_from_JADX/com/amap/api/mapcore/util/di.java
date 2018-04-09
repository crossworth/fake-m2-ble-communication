package com.amap.api.mapcore.util;

/* compiled from: ShortArray */
public class di {
    public short[] f452a;
    public int f453b;
    public boolean f454c;

    public di() {
        this(true, 16);
    }

    public di(boolean z, int i) {
        this.f454c = z;
        this.f452a = new short[i];
    }

    public void m550a(short s) {
        short[] sArr = this.f452a;
        if (this.f453b == sArr.length) {
            sArr = m553d(Math.max(8, (int) (((float) this.f453b) * 1.75f)));
        }
        int i = this.f453b;
        this.f453b = i + 1;
        sArr[i] = s;
    }

    public short m548a(int i) {
        if (i < this.f453b) {
            return this.f452a[i];
        }
        throw new IndexOutOfBoundsException("index can't be >= size: " + i + " >= " + this.f453b);
    }

    public short m551b(int i) {
        if (i >= this.f453b) {
            throw new IndexOutOfBoundsException("index can't be >= size: " + i + " >= " + this.f453b);
        }
        Object obj = this.f452a;
        short s = obj[i];
        this.f453b--;
        if (this.f454c) {
            System.arraycopy(obj, i + 1, obj, i, this.f453b - i);
        } else {
            obj[i] = obj[this.f453b];
        }
        return s;
    }

    public void m549a() {
        this.f453b = 0;
    }

    public short[] m552c(int i) {
        int i2 = this.f453b + i;
        if (i2 > this.f452a.length) {
            m553d(Math.max(8, i2));
        }
        return this.f452a;
    }

    protected short[] m553d(int i) {
        Object obj = new short[i];
        System.arraycopy(this.f452a, 0, obj, 0, Math.min(this.f453b, obj.length));
        this.f452a = obj;
        return obj;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof di)) {
            return false;
        }
        di diVar = (di) obj;
        int i = this.f453b;
        if (i != diVar.f453b) {
            return false;
        }
        for (int i2 = 0; i2 < i; i2++) {
            if (this.f452a[i2] != diVar.f452a[i2]) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        if (this.f453b == 0) {
            return "[]";
        }
        short[] sArr = this.f452a;
        StringBuilder stringBuilder = new StringBuilder(32);
        stringBuilder.append('[');
        stringBuilder.append(sArr[0]);
        for (int i = 1; i < this.f453b; i++) {
            stringBuilder.append(", ");
            stringBuilder.append(sArr[i]);
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }
}
