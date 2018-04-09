package p031u.aly;

import com.umeng.socialize.common.SocializeConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: UMEnvelope */
public class bg implements Serializable, Cloneable, bp<bg, C1944e> {
    private static final int f5099A = 3;
    public static final Map<C1944e, cb> f5100k;
    private static final ct f5101l = new ct("UMEnvelope");
    private static final cj f5102m = new cj("version", (byte) 11, (short) 1);
    private static final cj f5103n = new cj("address", (byte) 11, (short) 2);
    private static final cj f5104o = new cj("signature", (byte) 11, (short) 3);
    private static final cj f5105p = new cj("serial_num", (byte) 8, (short) 4);
    private static final cj f5106q = new cj("ts_secs", (byte) 8, (short) 5);
    private static final cj f5107r = new cj("length", (byte) 8, (short) 6);
    private static final cj f5108s = new cj("entity", (byte) 11, (short) 7);
    private static final cj f5109t = new cj("guid", (byte) 11, (short) 8);
    private static final cj f5110u = new cj("checksum", (byte) 11, (short) 9);
    private static final cj f5111v = new cj("codex", (byte) 8, (short) 10);
    private static final Map<Class<? extends cw>, cx> f5112w = new HashMap();
    private static final int f5113x = 0;
    private static final int f5114y = 1;
    private static final int f5115z = 2;
    private byte f5116B;
    private C1944e[] f5117C;
    public String f5118a;
    public String f5119b;
    public String f5120c;
    public int f5121d;
    public int f5122e;
    public int f5123f;
    public ByteBuffer f5124g;
    public String f5125h;
    public String f5126i;
    public int f5127j;

    /* compiled from: UMEnvelope */
    private static class C1942b implements cx {
        private C1942b() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5375a();
        }

        public C2032a m5375a() {
            return new C2032a();
        }
    }

    /* compiled from: UMEnvelope */
    private static class C1943d implements cx {
        private C1943d() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5377a();
        }

        public C2033c m5377a() {
            return new C2033c();
        }
    }

    /* compiled from: UMEnvelope */
    public enum C1944e implements bw {
        VERSION((short) 1, "version"),
        ADDRESS((short) 2, "address"),
        SIGNATURE((short) 3, "signature"),
        SERIAL_NUM((short) 4, "serial_num"),
        TS_SECS((short) 5, "ts_secs"),
        LENGTH((short) 6, "length"),
        ENTITY((short) 7, "entity"),
        GUID((short) 8, "guid"),
        CHECKSUM((short) 9, "checksum"),
        CODEX((short) 10, "codex");
        
        private static final Map<String, C1944e> f5095k = null;
        private final short f5097l;
        private final String f5098m;

        static {
            f5095k = new HashMap();
            Iterator it = EnumSet.allOf(C1944e.class).iterator();
            while (it.hasNext()) {
                C1944e c1944e = (C1944e) it.next();
                f5095k.put(c1944e.mo2764b(), c1944e);
            }
        }

        public static C1944e m5379a(int i) {
            switch (i) {
                case 1:
                    return VERSION;
                case 2:
                    return ADDRESS;
                case 3:
                    return SIGNATURE;
                case 4:
                    return SERIAL_NUM;
                case 5:
                    return TS_SECS;
                case 6:
                    return LENGTH;
                case 7:
                    return ENTITY;
                case 8:
                    return GUID;
                case 9:
                    return CHECKSUM;
                case 10:
                    return CODEX;
                default:
                    return null;
            }
        }

        public static C1944e m5381b(int i) {
            C1944e a = C1944e.m5379a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static C1944e m5380a(String str) {
            return (C1944e) f5095k.get(str);
        }

        private C1944e(short s, String str) {
            this.f5097l = s;
            this.f5098m = str;
        }

        public short mo2763a() {
            return this.f5097l;
        }

        public String mo2764b() {
            return this.f5098m;
        }
    }

    /* compiled from: UMEnvelope */
    private static class C2032a extends cy<bg> {
        private C2032a() {
        }

        public /* synthetic */ void mo3681a(co coVar, bp bpVar) throws bv {
            m6171b(coVar, (bg) bpVar);
        }

        public /* synthetic */ void mo3682b(co coVar, bp bpVar) throws bv {
            m6169a(coVar, (bg) bpVar);
        }

        public void m6169a(co coVar, bg bgVar) throws bv {
            coVar.mo2795j();
            while (true) {
                cj l = coVar.mo2797l();
                if (l.f3752b == (byte) 0) {
                    coVar.mo2796k();
                    if (!bgVar.m5444n()) {
                        throw new cp("Required field 'serial_num' was not found in serialized data! Struct: " + toString());
                    } else if (!bgVar.m5448r()) {
                        throw new cp("Required field 'ts_secs' was not found in serialized data! Struct: " + toString());
                    } else if (bgVar.m5451u()) {
                        bgVar.m5405I();
                        return;
                    } else {
                        throw new cp("Required field 'length' was not found in serialized data! Struct: " + toString());
                    }
                }
                switch (l.f3753c) {
                    case (short) 1:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bgVar.f5118a = coVar.mo2811z();
                        bgVar.m5412a(true);
                        break;
                    case (short) 2:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bgVar.f5119b = coVar.mo2811z();
                        bgVar.m5417b(true);
                        break;
                    case (short) 3:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bgVar.f5120c = coVar.mo2811z();
                        bgVar.m5421c(true);
                        break;
                    case (short) 4:
                        if (l.f3752b != (byte) 8) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bgVar.f5121d = coVar.mo2808w();
                        bgVar.m5425d(true);
                        break;
                    case (short) 5:
                        if (l.f3752b != (byte) 8) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bgVar.f5122e = coVar.mo2808w();
                        bgVar.m5428e(true);
                        break;
                    case (short) 6:
                        if (l.f3752b != (byte) 8) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bgVar.f5123f = coVar.mo2808w();
                        bgVar.m5432f(true);
                        break;
                    case (short) 7:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bgVar.f5124g = coVar.mo2771A();
                        bgVar.m5434g(true);
                        break;
                    case (short) 8:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bgVar.f5125h = coVar.mo2811z();
                        bgVar.m5435h(true);
                        break;
                    case (short) 9:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bgVar.f5126i = coVar.mo2811z();
                        bgVar.m5438i(true);
                        break;
                    case (short) 10:
                        if (l.f3752b != (byte) 8) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bgVar.f5127j = coVar.mo2808w();
                        bgVar.m5440j(true);
                        break;
                    default:
                        cr.m3735a(coVar, l.f3752b);
                        break;
                }
                coVar.mo2798m();
            }
        }

        public void m6171b(co coVar, bg bgVar) throws bv {
            bgVar.m5405I();
            coVar.mo2784a(bg.f5101l);
            if (bgVar.f5118a != null) {
                coVar.mo2779a(bg.f5102m);
                coVar.mo2777a(bgVar.f5118a);
                coVar.mo2788c();
            }
            if (bgVar.f5119b != null) {
                coVar.mo2779a(bg.f5103n);
                coVar.mo2777a(bgVar.f5119b);
                coVar.mo2788c();
            }
            if (bgVar.f5120c != null) {
                coVar.mo2779a(bg.f5104o);
                coVar.mo2777a(bgVar.f5120c);
                coVar.mo2788c();
            }
            coVar.mo2779a(bg.f5105p);
            coVar.mo2775a(bgVar.f5121d);
            coVar.mo2788c();
            coVar.mo2779a(bg.f5106q);
            coVar.mo2775a(bgVar.f5122e);
            coVar.mo2788c();
            coVar.mo2779a(bg.f5107r);
            coVar.mo2775a(bgVar.f5123f);
            coVar.mo2788c();
            if (bgVar.f5124g != null) {
                coVar.mo2779a(bg.f5108s);
                coVar.mo2778a(bgVar.f5124g);
                coVar.mo2788c();
            }
            if (bgVar.f5125h != null) {
                coVar.mo2779a(bg.f5109t);
                coVar.mo2777a(bgVar.f5125h);
                coVar.mo2788c();
            }
            if (bgVar.f5126i != null) {
                coVar.mo2779a(bg.f5110u);
                coVar.mo2777a(bgVar.f5126i);
                coVar.mo2788c();
            }
            if (bgVar.m5404H()) {
                coVar.mo2779a(bg.f5111v);
                coVar.mo2775a(bgVar.f5127j);
                coVar.mo2788c();
            }
            coVar.mo2789d();
            coVar.mo2787b();
        }
    }

    /* compiled from: UMEnvelope */
    private static class C2033c extends cz<bg> {
        private C2033c() {
        }

        public void m6173a(co coVar, bg bgVar) throws bv {
            cu cuVar = (cu) coVar;
            cuVar.mo2777a(bgVar.f5118a);
            cuVar.mo2777a(bgVar.f5119b);
            cuVar.mo2777a(bgVar.f5120c);
            cuVar.mo2775a(bgVar.f5121d);
            cuVar.mo2775a(bgVar.f5122e);
            cuVar.mo2775a(bgVar.f5123f);
            cuVar.mo2778a(bgVar.f5124g);
            cuVar.mo2777a(bgVar.f5125h);
            cuVar.mo2777a(bgVar.f5126i);
            BitSet bitSet = new BitSet();
            if (bgVar.m5404H()) {
                bitSet.set(0);
            }
            cuVar.m6188a(bitSet, 1);
            if (bgVar.m5404H()) {
                cuVar.mo2775a(bgVar.f5127j);
            }
        }

        public void m6175b(co coVar, bg bgVar) throws bv {
            cu cuVar = (cu) coVar;
            bgVar.f5118a = cuVar.mo2811z();
            bgVar.m5412a(true);
            bgVar.f5119b = cuVar.mo2811z();
            bgVar.m5417b(true);
            bgVar.f5120c = cuVar.mo2811z();
            bgVar.m5421c(true);
            bgVar.f5121d = cuVar.mo2808w();
            bgVar.m5425d(true);
            bgVar.f5122e = cuVar.mo2808w();
            bgVar.m5428e(true);
            bgVar.f5123f = cuVar.mo2808w();
            bgVar.m5432f(true);
            bgVar.f5124g = cuVar.mo2771A();
            bgVar.m5434g(true);
            bgVar.f5125h = cuVar.mo2811z();
            bgVar.m5435h(true);
            bgVar.f5126i = cuVar.mo2811z();
            bgVar.m5438i(true);
            if (cuVar.mo3684b(1).get(0)) {
                bgVar.f5127j = cuVar.mo2808w();
                bgVar.m5440j(true);
            }
        }
    }

    public /* synthetic */ bw mo2766b(int i) {
        return m5431f(i);
    }

    public /* synthetic */ bp mo2769p() {
        return m5406a();
    }

    static {
        f5112w.put(cy.class, new C1942b());
        f5112w.put(cz.class, new C1943d());
        Map enumMap = new EnumMap(C1944e.class);
        enumMap.put(C1944e.VERSION, new cb("version", (byte) 1, new cc((byte) 11)));
        enumMap.put(C1944e.ADDRESS, new cb("address", (byte) 1, new cc((byte) 11)));
        enumMap.put(C1944e.SIGNATURE, new cb("signature", (byte) 1, new cc((byte) 11)));
        enumMap.put(C1944e.SERIAL_NUM, new cb("serial_num", (byte) 1, new cc((byte) 8)));
        enumMap.put(C1944e.TS_SECS, new cb("ts_secs", (byte) 1, new cc((byte) 8)));
        enumMap.put(C1944e.LENGTH, new cb("length", (byte) 1, new cc((byte) 8)));
        enumMap.put(C1944e.ENTITY, new cb("entity", (byte) 1, new cc((byte) 11, true)));
        enumMap.put(C1944e.GUID, new cb("guid", (byte) 1, new cc((byte) 11)));
        enumMap.put(C1944e.CHECKSUM, new cb("checksum", (byte) 1, new cc((byte) 11)));
        enumMap.put(C1944e.CODEX, new cb("codex", (byte) 2, new cc((byte) 8)));
        f5100k = Collections.unmodifiableMap(enumMap);
        cb.m3680a(bg.class, f5100k);
    }

    public bg() {
        this.f5116B = (byte) 0;
        this.f5117C = new C1944e[]{C1944e.CODEX};
    }

    public bg(String str, String str2, String str3, int i, int i2, int i3, ByteBuffer byteBuffer, String str4, String str5) {
        this();
        this.f5118a = str;
        this.f5119b = str2;
        this.f5120c = str3;
        this.f5121d = i;
        m5425d(true);
        this.f5122e = i2;
        m5428e(true);
        this.f5123f = i3;
        m5432f(true);
        this.f5124g = byteBuffer;
        this.f5125h = str4;
        this.f5126i = str5;
    }

    public bg(bg bgVar) {
        this.f5116B = (byte) 0;
        this.f5117C = new C1944e[]{C1944e.CODEX};
        this.f5116B = bgVar.f5116B;
        if (bgVar.m5429e()) {
            this.f5118a = bgVar.f5118a;
        }
        if (bgVar.m5436h()) {
            this.f5119b = bgVar.f5119b;
        }
        if (bgVar.m5441k()) {
            this.f5120c = bgVar.f5120c;
        }
        this.f5121d = bgVar.f5121d;
        this.f5122e = bgVar.f5122e;
        this.f5123f = bgVar.f5123f;
        if (bgVar.m5455y()) {
            this.f5124g = bq.m3646d(bgVar.f5124g);
        }
        if (bgVar.m5398B()) {
            this.f5125h = bgVar.f5125h;
        }
        if (bgVar.m5401E()) {
            this.f5126i = bgVar.f5126i;
        }
        this.f5127j = bgVar.f5127j;
    }

    public bg m5406a() {
        return new bg(this);
    }

    public void mo2767b() {
        this.f5118a = null;
        this.f5119b = null;
        this.f5120c = null;
        m5425d(false);
        this.f5121d = 0;
        m5428e(false);
        this.f5122e = 0;
        m5432f(false);
        this.f5123f = 0;
        this.f5124g = null;
        this.f5125h = null;
        this.f5126i = null;
        m5440j(false);
        this.f5127j = 0;
    }

    public String m5418c() {
        return this.f5118a;
    }

    public bg m5408a(String str) {
        this.f5118a = str;
        return this;
    }

    public void m5424d() {
        this.f5118a = null;
    }

    public boolean m5429e() {
        return this.f5118a != null;
    }

    public void m5412a(boolean z) {
        if (!z) {
            this.f5118a = null;
        }
    }

    public String m5430f() {
        return this.f5119b;
    }

    public bg m5413b(String str) {
        this.f5119b = str;
        return this;
    }

    public void m5433g() {
        this.f5119b = null;
    }

    public boolean m5436h() {
        return this.f5119b != null;
    }

    public void m5417b(boolean z) {
        if (!z) {
            this.f5119b = null;
        }
    }

    public String m5437i() {
        return this.f5120c;
    }

    public bg m5420c(String str) {
        this.f5120c = str;
        return this;
    }

    public void m5439j() {
        this.f5120c = null;
    }

    public boolean m5441k() {
        return this.f5120c != null;
    }

    public void m5421c(boolean z) {
        if (!z) {
            this.f5120c = null;
        }
    }

    public int m5442l() {
        return this.f5121d;
    }

    public bg m5407a(int i) {
        this.f5121d = i;
        m5425d(true);
        return this;
    }

    public void m5443m() {
        this.f5116B = bm.m3612b(this.f5116B, 0);
    }

    public boolean m5444n() {
        return bm.m3608a(this.f5116B, 0);
    }

    public void m5425d(boolean z) {
        this.f5116B = bm.m3600a(this.f5116B, 0, z);
    }

    public int m5445o() {
        return this.f5122e;
    }

    public bg m5419c(int i) {
        this.f5122e = i;
        m5428e(true);
        return this;
    }

    public void m5447q() {
        this.f5116B = bm.m3612b(this.f5116B, 1);
    }

    public boolean m5448r() {
        return bm.m3608a(this.f5116B, 1);
    }

    public void m5428e(boolean z) {
        this.f5116B = bm.m3600a(this.f5116B, 1, z);
    }

    public int m5449s() {
        return this.f5123f;
    }

    public bg m5422d(int i) {
        this.f5123f = i;
        m5432f(true);
        return this;
    }

    public void m5450t() {
        this.f5116B = bm.m3612b(this.f5116B, 2);
    }

    public boolean m5451u() {
        return bm.m3608a(this.f5116B, 2);
    }

    public void m5432f(boolean z) {
        this.f5116B = bm.m3600a(this.f5116B, 2, z);
    }

    public byte[] m5452v() {
        m5409a(bq.m3645c(this.f5124g));
        return this.f5124g == null ? null : this.f5124g.array();
    }

    public ByteBuffer m5453w() {
        return this.f5124g;
    }

    public bg m5410a(byte[] bArr) {
        m5409a(bArr == null ? (ByteBuffer) null : ByteBuffer.wrap(bArr));
        return this;
    }

    public bg m5409a(ByteBuffer byteBuffer) {
        this.f5124g = byteBuffer;
        return this;
    }

    public void m5454x() {
        this.f5124g = null;
    }

    public boolean m5455y() {
        return this.f5124g != null;
    }

    public void m5434g(boolean z) {
        if (!z) {
            this.f5124g = null;
        }
    }

    public String m5456z() {
        return this.f5125h;
    }

    public bg m5423d(String str) {
        this.f5125h = str;
        return this;
    }

    public void m5397A() {
        this.f5125h = null;
    }

    public boolean m5398B() {
        return this.f5125h != null;
    }

    public void m5435h(boolean z) {
        if (!z) {
            this.f5125h = null;
        }
    }

    public String m5399C() {
        return this.f5126i;
    }

    public bg m5427e(String str) {
        this.f5126i = str;
        return this;
    }

    public void m5400D() {
        this.f5126i = null;
    }

    public boolean m5401E() {
        return this.f5126i != null;
    }

    public void m5438i(boolean z) {
        if (!z) {
            this.f5126i = null;
        }
    }

    public int m5402F() {
        return this.f5127j;
    }

    public bg m5426e(int i) {
        this.f5127j = i;
        m5440j(true);
        return this;
    }

    public void m5403G() {
        this.f5116B = bm.m3612b(this.f5116B, 3);
    }

    public boolean m5404H() {
        return bm.m3608a(this.f5116B, 3);
    }

    public void m5440j(boolean z) {
        this.f5116B = bm.m3600a(this.f5116B, 3, z);
    }

    public C1944e m5431f(int i) {
        return C1944e.m5379a(i);
    }

    public void mo2765a(co coVar) throws bv {
        ((cx) f5112w.get(coVar.mo3683D())).mo2762b().mo3682b(coVar, this);
    }

    public void mo2768b(co coVar) throws bv {
        ((cx) f5112w.get(coVar.mo3683D())).mo2762b().mo3681a(coVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("UMEnvelope(");
        stringBuilder.append("version:");
        if (this.f5118a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f5118a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("address:");
        if (this.f5119b == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f5119b);
        }
        stringBuilder.append(", ");
        stringBuilder.append("signature:");
        if (this.f5120c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f5120c);
        }
        stringBuilder.append(", ");
        stringBuilder.append("serial_num:");
        stringBuilder.append(this.f5121d);
        stringBuilder.append(", ");
        stringBuilder.append("ts_secs:");
        stringBuilder.append(this.f5122e);
        stringBuilder.append(", ");
        stringBuilder.append("length:");
        stringBuilder.append(this.f5123f);
        stringBuilder.append(", ");
        stringBuilder.append("entity:");
        if (this.f5124g == null) {
            stringBuilder.append("null");
        } else {
            bq.m3641a(this.f5124g, stringBuilder);
        }
        stringBuilder.append(", ");
        stringBuilder.append("guid:");
        if (this.f5125h == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f5125h);
        }
        stringBuilder.append(", ");
        stringBuilder.append("checksum:");
        if (this.f5126i == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f5126i);
        }
        if (m5404H()) {
            stringBuilder.append(", ");
            stringBuilder.append("codex:");
            stringBuilder.append(this.f5127j);
        }
        stringBuilder.append(SocializeConstants.OP_CLOSE_PAREN);
        return stringBuilder.toString();
    }

    public void m5405I() throws bv {
        if (this.f5118a == null) {
            throw new cp("Required field 'version' was not present! Struct: " + toString());
        } else if (this.f5119b == null) {
            throw new cp("Required field 'address' was not present! Struct: " + toString());
        } else if (this.f5120c == null) {
            throw new cp("Required field 'signature' was not present! Struct: " + toString());
        } else if (this.f5124g == null) {
            throw new cp("Required field 'entity' was not present! Struct: " + toString());
        } else if (this.f5125h == null) {
            throw new cp("Required field 'guid' was not present! Struct: " + toString());
        } else if (this.f5126i == null) {
            throw new cp("Required field 'checksum' was not present! Struct: " + toString());
        }
    }

    private void m5396a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            mo2768b(new ci(new da((OutputStream) objectOutputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }

    private void m5395a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f5116B = (byte) 0;
            mo2765a(new ci(new da((InputStream) objectInputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }
}
