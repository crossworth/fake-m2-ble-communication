package p031u.aly;

import p031u.aly.ci.C1948a;

/* compiled from: TProtocolUtil */
public class cr {
    private static int f3767a = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;

    public static void m3734a(int i) {
        f3767a = i;
    }

    public static void m3735a(co coVar, byte b) throws bv {
        cr.m3736a(coVar, b, f3767a);
    }

    public static void m3736a(co coVar, byte b, int i) throws bv {
        int i2 = 0;
        if (i <= 0) {
            throw new bv("Maximum skip depth exceeded");
        }
        switch (b) {
            case (byte) 2:
                coVar.mo2805t();
                return;
            case (byte) 3:
                coVar.mo2806u();
                return;
            case (byte) 4:
                coVar.mo2810y();
                return;
            case (byte) 6:
                coVar.mo2807v();
                return;
            case (byte) 8:
                coVar.mo2808w();
                return;
            case (byte) 10:
                coVar.mo2809x();
                return;
            case (byte) 11:
                coVar.mo2771A();
                return;
            case (byte) 12:
                coVar.mo2795j();
                while (true) {
                    cj l = coVar.mo2797l();
                    if (l.f3752b == (byte) 0) {
                        coVar.mo2796k();
                        return;
                    } else {
                        cr.m3736a(coVar, l.f3752b, i - 1);
                        coVar.mo2798m();
                    }
                }
            case (byte) 13:
                cl n = coVar.mo2799n();
                while (i2 < n.f3758c) {
                    cr.m3736a(coVar, n.f3756a, i - 1);
                    cr.m3736a(coVar, n.f3757b, i - 1);
                    i2++;
                }
                coVar.mo2800o();
                return;
            case (byte) 14:
                cs r = coVar.mo2803r();
                while (i2 < r.f3769b) {
                    cr.m3736a(coVar, r.f3768a, i - 1);
                    i2++;
                }
                coVar.mo2804s();
                return;
            case (byte) 15:
                ck p = coVar.mo2801p();
                while (i2 < p.f3755b) {
                    cr.m3736a(coVar, p.f3754a, i - 1);
                    i2++;
                }
                coVar.mo2802q();
                return;
            default:
                return;
        }
    }

    public static cq m3733a(byte[] bArr, cq cqVar) {
        if (bArr[0] > cv.f3784n) {
            return new C1948a();
        }
        if (bArr.length <= 1 || (bArr[1] & 128) == 0) {
            return cqVar;
        }
        return new C1948a();
    }
}
