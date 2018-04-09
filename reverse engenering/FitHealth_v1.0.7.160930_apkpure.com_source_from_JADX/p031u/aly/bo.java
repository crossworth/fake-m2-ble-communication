package p031u.aly;

import com.facebook.share.internal.ShareConstants;

/* compiled from: TApplicationException */
public class bo extends bv {
    public static final int f5128a = 0;
    public static final int f5129b = 1;
    public static final int f5130c = 2;
    public static final int f5131d = 3;
    public static final int f5132e = 4;
    public static final int f5133f = 5;
    public static final int f5134g = 6;
    public static final int f5135h = 7;
    private static final ct f5136j = new ct("TApplicationException");
    private static final cj f5137k = new cj(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, (byte) 11, (short) 1);
    private static final cj f5138l = new cj("type", (byte) 8, (short) 2);
    private static final long f5139m = 1;
    protected int f5140i = 0;

    public bo(int i) {
        this.f5140i = i;
    }

    public bo(int i, String str) {
        super(str);
        this.f5140i = i;
    }

    public bo(String str) {
        super(str);
    }

    public int m5458a() {
        return this.f5140i;
    }

    public static bo m5457a(co coVar) throws bv {
        coVar.mo2795j();
        String str = null;
        int i = 0;
        while (true) {
            cj l = coVar.mo2797l();
            if (l.f3752b == (byte) 0) {
                coVar.mo2796k();
                return new bo(i, str);
            }
            switch (l.f3753c) {
                case (short) 1:
                    if (l.f3752b != (byte) 11) {
                        cr.m3735a(coVar, l.f3752b);
                        break;
                    }
                    str = coVar.mo2811z();
                    break;
                case (short) 2:
                    if (l.f3752b != (byte) 8) {
                        cr.m3735a(coVar, l.f3752b);
                        break;
                    }
                    i = coVar.mo2808w();
                    break;
                default:
                    cr.m3735a(coVar, l.f3752b);
                    break;
            }
            coVar.mo2798m();
        }
    }

    public void m5459b(co coVar) throws bv {
        coVar.mo2784a(f5136j);
        if (getMessage() != null) {
            coVar.mo2779a(f5137k);
            coVar.mo2777a(getMessage());
            coVar.mo2788c();
        }
        coVar.mo2779a(f5138l);
        coVar.mo2775a(this.f5140i);
        coVar.mo2788c();
        coVar.mo2789d();
        coVar.mo2787b();
    }
}
