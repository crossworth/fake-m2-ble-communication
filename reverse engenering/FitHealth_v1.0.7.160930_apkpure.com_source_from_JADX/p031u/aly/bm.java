package p031u.aly;

/* compiled from: EncodingUtils */
public class bm {
    public static final void m3606a(int i, byte[] bArr) {
        bm.m3607a(i, bArr, 0);
    }

    public static final void m3607a(int i, byte[] bArr, int i2) {
        bArr[i2] = (byte) ((i >> 24) & 255);
        bArr[i2 + 1] = (byte) ((i >> 16) & 255);
        bArr[i2 + 2] = (byte) ((i >> 8) & 255);
        bArr[i2 + 3] = (byte) (i & 255);
    }

    public static final int m3602a(byte[] bArr) {
        return bm.m3603a(bArr, 0);
    }

    public static final int m3603a(byte[] bArr, int i) {
        return ((((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16)) | ((bArr[i + 2] & 255) << 8)) | (bArr[i + 3] & 255);
    }

    public static final boolean m3608a(byte b, int i) {
        return bm.m3609a((int) b, i);
    }

    public static final boolean m3611a(short s, int i) {
        return bm.m3609a((int) s, i);
    }

    public static final boolean m3609a(int i, int i2) {
        return ((1 << i2) & i) != 0;
    }

    public static final boolean m3610a(long j, int i) {
        return ((1 << i) & j) != 0;
    }

    public static final byte m3612b(byte b, int i) {
        return (byte) bm.m3613b((int) b, i);
    }

    public static final short m3615b(short s, int i) {
        return (short) bm.m3613b((int) s, i);
    }

    public static final int m3613b(int i, int i2) {
        return ((1 << i2) ^ -1) & i;
    }

    public static final long m3614b(long j, int i) {
        return ((1 << i) ^ -1) & j;
    }

    public static final byte m3600a(byte b, int i, boolean z) {
        return (byte) bm.m3601a((int) b, i, z);
    }

    public static final short m3605a(short s, int i, boolean z) {
        return (short) bm.m3601a((int) s, i, z);
    }

    public static final int m3601a(int i, int i2, boolean z) {
        if (z) {
            return (1 << i2) | i;
        }
        return bm.m3613b(i, i2);
    }

    public static final long m3604a(long j, int i, boolean z) {
        if (z) {
            return (1 << i) | j;
        }
        return bm.m3614b(j, i);
    }
}
