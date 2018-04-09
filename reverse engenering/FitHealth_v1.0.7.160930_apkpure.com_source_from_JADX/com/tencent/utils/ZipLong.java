package com.tencent.utils;

import android.support.v4.view.MotionEventCompat;

/* compiled from: ProGuard */
public final class ZipLong implements Cloneable {
    private long f2999a;

    public ZipLong(byte[] bArr) {
        this(bArr, 0);
    }

    public ZipLong(byte[] bArr, int i) {
        this.f2999a = ((long) (bArr[i + 3] << 24)) & 4278190080L;
        this.f2999a += (long) ((bArr[i + 2] << 16) & 16711680);
        this.f2999a += (long) ((bArr[i + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        this.f2999a += (long) (bArr[i] & 255);
    }

    public ZipLong(long j) {
        this.f2999a = j;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ZipLong) && this.f2999a == ((ZipLong) obj).getValue()) {
            return true;
        }
        return false;
    }

    public byte[] getBytes() {
        return new byte[]{(byte) ((int) (this.f2999a & 255)), (byte) ((int) ((this.f2999a & 65280) >> 8)), (byte) ((int) ((this.f2999a & 16711680) >> 16)), (byte) ((int) ((this.f2999a & 4278190080L) >> 24))};
    }

    public long getValue() {
        return this.f2999a;
    }

    public int hashCode() {
        return (int) this.f2999a;
    }
}
