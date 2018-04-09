package com.tencent.open.utils;

import android.support.v4.view.MotionEventCompat;

/* compiled from: ProGuard */
public final class ZipLong implements Cloneable {
    private long f4235a;

    public ZipLong(byte[] bArr) {
        this(bArr, 0);
    }

    public ZipLong(byte[] bArr, int i) {
        this.f4235a = ((long) (bArr[i + 3] << 24)) & 4278190080L;
        this.f4235a += (long) ((bArr[i + 2] << 16) & 16711680);
        this.f4235a += (long) ((bArr[i + 1] << 8) & MotionEventCompat.ACTION_POINTER_INDEX_MASK);
        this.f4235a += (long) (bArr[i] & 255);
    }

    public ZipLong(long j) {
        this.f4235a = j;
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ZipLong) && this.f4235a == ((ZipLong) obj).getValue()) {
            return true;
        }
        return false;
    }

    public byte[] getBytes() {
        return new byte[]{(byte) ((int) (this.f4235a & 255)), (byte) ((int) ((this.f4235a & 65280) >> 8)), (byte) ((int) ((this.f4235a & 16711680) >> 16)), (byte) ((int) ((this.f4235a & 4278190080L) >> 24))};
    }

    public long getValue() {
        return this.f4235a;
    }

    public int hashCode() {
        return (int) this.f4235a;
    }
}
