package p031u.aly;

/* compiled from: TMemoryInputTransport */
public final class db extends dc {
    private byte[] f5196a;
    private int f5197b;
    private int f5198c;

    public db(byte[] bArr) {
        m5607a(bArr);
    }

    public db(byte[] bArr, int i, int i2) {
        m5612c(bArr, i, i2);
    }

    public void m5607a(byte[] bArr) {
        m5612c(bArr, 0, bArr.length);
    }

    public void m5612c(byte[] bArr, int i, int i2) {
        this.f5196a = bArr;
        this.f5197b = i;
        this.f5198c = i + i2;
    }

    public void m5613e() {
        this.f5196a = null;
    }

    public void mo2817c() {
    }

    public boolean mo2814a() {
        return true;
    }

    public void mo2815b() throws dd {
    }

    public int mo2813a(byte[] bArr, int i, int i2) throws dd {
        int h = mo2822h();
        if (i2 > h) {
            i2 = h;
        }
        if (i2 > 0) {
            System.arraycopy(this.f5196a, this.f5197b, bArr, i, i2);
            mo2819a(i2);
        }
        return i2;
    }

    public void mo2816b(byte[] bArr, int i, int i2) throws dd {
        throw new UnsupportedOperationException("No writing allowed!");
    }

    public byte[] mo2820f() {
        return this.f5196a;
    }

    public int mo2821g() {
        return this.f5197b;
    }

    public int mo2822h() {
        return this.f5198c - this.f5197b;
    }

    public void mo2819a(int i) {
        this.f5197b += i;
    }
}
