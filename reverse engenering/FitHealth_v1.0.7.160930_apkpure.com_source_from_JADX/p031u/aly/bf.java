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

/* compiled from: Response */
public class bf implements Serializable, Cloneable, bp<bf, C1941e> {
    public static final Map<C1941e, cb> f5073d;
    private static final ct f5074e = new ct("Response");
    private static final cj f5075f = new cj("resp_code", (byte) 8, (short) 1);
    private static final cj f5076g = new cj("msg", (byte) 11, (short) 2);
    private static final cj f5077h = new cj(au.f3559N, (byte) 12, (short) 3);
    private static final Map<Class<? extends cw>, cx> f5078i = new HashMap();
    private static final int f5079j = 0;
    public int f5080a;
    public String f5081b;
    public bd f5082c;
    private byte f5083k;
    private C1941e[] f5084l;

    /* compiled from: Response */
    private static class C1939b implements cx {
        private C1939b() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5337a();
        }

        public C2030a m5337a() {
            return new C2030a();
        }
    }

    /* compiled from: Response */
    private static class C1940d implements cx {
        private C1940d() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5339a();
        }

        public C2031c m5339a() {
            return new C2031c();
        }
    }

    /* compiled from: Response */
    public enum C1941e implements bw {
        RESP_CODE((short) 1, "resp_code"),
        MSG((short) 2, "msg"),
        IMPRINT((short) 3, au.f3559N);
        
        private static final Map<String, C1941e> f5069d = null;
        private final short f5071e;
        private final String f5072f;

        static {
            f5069d = new HashMap();
            Iterator it = EnumSet.allOf(C1941e.class).iterator();
            while (it.hasNext()) {
                C1941e c1941e = (C1941e) it.next();
                f5069d.put(c1941e.mo2764b(), c1941e);
            }
        }

        public static C1941e m5341a(int i) {
            switch (i) {
                case 1:
                    return RESP_CODE;
                case 2:
                    return MSG;
                case 3:
                    return IMPRINT;
                default:
                    return null;
            }
        }

        public static C1941e m5343b(int i) {
            C1941e a = C1941e.m5341a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static C1941e m5342a(String str) {
            return (C1941e) f5069d.get(str);
        }

        private C1941e(short s, String str) {
            this.f5071e = s;
            this.f5072f = str;
        }

        public short mo2763a() {
            return this.f5071e;
        }

        public String mo2764b() {
            return this.f5072f;
        }
    }

    /* compiled from: Response */
    private static class C2030a extends cy<bf> {
        private C2030a() {
        }

        public /* synthetic */ void mo3681a(co coVar, bp bpVar) throws bv {
            m6163b(coVar, (bf) bpVar);
        }

        public /* synthetic */ void mo3682b(co coVar, bp bpVar) throws bv {
            m6161a(coVar, (bf) bpVar);
        }

        public void m6161a(co coVar, bf bfVar) throws bv {
            coVar.mo2795j();
            while (true) {
                cj l = coVar.mo2797l();
                if (l.f3752b == (byte) 0) {
                    coVar.mo2796k();
                    if (bfVar.m5366e()) {
                        bfVar.m5373l();
                        return;
                    }
                    throw new cp("Required field 'resp_code' was not found in serialized data! Struct: " + toString());
                }
                switch (l.f3753c) {
                    case (short) 1:
                        if (l.f3752b != (byte) 8) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bfVar.f5080a = coVar.mo2808w();
                        bfVar.m5357a(true);
                        break;
                    case (short) 2:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bfVar.f5081b = coVar.mo2811z();
                        bfVar.m5361b(true);
                        break;
                    case (short) 3:
                        if (l.f3752b != (byte) 12) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bfVar.f5082c = new bd();
                        bfVar.f5082c.mo2765a(coVar);
                        bfVar.m5364c(true);
                        break;
                    default:
                        cr.m3735a(coVar, l.f3752b);
                        break;
                }
                coVar.mo2798m();
            }
        }

        public void m6163b(co coVar, bf bfVar) throws bv {
            bfVar.m5373l();
            coVar.mo2784a(bf.f5074e);
            coVar.mo2779a(bf.f5075f);
            coVar.mo2775a(bfVar.f5080a);
            coVar.mo2788c();
            if (bfVar.f5081b != null && bfVar.m5369h()) {
                coVar.mo2779a(bf.f5076g);
                coVar.mo2777a(bfVar.f5081b);
                coVar.mo2788c();
            }
            if (bfVar.f5082c != null && bfVar.m5372k()) {
                coVar.mo2779a(bf.f5077h);
                bfVar.f5082c.mo2768b(coVar);
                coVar.mo2788c();
            }
            coVar.mo2789d();
            coVar.mo2787b();
        }
    }

    /* compiled from: Response */
    private static class C2031c extends cz<bf> {
        private C2031c() {
        }

        public void m6165a(co coVar, bf bfVar) throws bv {
            coVar = (cu) coVar;
            coVar.mo2775a(bfVar.f5080a);
            BitSet bitSet = new BitSet();
            if (bfVar.m5369h()) {
                bitSet.set(0);
            }
            if (bfVar.m5372k()) {
                bitSet.set(1);
            }
            coVar.m6188a(bitSet, 2);
            if (bfVar.m5369h()) {
                coVar.mo2777a(bfVar.f5081b);
            }
            if (bfVar.m5372k()) {
                bfVar.f5082c.mo2768b(coVar);
            }
        }

        public void m6167b(co coVar, bf bfVar) throws bv {
            coVar = (cu) coVar;
            bfVar.f5080a = coVar.mo2808w();
            bfVar.m5357a(true);
            BitSet b = coVar.mo3684b(2);
            if (b.get(0)) {
                bfVar.f5081b = coVar.mo2811z();
                bfVar.m5361b(true);
            }
            if (b.get(1)) {
                bfVar.f5082c = new bd();
                bfVar.f5082c.mo2765a(coVar);
                bfVar.m5364c(true);
            }
        }
    }

    public /* synthetic */ bw mo2766b(int i) {
        return m5363c(i);
    }

    public /* synthetic */ bp mo2769p() {
        return m5352a();
    }

    static {
        f5078i.put(cy.class, new C1939b());
        f5078i.put(cz.class, new C1940d());
        Map enumMap = new EnumMap(C1941e.class);
        enumMap.put(C1941e.RESP_CODE, new cb("resp_code", (byte) 1, new cc((byte) 8)));
        enumMap.put(C1941e.MSG, new cb("msg", (byte) 2, new cc((byte) 11)));
        enumMap.put(C1941e.IMPRINT, new cb(au.f3559N, (byte) 2, new cg((byte) 12, bd.class)));
        f5073d = Collections.unmodifiableMap(enumMap);
        cb.m3680a(bf.class, f5073d);
    }

    public bf() {
        this.f5083k = (byte) 0;
        this.f5084l = new C1941e[]{C1941e.MSG, C1941e.IMPRINT};
    }

    public bf(int i) {
        this();
        this.f5080a = i;
        m5357a(true);
    }

    public bf(bf bfVar) {
        this.f5083k = (byte) 0;
        this.f5084l = new C1941e[]{C1941e.MSG, C1941e.IMPRINT};
        this.f5083k = bfVar.f5083k;
        this.f5080a = bfVar.f5080a;
        if (bfVar.m5369h()) {
            this.f5081b = bfVar.f5081b;
        }
        if (bfVar.m5372k()) {
            this.f5082c = new bd(bfVar.f5082c);
        }
    }

    public bf m5352a() {
        return new bf(this);
    }

    public void mo2767b() {
        m5357a(false);
        this.f5080a = 0;
        this.f5081b = null;
        this.f5082c = null;
    }

    public int m5362c() {
        return this.f5080a;
    }

    public bf m5353a(int i) {
        this.f5080a = i;
        m5357a(true);
        return this;
    }

    public void m5365d() {
        this.f5083k = bm.m3612b(this.f5083k, 0);
    }

    public boolean m5366e() {
        return bm.m3608a(this.f5083k, 0);
    }

    public void m5357a(boolean z) {
        this.f5083k = bm.m3600a(this.f5083k, 0, z);
    }

    public String m5367f() {
        return this.f5081b;
    }

    public bf m5354a(String str) {
        this.f5081b = str;
        return this;
    }

    public void m5368g() {
        this.f5081b = null;
    }

    public boolean m5369h() {
        return this.f5081b != null;
    }

    public void m5361b(boolean z) {
        if (!z) {
            this.f5081b = null;
        }
    }

    public bd m5370i() {
        return this.f5082c;
    }

    public bf m5355a(bd bdVar) {
        this.f5082c = bdVar;
        return this;
    }

    public void m5371j() {
        this.f5082c = null;
    }

    public boolean m5372k() {
        return this.f5082c != null;
    }

    public void m5364c(boolean z) {
        if (!z) {
            this.f5082c = null;
        }
    }

    public C1941e m5363c(int i) {
        return C1941e.m5341a(i);
    }

    public void mo2765a(co coVar) throws bv {
        ((cx) f5078i.get(coVar.mo3683D())).mo2762b().mo3682b(coVar, this);
    }

    public void mo2768b(co coVar) throws bv {
        ((cx) f5078i.get(coVar.mo3683D())).mo2762b().mo3681a(coVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Response(");
        stringBuilder.append("resp_code:");
        stringBuilder.append(this.f5080a);
        if (m5369h()) {
            stringBuilder.append(", ");
            stringBuilder.append("msg:");
            if (this.f5081b == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f5081b);
            }
        }
        if (m5372k()) {
            stringBuilder.append(", ");
            stringBuilder.append("imprint:");
            if (this.f5082c == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f5082c);
            }
        }
        stringBuilder.append(SocializeConstants.OP_CLOSE_PAREN);
        return stringBuilder.toString();
    }

    public void m5373l() throws bv {
        if (this.f5082c != null) {
            this.f5082c.m5297m();
        }
    }

    private void m5347a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            mo2768b(new ci(new da((OutputStream) objectOutputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }

    private void m5346a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f5083k = (byte) 0;
            mo2765a(new ci(new da((InputStream) objectInputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }
}
