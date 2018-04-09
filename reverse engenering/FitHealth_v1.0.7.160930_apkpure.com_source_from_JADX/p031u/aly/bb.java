package p031u.aly;

import com.umeng.socialize.common.SocializeConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.apache.http.protocol.HTTP;

/* compiled from: IdSnapshot */
public class bb implements Serializable, Cloneable, bp<bb, C1929e> {
    public static final Map<C1929e, cb> f5000d;
    private static final ct f5001e = new ct("IdSnapshot");
    private static final cj f5002f = new cj(HTTP.IDENTITY_CODING, (byte) 11, (short) 1);
    private static final cj f5003g = new cj("ts", (byte) 10, (short) 2);
    private static final cj f5004h = new cj("version", (byte) 8, (short) 3);
    private static final Map<Class<? extends cw>, cx> f5005i = new HashMap();
    private static final int f5006j = 0;
    private static final int f5007k = 1;
    public String f5008a;
    public long f5009b;
    public int f5010c;
    private byte f5011l;

    /* compiled from: IdSnapshot */
    private static class C1927b implements cx {
        private C1927b() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5178a();
        }

        public C2022a m5178a() {
            return new C2022a();
        }
    }

    /* compiled from: IdSnapshot */
    private static class C1928d implements cx {
        private C1928d() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5180a();
        }

        public C2023c m5180a() {
            return new C2023c();
        }
    }

    /* compiled from: IdSnapshot */
    public enum C1929e implements bw {
        IDENTITY((short) 1, HTTP.IDENTITY_CODING),
        TS((short) 2, "ts"),
        VERSION((short) 3, "version");
        
        private static final Map<String, C1929e> f4996d = null;
        private final short f4998e;
        private final String f4999f;

        static {
            f4996d = new HashMap();
            Iterator it = EnumSet.allOf(C1929e.class).iterator();
            while (it.hasNext()) {
                C1929e c1929e = (C1929e) it.next();
                f4996d.put(c1929e.mo2764b(), c1929e);
            }
        }

        public static C1929e m5182a(int i) {
            switch (i) {
                case 1:
                    return IDENTITY;
                case 2:
                    return TS;
                case 3:
                    return VERSION;
                default:
                    return null;
            }
        }

        public static C1929e m5184b(int i) {
            C1929e a = C1929e.m5182a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static C1929e m5183a(String str) {
            return (C1929e) f4996d.get(str);
        }

        private C1929e(short s, String str) {
            this.f4998e = s;
            this.f4999f = str;
        }

        public short mo2763a() {
            return this.f4998e;
        }

        public String mo2764b() {
            return this.f4999f;
        }
    }

    /* compiled from: IdSnapshot */
    private static class C2022a extends cy<bb> {
        private C2022a() {
        }

        public /* synthetic */ void mo3681a(co coVar, bp bpVar) throws bv {
            m6131b(coVar, (bb) bpVar);
        }

        public /* synthetic */ void mo3682b(co coVar, bp bpVar) throws bv {
            m6129a(coVar, (bb) bpVar);
        }

        public void m6129a(co coVar, bb bbVar) throws bv {
            coVar.mo2795j();
            while (true) {
                cj l = coVar.mo2797l();
                if (l.f3752b == (byte) 0) {
                    coVar.mo2796k();
                    if (!bbVar.m5210h()) {
                        throw new cp("Required field 'ts' was not found in serialized data! Struct: " + toString());
                    } else if (bbVar.m5213k()) {
                        bbVar.m5214l();
                        return;
                    } else {
                        throw new cp("Required field 'version' was not found in serialized data! Struct: " + toString());
                    }
                }
                switch (l.f3753c) {
                    case (short) 1:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bbVar.f5008a = coVar.mo2811z();
                        bbVar.m5198a(true);
                        break;
                    case (short) 2:
                        if (l.f3752b != (byte) 10) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bbVar.f5009b = coVar.mo2809x();
                        bbVar.m5202b(true);
                        break;
                    case (short) 3:
                        if (l.f3752b != (byte) 8) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bbVar.f5010c = coVar.mo2808w();
                        bbVar.m5205c(true);
                        break;
                    default:
                        cr.m3735a(coVar, l.f3752b);
                        break;
                }
                coVar.mo2798m();
            }
        }

        public void m6131b(co coVar, bb bbVar) throws bv {
            bbVar.m5214l();
            coVar.mo2784a(bb.f5001e);
            if (bbVar.f5008a != null) {
                coVar.mo2779a(bb.f5002f);
                coVar.mo2777a(bbVar.f5008a);
                coVar.mo2788c();
            }
            coVar.mo2779a(bb.f5003g);
            coVar.mo2776a(bbVar.f5009b);
            coVar.mo2788c();
            coVar.mo2779a(bb.f5004h);
            coVar.mo2775a(bbVar.f5010c);
            coVar.mo2788c();
            coVar.mo2789d();
            coVar.mo2787b();
        }
    }

    /* compiled from: IdSnapshot */
    private static class C2023c extends cz<bb> {
        private C2023c() {
        }

        public void m6133a(co coVar, bb bbVar) throws bv {
            cu cuVar = (cu) coVar;
            cuVar.mo2777a(bbVar.f5008a);
            cuVar.mo2776a(bbVar.f5009b);
            cuVar.mo2775a(bbVar.f5010c);
        }

        public void m6135b(co coVar, bb bbVar) throws bv {
            cu cuVar = (cu) coVar;
            bbVar.f5008a = cuVar.mo2811z();
            bbVar.m5198a(true);
            bbVar.f5009b = cuVar.mo2809x();
            bbVar.m5202b(true);
            bbVar.f5010c = cuVar.mo2808w();
            bbVar.m5205c(true);
        }
    }

    public /* synthetic */ bw mo2766b(int i) {
        return m5204c(i);
    }

    public /* synthetic */ bp mo2769p() {
        return m5193a();
    }

    static {
        f5005i.put(cy.class, new C1927b());
        f5005i.put(cz.class, new C1928d());
        Map enumMap = new EnumMap(C1929e.class);
        enumMap.put(C1929e.IDENTITY, new cb(HTTP.IDENTITY_CODING, (byte) 1, new cc((byte) 11)));
        enumMap.put(C1929e.TS, new cb("ts", (byte) 1, new cc((byte) 10)));
        enumMap.put(C1929e.VERSION, new cb("version", (byte) 1, new cc((byte) 8)));
        f5000d = Collections.unmodifiableMap(enumMap);
        cb.m3680a(bb.class, f5000d);
    }

    public bb() {
        this.f5011l = (byte) 0;
    }

    public bb(String str, long j, int i) {
        this();
        this.f5008a = str;
        this.f5009b = j;
        m5202b(true);
        this.f5010c = i;
        m5205c(true);
    }

    public bb(bb bbVar) {
        this.f5011l = (byte) 0;
        this.f5011l = bbVar.f5011l;
        if (bbVar.m5207e()) {
            this.f5008a = bbVar.f5008a;
        }
        this.f5009b = bbVar.f5009b;
        this.f5010c = bbVar.f5010c;
    }

    public bb m5193a() {
        return new bb(this);
    }

    public void mo2767b() {
        this.f5008a = null;
        m5202b(false);
        this.f5009b = 0;
        m5205c(false);
        this.f5010c = 0;
    }

    public String m5203c() {
        return this.f5008a;
    }

    public bb m5196a(String str) {
        this.f5008a = str;
        return this;
    }

    public void m5206d() {
        this.f5008a = null;
    }

    public boolean m5207e() {
        return this.f5008a != null;
    }

    public void m5198a(boolean z) {
        if (!z) {
            this.f5008a = null;
        }
    }

    public long m5208f() {
        return this.f5009b;
    }

    public bb m5195a(long j) {
        this.f5009b = j;
        m5202b(true);
        return this;
    }

    public void m5209g() {
        this.f5011l = bm.m3612b(this.f5011l, 0);
    }

    public boolean m5210h() {
        return bm.m3608a(this.f5011l, 0);
    }

    public void m5202b(boolean z) {
        this.f5011l = bm.m3600a(this.f5011l, 0, z);
    }

    public int m5211i() {
        return this.f5010c;
    }

    public bb m5194a(int i) {
        this.f5010c = i;
        m5205c(true);
        return this;
    }

    public void m5212j() {
        this.f5011l = bm.m3612b(this.f5011l, 1);
    }

    public boolean m5213k() {
        return bm.m3608a(this.f5011l, 1);
    }

    public void m5205c(boolean z) {
        this.f5011l = bm.m3600a(this.f5011l, 1, z);
    }

    public C1929e m5204c(int i) {
        return C1929e.m5182a(i);
    }

    public void mo2765a(co coVar) throws bv {
        ((cx) f5005i.get(coVar.mo3683D())).mo2762b().mo3682b(coVar, this);
    }

    public void mo2768b(co coVar) throws bv {
        ((cx) f5005i.get(coVar.mo3683D())).mo2762b().mo3681a(coVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("IdSnapshot(");
        stringBuilder.append("identity:");
        if (this.f5008a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f5008a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("ts:");
        stringBuilder.append(this.f5009b);
        stringBuilder.append(", ");
        stringBuilder.append("version:");
        stringBuilder.append(this.f5010c);
        stringBuilder.append(SocializeConstants.OP_CLOSE_PAREN);
        return stringBuilder.toString();
    }

    public void m5214l() throws bv {
        if (this.f5008a == null) {
            throw new cp("Required field 'identity' was not present! Struct: " + toString());
        }
    }

    private void m5188a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            mo2768b(new ci(new da((OutputStream) objectOutputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }

    private void m5187a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f5011l = (byte) 0;
            mo2765a(new ci(new da((InputStream) objectInputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }
}
