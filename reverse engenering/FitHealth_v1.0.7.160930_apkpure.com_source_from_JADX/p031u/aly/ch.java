package p031u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* compiled from: TBinaryProtocol */
public class ch extends co {
    protected static final int f5153a = -65536;
    protected static final int f5154b = -2147418112;
    private static final ct f5155h = new ct();
    protected boolean f5156c;
    protected boolean f5157d;
    protected int f5158e;
    protected boolean f5159f;
    private byte[] f5160i;
    private byte[] f5161j;
    private byte[] f5162k;
    private byte[] f5163l;
    private byte[] f5164m;
    private byte[] f5165n;
    private byte[] f5166o;
    private byte[] f5167p;

    /* compiled from: TBinaryProtocol */
    public static class C1947a implements cq {
        protected boolean f5150a;
        protected boolean f5151b;
        protected int f5152c;

        public C1947a() {
            this(false, true);
        }

        public C1947a(boolean z, boolean z2) {
            this(z, z2, 0);
        }

        public C1947a(boolean z, boolean z2, int i) {
            this.f5150a = false;
            this.f5151b = true;
            this.f5150a = z;
            this.f5151b = z2;
            this.f5152c = i;
        }

        public co mo2770a(dc dcVar) {
            co chVar = new ch(dcVar, this.f5150a, this.f5151b);
            if (this.f5152c != 0) {
                chVar.m5509c(this.f5152c);
            }
            return chVar;
        }
    }

    public ch(dc dcVar) {
        this(dcVar, false, true);
    }

    public ch(dc dcVar, boolean z, boolean z2) {
        super(dcVar);
        this.f5156c = false;
        this.f5157d = true;
        this.f5159f = false;
        this.f5160i = new byte[1];
        this.f5161j = new byte[2];
        this.f5162k = new byte[4];
        this.f5163l = new byte[8];
        this.f5164m = new byte[1];
        this.f5165n = new byte[2];
        this.f5166o = new byte[4];
        this.f5167p = new byte[8];
        this.f5156c = z;
        this.f5157d = z2;
    }

    public void mo2782a(cm cmVar) throws bv {
        if (this.f5157d) {
            mo2775a(f5154b | cmVar.f3760b);
            mo2777a(cmVar.f3759a);
            mo2775a(cmVar.f3761c);
            return;
        }
        mo2777a(cmVar.f3759a);
        mo2773a(cmVar.f3760b);
        mo2775a(cmVar.f3761c);
    }

    public void mo2772a() {
    }

    public void mo2784a(ct ctVar) {
    }

    public void mo2787b() {
    }

    public void mo2779a(cj cjVar) throws bv {
        mo2773a(cjVar.f3752b);
        mo2785a(cjVar.f3753c);
    }

    public void mo2788c() {
    }

    public void mo2789d() throws bv {
        mo2773a((byte) 0);
    }

    public void mo2781a(cl clVar) throws bv {
        mo2773a(clVar.f3756a);
        mo2773a(clVar.f3757b);
        mo2775a(clVar.f3758c);
    }

    public void mo2790e() {
    }

    public void mo2780a(ck ckVar) throws bv {
        mo2773a(ckVar.f3754a);
        mo2775a(ckVar.f3755b);
    }

    public void mo2791f() {
    }

    public void mo2783a(cs csVar) throws bv {
        mo2773a(csVar.f3768a);
        mo2775a(csVar.f3769b);
    }

    public void mo2792g() {
    }

    public void mo2786a(boolean z) throws bv {
        mo2773a(z ? (byte) 1 : (byte) 0);
    }

    public void mo2773a(byte b) throws bv {
        this.f5160i[0] = b;
        this.g.mo2816b(this.f5160i, 0, 1);
    }

    public void mo2785a(short s) throws bv {
        this.f5161j[0] = (byte) ((s >> 8) & 255);
        this.f5161j[1] = (byte) (s & 255);
        this.g.mo2816b(this.f5161j, 0, 2);
    }

    public void mo2775a(int i) throws bv {
        this.f5162k[0] = (byte) ((i >> 24) & 255);
        this.f5162k[1] = (byte) ((i >> 16) & 255);
        this.f5162k[2] = (byte) ((i >> 8) & 255);
        this.f5162k[3] = (byte) (i & 255);
        this.g.mo2816b(this.f5162k, 0, 4);
    }

    public void mo2776a(long j) throws bv {
        this.f5163l[0] = (byte) ((int) ((j >> 56) & 255));
        this.f5163l[1] = (byte) ((int) ((j >> 48) & 255));
        this.f5163l[2] = (byte) ((int) ((j >> 40) & 255));
        this.f5163l[3] = (byte) ((int) ((j >> 32) & 255));
        this.f5163l[4] = (byte) ((int) ((j >> 24) & 255));
        this.f5163l[5] = (byte) ((int) ((j >> 16) & 255));
        this.f5163l[6] = (byte) ((int) ((j >> 8) & 255));
        this.f5163l[7] = (byte) ((int) (255 & j));
        this.g.mo2816b(this.f5163l, 0, 8);
    }

    public void mo2774a(double d) throws bv {
        mo2776a(Double.doubleToLongBits(d));
    }

    public void mo2777a(String str) throws bv {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            mo2775a(bytes.length);
            this.g.mo2816b(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new bv("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    public void mo2778a(ByteBuffer byteBuffer) throws bv {
        int limit = byteBuffer.limit() - byteBuffer.position();
        mo2775a(limit);
        this.g.mo2816b(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), limit);
    }

    public cm mo2793h() throws bv {
        int w = mo2808w();
        if (w < 0) {
            if ((-65536 & w) == f5154b) {
                return new cm(mo2811z(), (byte) (w & 255), mo2808w());
            }
            throw new cp(4, "Bad version in readMessageBegin");
        } else if (!this.f5156c) {
            return new cm(m5506b(w), mo2806u(), mo2808w());
        } else {
            throw new cp(4, "Missing version in readMessageBegin, old client?");
        }
    }

    public void mo2794i() {
    }

    public ct mo2795j() {
        return f5155h;
    }

    public void mo2796k() {
    }

    public cj mo2797l() throws bv {
        byte u = mo2806u();
        return new cj("", u, u == (byte) 0 ? (short) 0 : mo2807v());
    }

    public void mo2798m() {
    }

    public cl mo2799n() throws bv {
        return new cl(mo2806u(), mo2806u(), mo2808w());
    }

    public void mo2800o() {
    }

    public ck mo2801p() throws bv {
        return new ck(mo2806u(), mo2808w());
    }

    public void mo2802q() {
    }

    public cs mo2803r() throws bv {
        return new cs(mo2806u(), mo2808w());
    }

    public void mo2804s() {
    }

    public boolean mo2805t() throws bv {
        return mo2806u() == (byte) 1;
    }

    public byte mo2806u() throws bv {
        if (this.g.mo2822h() >= 1) {
            byte b = this.g.mo2820f()[this.g.mo2821g()];
            this.g.mo2819a(1);
            return b;
        }
        m5489a(this.f5164m, 0, 1);
        return this.f5164m[0];
    }

    public short mo2807v() throws bv {
        int i = 0;
        byte[] bArr = this.f5165n;
        if (this.g.mo2822h() >= 2) {
            bArr = this.g.mo2820f();
            i = this.g.mo2821g();
            this.g.mo2819a(2);
        } else {
            m5489a(this.f5165n, 0, 2);
        }
        return (short) ((bArr[i + 1] & 255) | ((bArr[i] & 255) << 8));
    }

    public int mo2808w() throws bv {
        int i = 0;
        byte[] bArr = this.f5166o;
        if (this.g.mo2822h() >= 4) {
            bArr = this.g.mo2820f();
            i = this.g.mo2821g();
            this.g.mo2819a(4);
        } else {
            m5489a(this.f5166o, 0, 4);
        }
        return (bArr[i + 3] & 255) | ((((bArr[i] & 255) << 24) | ((bArr[i + 1] & 255) << 16)) | ((bArr[i + 2] & 255) << 8));
    }

    public long mo2809x() throws bv {
        int i = 0;
        byte[] bArr = this.f5167p;
        if (this.g.mo2822h() >= 8) {
            bArr = this.g.mo2820f();
            i = this.g.mo2821g();
            this.g.mo2819a(8);
        } else {
            m5489a(this.f5167p, 0, 8);
        }
        return ((long) (bArr[i + 7] & 255)) | (((((((((long) (bArr[i] & 255)) << 56) | (((long) (bArr[i + 1] & 255)) << 48)) | (((long) (bArr[i + 2] & 255)) << 40)) | (((long) (bArr[i + 3] & 255)) << 32)) | (((long) (bArr[i + 4] & 255)) << 24)) | (((long) (bArr[i + 5] & 255)) << 16)) | (((long) (bArr[i + 6] & 255)) << 8));
    }

    public double mo2810y() throws bv {
        return Double.longBitsToDouble(mo2809x());
    }

    public String mo2811z() throws bv {
        int w = mo2808w();
        if (this.g.mo2822h() < w) {
            return m5506b(w);
        }
        try {
            String str = new String(this.g.mo2820f(), this.g.mo2821g(), w, "UTF-8");
            this.g.mo2819a(w);
            return str;
        } catch (UnsupportedEncodingException e) {
            throw new bv("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    public String m5506b(int i) throws bv {
        try {
            m5511d(i);
            byte[] bArr = new byte[i];
            this.g.m3751d(bArr, 0, i);
            return new String(bArr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new bv("JVM DOES NOT SUPPORT UTF-8");
        }
    }

    public ByteBuffer mo2771A() throws bv {
        int w = mo2808w();
        m5511d(w);
        if (this.g.mo2822h() >= w) {
            ByteBuffer wrap = ByteBuffer.wrap(this.g.mo2820f(), this.g.mo2821g(), w);
            this.g.mo2819a(w);
            return wrap;
        }
        byte[] bArr = new byte[w];
        this.g.m3751d(bArr, 0, w);
        return ByteBuffer.wrap(bArr);
    }

    private int m5489a(byte[] bArr, int i, int i2) throws bv {
        m5511d(i2);
        return this.g.m3751d(bArr, i, i2);
    }

    public void m5509c(int i) {
        this.f5158e = i;
        this.f5159f = true;
    }

    protected void m5511d(int i) throws bv {
        if (i < 0) {
            throw new cp("Negative length: " + i);
        } else if (this.f5159f) {
            this.f5158e -= i;
            if (this.f5158e < 0) {
                throw new cp("Message length exceeded: " + i);
            }
        }
    }
}
