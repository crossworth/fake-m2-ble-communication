package p031u.aly;

import com.umeng.socialize.common.SocializeConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: ImprintValue */
public class be implements Serializable, Cloneable, bp<be, C1938e> {
    public static final Map<C1938e, cb> f5054d;
    private static final ct f5055e = new ct("ImprintValue");
    private static final cj f5056f = new cj("value", (byte) 11, (short) 1);
    private static final cj f5057g = new cj("ts", (byte) 10, (short) 2);
    private static final cj f5058h = new cj("guid", (byte) 11, (short) 3);
    private static final Map<Class<? extends cw>, cx> f5059i = new HashMap();
    private static final int f5060j = 0;
    public String f5061a;
    public long f5062b;
    public String f5063c;
    private byte f5064k;
    private C1938e[] f5065l;

    /* compiled from: ImprintValue */
    private static class C1936b implements cx {
        private C1936b() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5299a();
        }

        public C2028a m5299a() {
            return new C2028a();
        }
    }

    /* compiled from: ImprintValue */
    private static class C1937d implements cx {
        private C1937d() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5301a();
        }

        public C2029c m5301a() {
            return new C2029c();
        }
    }

    /* compiled from: ImprintValue */
    public enum C1938e implements bw {
        VALUE((short) 1, "value"),
        TS((short) 2, "ts"),
        GUID((short) 3, "guid");
        
        private static final Map<String, C1938e> f5050d = null;
        private final short f5052e;
        private final String f5053f;

        static {
            f5050d = new HashMap();
            Iterator it = EnumSet.allOf(C1938e.class).iterator();
            while (it.hasNext()) {
                C1938e c1938e = (C1938e) it.next();
                f5050d.put(c1938e.mo2764b(), c1938e);
            }
        }

        public static C1938e m5303a(int i) {
            switch (i) {
                case 1:
                    return VALUE;
                case 2:
                    return TS;
                case 3:
                    return GUID;
                default:
                    return null;
            }
        }

        public static C1938e m5305b(int i) {
            C1938e a = C1938e.m5303a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static C1938e m5304a(String str) {
            return (C1938e) f5050d.get(str);
        }

        private C1938e(short s, String str) {
            this.f5052e = s;
            this.f5053f = str;
        }

        public short mo2763a() {
            return this.f5052e;
        }

        public String mo2764b() {
            return this.f5053f;
        }
    }

    /* compiled from: ImprintValue */
    private static class C2028a extends cy<be> {
        private C2028a() {
        }

        public /* synthetic */ void mo3681a(co coVar, bp bpVar) throws bv {
            m6155b(coVar, (be) bpVar);
        }

        public /* synthetic */ void mo3682b(co coVar, bp bpVar) throws bv {
            m6153a(coVar, (be) bpVar);
        }

        public void m6153a(co coVar, be beVar) throws bv {
            coVar.mo2795j();
            while (true) {
                cj l = coVar.mo2797l();
                if (l.f3752b == (byte) 0) {
                    coVar.mo2796k();
                    if (beVar.m5331h()) {
                        beVar.m5335l();
                        return;
                    }
                    throw new cp("Required field 'ts' was not found in serialized data! Struct: " + toString());
                }
                switch (l.f3753c) {
                    case (short) 1:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        beVar.f5061a = coVar.mo2811z();
                        beVar.m5319a(true);
                        break;
                    case (short) 2:
                        if (l.f3752b != (byte) 10) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        beVar.f5062b = coVar.mo2809x();
                        beVar.m5324b(true);
                        break;
                    case (short) 3:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        beVar.f5063c = coVar.mo2811z();
                        beVar.m5326c(true);
                        break;
                    default:
                        cr.m3735a(coVar, l.f3752b);
                        break;
                }
                coVar.mo2798m();
            }
        }

        public void m6155b(co coVar, be beVar) throws bv {
            beVar.m5335l();
            coVar.mo2784a(be.f5055e);
            if (beVar.f5061a != null && beVar.m5328e()) {
                coVar.mo2779a(be.f5056f);
                coVar.mo2777a(beVar.f5061a);
                coVar.mo2788c();
            }
            coVar.mo2779a(be.f5057g);
            coVar.mo2776a(beVar.f5062b);
            coVar.mo2788c();
            if (beVar.f5063c != null) {
                coVar.mo2779a(be.f5058h);
                coVar.mo2777a(beVar.f5063c);
                coVar.mo2788c();
            }
            coVar.mo2789d();
            coVar.mo2787b();
        }
    }

    /* compiled from: ImprintValue */
    private static class C2029c extends cz<be> {
        private C2029c() {
        }

        public void m6157a(co coVar, be beVar) throws bv {
            cu cuVar = (cu) coVar;
            cuVar.mo2776a(beVar.f5062b);
            cuVar.mo2777a(beVar.f5063c);
            BitSet bitSet = new BitSet();
            if (beVar.m5328e()) {
                bitSet.set(0);
            }
            cuVar.m6188a(bitSet, 1);
            if (beVar.m5328e()) {
                cuVar.mo2777a(beVar.f5061a);
            }
        }

        public void m6159b(co coVar, be beVar) throws bv {
            cu cuVar = (cu) coVar;
            beVar.f5062b = cuVar.mo2809x();
            beVar.m5324b(true);
            beVar.f5063c = cuVar.mo2811z();
            beVar.m5326c(true);
            if (cuVar.mo3684b(1).get(0)) {
                beVar.f5061a = cuVar.mo2811z();
                beVar.m5319a(true);
            }
        }
    }

    public /* synthetic */ bw mo2766b(int i) {
        return m5314a(i);
    }

    public /* synthetic */ bp mo2769p() {
        return m5315a();
    }

    static {
        f5059i.put(cy.class, new C1936b());
        f5059i.put(cz.class, new C1937d());
        Map enumMap = new EnumMap(C1938e.class);
        enumMap.put(C1938e.VALUE, new cb("value", (byte) 2, new cc((byte) 11)));
        enumMap.put(C1938e.TS, new cb("ts", (byte) 1, new cc((byte) 10)));
        enumMap.put(C1938e.GUID, new cb("guid", (byte) 1, new cc((byte) 11)));
        f5054d = Collections.unmodifiableMap(enumMap);
        cb.m3680a(be.class, f5054d);
    }

    public be() {
        this.f5064k = (byte) 0;
        this.f5065l = new C1938e[]{C1938e.VALUE};
    }

    public be(long j, String str) {
        this();
        this.f5062b = j;
        m5324b(true);
        this.f5063c = str;
    }

    public be(be beVar) {
        this.f5064k = (byte) 0;
        this.f5065l = new C1938e[]{C1938e.VALUE};
        this.f5064k = beVar.f5064k;
        if (beVar.m5328e()) {
            this.f5061a = beVar.f5061a;
        }
        this.f5062b = beVar.f5062b;
        if (beVar.m5334k()) {
            this.f5063c = beVar.f5063c;
        }
    }

    public be m5315a() {
        return new be(this);
    }

    public void mo2767b() {
        this.f5061a = null;
        m5324b(false);
        this.f5062b = 0;
        this.f5063c = null;
    }

    public String m5325c() {
        return this.f5061a;
    }

    public be m5317a(String str) {
        this.f5061a = str;
        return this;
    }

    public void m5327d() {
        this.f5061a = null;
    }

    public boolean m5328e() {
        return this.f5061a != null;
    }

    public void m5319a(boolean z) {
        if (!z) {
            this.f5061a = null;
        }
    }

    public long m5329f() {
        return this.f5062b;
    }

    public be m5316a(long j) {
        this.f5062b = j;
        m5324b(true);
        return this;
    }

    public void m5330g() {
        this.f5064k = bm.m3612b(this.f5064k, 0);
    }

    public boolean m5331h() {
        return bm.m3608a(this.f5064k, 0);
    }

    public void m5324b(boolean z) {
        this.f5064k = bm.m3600a(this.f5064k, 0, z);
    }

    public String m5332i() {
        return this.f5063c;
    }

    public be m5320b(String str) {
        this.f5063c = str;
        return this;
    }

    public void m5333j() {
        this.f5063c = null;
    }

    public boolean m5334k() {
        return this.f5063c != null;
    }

    public void m5326c(boolean z) {
        if (!z) {
            this.f5063c = null;
        }
    }

    public C1938e m5314a(int i) {
        return C1938e.m5303a(i);
    }

    public void mo2765a(co coVar) throws bv {
        ((cx) f5059i.get(coVar.mo3683D())).mo2762b().mo3682b(coVar, this);
    }

    public void mo2768b(co coVar) throws bv {
        ((cx) f5059i.get(coVar.mo3683D())).mo2762b().mo3681a(coVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ImprintValue(");
        Object obj = 1;
        if (m5328e()) {
            stringBuilder.append("value:");
            if (this.f5061a == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f5061a);
            }
            obj = null;
        }
        if (obj == null) {
            stringBuilder.append(", ");
        }
        stringBuilder.append("ts:");
        stringBuilder.append(this.f5062b);
        stringBuilder.append(", ");
        stringBuilder.append("guid:");
        if (this.f5063c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f5063c);
        }
        stringBuilder.append(SocializeConstants.OP_CLOSE_PAREN);
        return stringBuilder.toString();
    }

    public void m5335l() throws bv {
        if (this.f5063c == null) {
            throw new cp("Required field 'guid' was not present! Struct: " + toString());
        }
    }

    private void m5309a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            mo2768b(new ci(new da((OutputStream) objectOutputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }

    private void m5308a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f5064k = (byte) 0;
            mo2765a(new ci(new da((InputStream) objectInputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }
}
