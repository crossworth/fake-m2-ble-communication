package p031u.aly;

/* compiled from: TTransport */
public abstract class dc {
    public abstract int mo2813a(byte[] bArr, int i, int i2) throws dd;

    public abstract boolean mo2814a();

    public abstract void mo2815b() throws dd;

    public abstract void mo2816b(byte[] bArr, int i, int i2) throws dd;

    public abstract void mo2817c();

    public boolean m3756i() {
        return mo2814a();
    }

    public int m3751d(byte[] bArr, int i, int i2) throws dd {
        int i3 = 0;
        while (i3 < i2) {
            int a = mo2813a(bArr, i + i3, i2 - i3);
            if (a <= 0) {
                throw new dd("Cannot read. Remote side has closed. Tried to read " + i2 + " bytes, but only got " + i3 + " bytes. (This is often indicative of an internal error on the server side. Please check your server logs.)");
            }
            i3 += a;
        }
        return i3;
    }

    public void m3748b(byte[] bArr) throws dd {
        mo2816b(bArr, 0, bArr.length);
    }

    public void mo2818d() throws dd {
    }

    public byte[] mo2820f() {
        return null;
    }

    public int mo2821g() {
        return 0;
    }

    public int mo2822h() {
        return -1;
    }

    public void mo2819a(int i) {
    }
}
