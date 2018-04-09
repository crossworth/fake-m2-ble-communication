package p031u.aly;

import android.support.v4.media.TransportMediator;
import com.umeng.socialize.common.SocializeConstants;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;

/* compiled from: TCompactProtocol */
public class ci extends co {
    private static final ct f5169d = new ct("");
    private static final cj f5170e = new cj("", (byte) 0, (short) 0);
    private static final byte[] f5171f = new byte[16];
    private static final byte f5172h = (byte) -126;
    private static final byte f5173i = (byte) 1;
    private static final byte f5174j = (byte) 31;
    private static final byte f5175k = (byte) -32;
    private static final int f5176l = 5;
    byte[] f5177a;
    byte[] f5178b;
    byte[] f5179c;
    private bn f5180m;
    private short f5181n;
    private cj f5182o;
    private Boolean f5183p;
    private final long f5184q;
    private byte[] f5185r;

    /* compiled from: TCompactProtocol */
    private static class C1492b {
        public static final byte f3739a = (byte) 1;
        public static final byte f3740b = (byte) 2;
        public static final byte f3741c = (byte) 3;
        public static final byte f3742d = (byte) 4;
        public static final byte f3743e = (byte) 5;
        public static final byte f3744f = (byte) 6;
        public static final byte f3745g = (byte) 7;
        public static final byte f3746h = (byte) 8;
        public static final byte f3747i = (byte) 9;
        public static final byte f3748j = (byte) 10;
        public static final byte f3749k = (byte) 11;
        public static final byte f3750l = (byte) 12;

        private C1492b() {
        }
    }

    /* compiled from: TCompactProtocol */
    public static class C1948a implements cq {
        private final long f5168a;

        public C1948a() {
            this.f5168a = -1;
        }

        public C1948a(int i) {
            this.f5168a = (long) i;
        }

        public co mo2770a(dc dcVar) {
            return new ci(dcVar, this.f5168a);
        }
    }

    static {
        f5171f[0] = (byte) 0;
        f5171f[2] = (byte) 1;
        f5171f[3] = (byte) 3;
        f5171f[6] = (byte) 4;
        f5171f[8] = (byte) 5;
        f5171f[10] = (byte) 6;
        f5171f[4] = (byte) 7;
        f5171f[11] = (byte) 8;
        f5171f[15] = (byte) 9;
        f5171f[14] = (byte) 10;
        f5171f[13] = (byte) 11;
        f5171f[12] = (byte) 12;
    }

    public ci(dc dcVar, long j) {
        super(dcVar);
        this.f5180m = new bn(15);
        this.f5181n = (short) 0;
        this.f5182o = null;
        this.f5183p = null;
        this.f5177a = new byte[5];
        this.f5178b = new byte[10];
        this.f5185r = new byte[1];
        this.f5179c = new byte[1];
        this.f5184q = j;
    }

    public ci(dc dcVar) {
        this(dcVar, -1);
    }

    public void mo2812B() {
        this.f5180m.m3620c();
        this.f5181n = (short) 0;
    }

    public void mo2782a(cm cmVar) throws bv {
        m5541b((byte) f5172h);
        m5549d(((cmVar.f3760b << 5) & -32) | 1);
        mo3684b(cmVar.f3761c);
        mo2777a(cmVar.f3759a);
    }

    public void mo2784a(ct ctVar) throws bv {
        this.f5180m.m3618a(this.f5181n);
        this.f5181n = (short) 0;
    }

    public void mo2787b() throws bv {
        this.f5181n = this.f5180m.m3617a();
    }

    public void mo2779a(cj cjVar) throws bv {
        if (cjVar.f3752b == (byte) 2) {
            this.f5182o = cjVar;
        } else {
            m5539a(cjVar, (byte) -1);
        }
    }

    private void m5539a(cj cjVar, byte b) throws bv {
        if (b == (byte) -1) {
            b = m5550e(cjVar.f3752b);
        }
        if (cjVar.f3753c <= this.f5181n || cjVar.f3753c - this.f5181n > 15) {
            m5541b(b);
            mo2785a(cjVar.f3753c);
        } else {
            m5549d(((cjVar.f3753c - this.f5181n) << 4) | b);
        }
        this.f5181n = cjVar.f3753c;
    }

    public void mo2789d() throws bv {
        m5541b((byte) 0);
    }

    public void mo2781a(cl clVar) throws bv {
        if (clVar.f3758c == 0) {
            m5549d(0);
            return;
        }
        mo3684b(clVar.f3758c);
        m5549d((m5550e(clVar.f3756a) << 4) | m5550e(clVar.f3757b));
    }

    public void mo2780a(ck ckVar) throws bv {
        m5558a(ckVar.f3754a, ckVar.f3755b);
    }

    public void mo2783a(cs csVar) throws bv {
        m5558a(csVar.f3768a, csVar.f3769b);
    }

    public void mo2786a(boolean z) throws bv {
        byte b = (byte) 1;
        if (this.f5182o != null) {
            cj cjVar = this.f5182o;
            if (!z) {
                b = (byte) 2;
            }
            m5539a(cjVar, b);
            this.f5182o = null;
            return;
        }
        if (!z) {
            b = (byte) 2;
        }
        m5541b(b);
    }

    public void mo2773a(byte b) throws bv {
        m5541b(b);
    }

    public void mo2785a(short s) throws bv {
        mo3684b(m5544c((int) s));
    }

    public void mo2775a(int i) throws bv {
        mo3684b(m5544c(i));
    }

    public void mo2776a(long j) throws bv {
        m5543b(m5545c(j));
    }

    public void mo2774a(double d) throws bv {
        byte[] bArr = new byte[]{(byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0, (byte) 0};
        m5538a(Double.doubleToLongBits(d), bArr, 0);
        this.g.m3748b(bArr);
    }

    public void mo2777a(String str) throws bv {
        try {
            byte[] bytes = str.getBytes("UTF-8");
            m5540a(bytes, 0, bytes.length);
        } catch (UnsupportedEncodingException e) {
            throw new bv("UTF-8 not supported!");
        }
    }

    public void mo2778a(ByteBuffer byteBuffer) throws bv {
        m5540a(byteBuffer.array(), byteBuffer.position() + byteBuffer.arrayOffset(), byteBuffer.limit() - byteBuffer.position());
    }

    private void m5540a(byte[] bArr, int i, int i2) throws bv {
        mo3684b(i2);
        this.g.mo2816b(bArr, i, i2);
    }

    public void mo2772a() throws bv {
    }

    public void mo2790e() throws bv {
    }

    public void mo2791f() throws bv {
    }

    public void mo2792g() throws bv {
    }

    public void mo2788c() throws bv {
    }

    protected void m5558a(byte b, int i) throws bv {
        if (i <= 14) {
            m5549d((i << 4) | m5550e(b));
            return;
        }
        m5549d(m5550e(b) | SocializeConstants.MASK_USER_CENTER_HIDE_AREA);
        mo3684b(i);
    }

    private void mo3684b(int i) throws bv {
        int i2 = 0;
        while ((i & -128) != 0) {
            int i3 = i2 + 1;
            this.f5177a[i2] = (byte) ((i & TransportMediator.KEYCODE_MEDIA_PAUSE) | 128);
            i >>>= 7;
            i2 = i3;
        }
        int i4 = i2 + 1;
        this.f5177a[i2] = (byte) i;
        this.g.mo2816b(this.f5177a, 0, i4);
    }

    private void m5543b(long j) throws bv {
        int i = 0;
        while ((-128 & j) != 0) {
            int i2 = i + 1;
            this.f5178b[i] = (byte) ((int) ((127 & j) | 128));
            j >>>= 7;
            i = i2;
        }
        int i3 = i + 1;
        this.f5178b[i] = (byte) ((int) j);
        this.g.mo2816b(this.f5178b, 0, i3);
    }

    private long m5545c(long j) {
        return (j << 1) ^ (j >> 63);
    }

    private int m5544c(int i) {
        return (i << 1) ^ (i >> 31);
    }

    private void m5538a(long j, byte[] bArr, int i) {
        bArr[i + 0] = (byte) ((int) (j & 255));
        bArr[i + 1] = (byte) ((int) ((j >> 8) & 255));
        bArr[i + 2] = (byte) ((int) ((j >> 16) & 255));
        bArr[i + 3] = (byte) ((int) ((j >> 24) & 255));
        bArr[i + 4] = (byte) ((int) ((j >> 32) & 255));
        bArr[i + 5] = (byte) ((int) ((j >> 40) & 255));
        bArr[i + 6] = (byte) ((int) ((j >> 48) & 255));
        bArr[i + 7] = (byte) ((int) ((j >> 56) & 255));
    }

    private void m5541b(byte b) throws bv {
        this.f5185r[0] = b;
        this.g.m3748b(this.f5185r);
    }

    private void m5549d(int i) throws bv {
        m5541b((byte) i);
    }

    public cm mo2793h() throws bv {
        byte u = mo2806u();
        if (u != f5172h) {
            throw new cp("Expected protocol id " + Integer.toHexString(-126) + " but got " + Integer.toHexString(u));
        }
        u = mo2806u();
        byte b = (byte) (u & 31);
        if (b != (byte) 1) {
            throw new cp("Expected version 1 but got " + b);
        }
        return new cm(mo2811z(), (byte) ((u >> 5) & 3), m5535E());
    }

    public ct mo2795j() throws bv {
        this.f5180m.m3618a(this.f5181n);
        this.f5181n = (short) 0;
        return f5169d;
    }

    public void mo2796k() throws bv {
        this.f5181n = this.f5180m.m3617a();
    }

    public cj mo2797l() throws bv {
        byte u = mo2806u();
        if (u == (byte) 0) {
            return f5170e;
        }
        short s = (short) ((u & SocializeConstants.MASK_USER_CENTER_HIDE_AREA) >> 4);
        if (s == (short) 0) {
            s = mo2807v();
        } else {
            s = (short) (s + this.f5181n);
        }
        cj cjVar = new cj("", m5547d((byte) (u & 15)), s);
        if (m5546c(u)) {
            this.f5183p = ((byte) (u & 15)) == (byte) 1 ? Boolean.TRUE : Boolean.FALSE;
        }
        this.f5181n = cjVar.f3753c;
        return cjVar;
    }

    public cl mo2799n() throws bv {
        int E = m5535E();
        int u = E == 0 ? 0 : mo2806u();
        return new cl(m5547d((byte) (u >> 4)), m5547d((byte) (u & 15)), E);
    }

    public ck mo2801p() throws bv {
        byte u = mo2806u();
        int i = (u >> 4) & 15;
        if (i == 15) {
            i = m5535E();
        }
        return new ck(m5547d(u), i);
    }

    public cs mo2803r() throws bv {
        return new cs(mo2801p());
    }

    public boolean mo2805t() throws bv {
        if (this.f5183p != null) {
            boolean booleanValue = this.f5183p.booleanValue();
            this.f5183p = null;
            return booleanValue;
        } else if (mo2806u() != (byte) 1) {
            return false;
        } else {
            return true;
        }
    }

    public byte mo2806u() throws bv {
        if (this.g.mo2822h() > 0) {
            byte b = this.g.mo2820f()[this.g.mo2821g()];
            this.g.mo2819a(1);
            return b;
        }
        this.g.m3751d(this.f5179c, 0, 1);
        return this.f5179c[0];
    }

    public short mo2807v() throws bv {
        return (short) m5553g(m5535E());
    }

    public int mo2808w() throws bv {
        return m5553g(m5535E());
    }

    public long mo2809x() throws bv {
        return m5548d(m5536F());
    }

    public double mo2810y() throws bv {
        byte[] bArr = new byte[8];
        this.g.m3751d(bArr, 0, 8);
        return Double.longBitsToDouble(m5537a(bArr));
    }

    public String mo2811z() throws bv {
        int E = m5535E();
        m5552f(E);
        if (E == 0) {
            return "";
        }
        try {
            if (this.g.mo2822h() < E) {
                return new String(m5551e(E), "UTF-8");
            }
            String str = new String(this.g.mo2820f(), this.g.mo2821g(), E, "UTF-8");
            this.g.mo2819a(E);
            return str;
        } catch (UnsupportedEncodingException e) {
            throw new bv("UTF-8 not supported!");
        }
    }

    public ByteBuffer mo2771A() throws bv {
        int E = m5535E();
        m5552f(E);
        if (E == 0) {
            return ByteBuffer.wrap(new byte[0]);
        }
        byte[] bArr = new byte[E];
        this.g.m3751d(bArr, 0, E);
        return ByteBuffer.wrap(bArr);
    }

    private byte[] m5551e(int i) throws bv {
        if (i == 0) {
            return new byte[0];
        }
        byte[] bArr = new byte[i];
        this.g.m3751d(bArr, 0, i);
        return bArr;
    }

    private void m5552f(int i) throws cp {
        if (i < 0) {
            throw new cp("Negative length: " + i);
        } else if (this.f5184q != -1 && ((long) i) > this.f5184q) {
            throw new cp("Length exceeded max allowed: " + i);
        }
    }

    public void mo2794i() throws bv {
    }

    public void mo2798m() throws bv {
    }

    public void mo2800o() throws bv {
    }

    public void mo2802q() throws bv {
    }

    public void mo2804s() throws bv {
    }

    private int m5535E() throws bv {
        int i = 0;
        int i2;
        if (this.g.mo2822h() >= 5) {
            byte[] f = this.g.mo2820f();
            int g = this.g.mo2821g();
            i2 = 0;
            int i3 = 0;
            while (true) {
                byte b = f[g + i];
                i3 |= (b & TransportMediator.KEYCODE_MEDIA_PAUSE) << i2;
                if ((b & 128) != 128) {
                    this.g.mo2819a(i + 1);
                    return i3;
                }
                i2 += 7;
                i++;
            }
        } else {
            i2 = 0;
            while (true) {
                byte u = mo2806u();
                i2 |= (u & TransportMediator.KEYCODE_MEDIA_PAUSE) << i;
                if ((u & 128) != 128) {
                    return i2;
                }
                i += 7;
            }
        }
    }

    private long m5536F() throws bv {
        long j = null;
        long j2 = 0;
        if (this.g.mo2822h() >= 10) {
            int i;
            byte[] f = this.g.mo2820f();
            int g = this.g.mo2821g();
            long j3 = 0;
            while (true) {
                byte b = f[g + i];
                j2 |= ((long) (b & TransportMediator.KEYCODE_MEDIA_PAUSE)) << j3;
                if ((b & 128) != 128) {
                    break;
                }
                j3 += 7;
                i++;
            }
            this.g.mo2819a(i + 1);
        } else {
            while (true) {
                byte u = mo2806u();
                j2 |= ((long) (u & TransportMediator.KEYCODE_MEDIA_PAUSE)) << j;
                if ((u & 128) != 128) {
                    break;
                }
                j += 7;
            }
        }
        return j2;
    }

    private int m5553g(int i) {
        return (i >>> 1) ^ (-(i & 1));
    }

    private long m5548d(long j) {
        return (j >>> 1) ^ (-(1 & j));
    }

    private long m5537a(byte[] bArr) {
        return ((((((((((long) bArr[7]) & 255) << 56) | ((((long) bArr[6]) & 255) << 48)) | ((((long) bArr[5]) & 255) << 40)) | ((((long) bArr[4]) & 255) << 32)) | ((((long) bArr[3]) & 255) << 24)) | ((((long) bArr[2]) & 255) << 16)) | ((((long) bArr[1]) & 255) << 8)) | (((long) bArr[0]) & 255);
    }

    private boolean m5546c(byte b) {
        int i = b & 15;
        if (i == 1 || i == 2) {
            return true;
        }
        return false;
    }

    private byte m5547d(byte b) throws cp {
        switch ((byte) (b & 15)) {
            case (byte) 0:
                return (byte) 0;
            case (byte) 1:
            case (byte) 2:
                return (byte) 2;
            case (byte) 3:
                return (byte) 3;
            case (byte) 4:
                return (byte) 6;
            case (byte) 5:
                return (byte) 8;
            case (byte) 6:
                return (byte) 10;
            case (byte) 7:
                return (byte) 4;
            case (byte) 8:
                return (byte) 11;
            case (byte) 9:
                return cv.f3783m;
            case (byte) 10:
                return cv.f3782l;
            case (byte) 11:
                return cv.f3781k;
            case (byte) 12:
                return (byte) 12;
            default:
                throw new cp("don't know what type: " + ((byte) (b & 15)));
        }
    }

    private byte m5550e(byte b) {
        return f5171f[b];
    }
}
