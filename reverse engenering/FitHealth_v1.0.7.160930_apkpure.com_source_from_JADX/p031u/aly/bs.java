package p031u.aly;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import p031u.aly.ci.C1948a;

/* compiled from: TDeserializer */
public class bs {
    private final co f3719a;
    private final db f3720b;

    public bs() {
        this(new C1948a());
    }

    public bs(cq cqVar) {
        this.f3720b = new db();
        this.f3719a = cqVar.mo2770a(this.f3720b);
    }

    public void m3654a(bp bpVar, byte[] bArr) throws bv {
        try {
            this.f3720b.m5607a(bArr);
            bpVar.mo2765a(this.f3719a);
        } finally {
            this.f3720b.m5613e();
            this.f3719a.mo2812B();
        }
    }

    public void m3653a(bp bpVar, String str, String str2) throws bv {
        try {
            m3654a(bpVar, str.getBytes(str2));
            this.f3719a.mo2812B();
        } catch (UnsupportedEncodingException e) {
            throw new bv("JVM DOES NOT SUPPORT ENCODING: " + str2);
        } catch (Throwable th) {
            this.f3719a.mo2812B();
        }
    }

    public void m3655a(bp bpVar, byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        try {
            if (m3650j(bArr, bwVar, bwVarArr) != null) {
                bpVar.mo2765a(this.f3719a);
            }
            this.f3720b.m5613e();
            this.f3719a.mo2812B();
        } catch (Throwable e) {
            throw new bv(e);
        } catch (Throwable th) {
            this.f3720b.m5613e();
            this.f3719a.mo2812B();
        }
    }

    public Boolean m3651a(byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        return (Boolean) m3649a((byte) 2, bArr, bwVar, bwVarArr);
    }

    public Byte m3656b(byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        return (Byte) m3649a((byte) 3, bArr, bwVar, bwVarArr);
    }

    public Double m3657c(byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        return (Double) m3649a((byte) 4, bArr, bwVar, bwVarArr);
    }

    public Short m3658d(byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        return (Short) m3649a((byte) 6, bArr, bwVar, bwVarArr);
    }

    public Integer m3659e(byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        return (Integer) m3649a((byte) 8, bArr, bwVar, bwVarArr);
    }

    public Long m3660f(byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        return (Long) m3649a((byte) 10, bArr, bwVar, bwVarArr);
    }

    public String m3661g(byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        return (String) m3649a((byte) 11, bArr, bwVar, bwVarArr);
    }

    public ByteBuffer m3662h(byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        return (ByteBuffer) m3649a((byte) 100, bArr, bwVar, bwVarArr);
    }

    public Short m3663i(byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        try {
            if (m3650j(bArr, bwVar, bwVarArr) != null) {
                this.f3719a.mo2795j();
                Short valueOf = Short.valueOf(this.f3719a.mo2797l().f3753c);
                this.f3720b.m5613e();
                this.f3719a.mo2812B();
                return valueOf;
            }
            this.f3720b.m5613e();
            this.f3719a.mo2812B();
            return null;
        } catch (Throwable e) {
            throw new bv(e);
        } catch (Throwable th) {
            this.f3720b.m5613e();
            this.f3719a.mo2812B();
        }
    }

    private Object m3649a(byte b, byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        try {
            cj j = m3650j(bArr, bwVar, bwVarArr);
            if (j != null) {
                Object valueOf;
                switch (b) {
                    case (byte) 2:
                        if (j.f3752b == (byte) 2) {
                            valueOf = Boolean.valueOf(this.f3719a.mo2805t());
                            this.f3720b.m5613e();
                            this.f3719a.mo2812B();
                            return valueOf;
                        }
                        break;
                    case (byte) 3:
                        if (j.f3752b == (byte) 3) {
                            valueOf = Byte.valueOf(this.f3719a.mo2806u());
                            this.f3720b.m5613e();
                            this.f3719a.mo2812B();
                            return valueOf;
                        }
                        break;
                    case (byte) 4:
                        if (j.f3752b == (byte) 4) {
                            valueOf = Double.valueOf(this.f3719a.mo2810y());
                            this.f3720b.m5613e();
                            this.f3719a.mo2812B();
                            return valueOf;
                        }
                        break;
                    case (byte) 6:
                        if (j.f3752b == (byte) 6) {
                            valueOf = Short.valueOf(this.f3719a.mo2807v());
                            this.f3720b.m5613e();
                            this.f3719a.mo2812B();
                            return valueOf;
                        }
                        break;
                    case (byte) 8:
                        if (j.f3752b == (byte) 8) {
                            valueOf = Integer.valueOf(this.f3719a.mo2808w());
                            this.f3720b.m5613e();
                            this.f3719a.mo2812B();
                            return valueOf;
                        }
                        break;
                    case (byte) 10:
                        if (j.f3752b == (byte) 10) {
                            valueOf = Long.valueOf(this.f3719a.mo2809x());
                            this.f3720b.m5613e();
                            this.f3719a.mo2812B();
                            return valueOf;
                        }
                        break;
                    case (byte) 11:
                        if (j.f3752b == (byte) 11) {
                            valueOf = this.f3719a.mo2811z();
                            this.f3720b.m5613e();
                            this.f3719a.mo2812B();
                            return valueOf;
                        }
                        break;
                    case (byte) 100:
                        if (j.f3752b == (byte) 11) {
                            valueOf = this.f3719a.mo2771A();
                            this.f3720b.m5613e();
                            this.f3719a.mo2812B();
                            return valueOf;
                        }
                        break;
                }
            }
            this.f3720b.m5613e();
            this.f3719a.mo2812B();
            return null;
        } catch (Throwable e) {
            throw new bv(e);
        } catch (Throwable th) {
            this.f3720b.m5613e();
            this.f3719a.mo2812B();
        }
    }

    private cj m3650j(byte[] bArr, bw bwVar, bw... bwVarArr) throws bv {
        int i = 0;
        this.f3720b.m5607a(bArr);
        bw[] bwVarArr2 = new bw[(bwVarArr.length + 1)];
        bwVarArr2[0] = bwVar;
        for (int i2 = 0; i2 < bwVarArr.length; i2++) {
            bwVarArr2[i2 + 1] = bwVarArr[i2];
        }
        this.f3719a.mo2795j();
        cj cjVar = null;
        while (i < bwVarArr2.length) {
            cjVar = this.f3719a.mo2797l();
            if (cjVar.f3752b == (byte) 0 || cjVar.f3753c > bwVarArr2[i].mo2763a()) {
                return null;
            }
            if (cjVar.f3753c != bwVarArr2[i].mo2763a()) {
                cr.m3735a(this.f3719a, cjVar.f3752b);
                this.f3719a.mo2798m();
            } else {
                i++;
                if (i < bwVarArr2.length) {
                    this.f3719a.mo2795j();
                }
            }
        }
        return cjVar;
    }

    public void m3652a(bp bpVar, String str) throws bv {
        m3654a(bpVar, str.getBytes());
    }
}
