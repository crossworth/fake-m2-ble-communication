package p031u.aly;

import com.umeng.socialize.common.SocializeConstants;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: IdTracking */
public class bc implements Serializable, Cloneable, bp<bc, C1932e> {
    public static final Map<C1932e, cb> f5019d;
    private static final ct f5020e = new ct("IdTracking");
    private static final cj f5021f = new cj("snapshots", cv.f3781k, (short) 1);
    private static final cj f5022g = new cj("journals", cv.f3783m, (short) 2);
    private static final cj f5023h = new cj("checksum", (byte) 11, (short) 3);
    private static final Map<Class<? extends cw>, cx> f5024i = new HashMap();
    public Map<String, bb> f5025a;
    public List<ba> f5026b;
    public String f5027c;
    private C1932e[] f5028j;

    /* compiled from: IdTracking */
    private static class C1930b implements cx {
        private C1930b() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5216a();
        }

        public C2024a m5216a() {
            return new C2024a();
        }
    }

    /* compiled from: IdTracking */
    private static class C1931d implements cx {
        private C1931d() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5218a();
        }

        public C2025c m5218a() {
            return new C2025c();
        }
    }

    /* compiled from: IdTracking */
    public enum C1932e implements bw {
        SNAPSHOTS((short) 1, "snapshots"),
        JOURNALS((short) 2, "journals"),
        CHECKSUM((short) 3, "checksum");
        
        private static final Map<String, C1932e> f5015d = null;
        private final short f5017e;
        private final String f5018f;

        static {
            f5015d = new HashMap();
            Iterator it = EnumSet.allOf(C1932e.class).iterator();
            while (it.hasNext()) {
                C1932e c1932e = (C1932e) it.next();
                f5015d.put(c1932e.mo2764b(), c1932e);
            }
        }

        public static C1932e m5220a(int i) {
            switch (i) {
                case 1:
                    return SNAPSHOTS;
                case 2:
                    return JOURNALS;
                case 3:
                    return CHECKSUM;
                default:
                    return null;
            }
        }

        public static C1932e m5222b(int i) {
            C1932e a = C1932e.m5220a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static C1932e m5221a(String str) {
            return (C1932e) f5015d.get(str);
        }

        private C1932e(short s, String str) {
            this.f5017e = s;
            this.f5018f = str;
        }

        public short mo2763a() {
            return this.f5017e;
        }

        public String mo2764b() {
            return this.f5018f;
        }
    }

    /* compiled from: IdTracking */
    private static class C2024a extends cy<bc> {
        private C2024a() {
        }

        public /* synthetic */ void mo3681a(co coVar, bp bpVar) throws bv {
            m6139b(coVar, (bc) bpVar);
        }

        public /* synthetic */ void mo3682b(co coVar, bp bpVar) throws bv {
            m6137a(coVar, (bc) bpVar);
        }

        public void m6137a(co coVar, bc bcVar) throws bv {
            coVar.mo2795j();
            while (true) {
                cj l = coVar.mo2797l();
                if (l.f3752b == (byte) 0) {
                    coVar.mo2796k();
                    bcVar.m5257o();
                    return;
                }
                int i;
                switch (l.f3753c) {
                    case (short) 1:
                        if (l.f3752b != cv.f3781k) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        cl n = coVar.mo2799n();
                        bcVar.f5025a = new HashMap(n.f3758c * 2);
                        for (i = 0; i < n.f3758c; i++) {
                            String z = coVar.mo2811z();
                            bb bbVar = new bb();
                            bbVar.mo2765a(coVar);
                            bcVar.f5025a.put(z, bbVar);
                        }
                        coVar.mo2800o();
                        bcVar.m5239a(true);
                        break;
                    case (short) 2:
                        if (l.f3752b != cv.f3783m) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        ck p = coVar.mo2801p();
                        bcVar.f5026b = new ArrayList(p.f3755b);
                        for (i = 0; i < p.f3755b; i++) {
                            ba baVar = new ba();
                            baVar.mo2765a(coVar);
                            bcVar.f5026b.add(baVar);
                        }
                        coVar.mo2802q();
                        bcVar.m5243b(true);
                        break;
                    case (short) 3:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bcVar.f5027c = coVar.mo2811z();
                        bcVar.m5245c(true);
                        break;
                    default:
                        cr.m3735a(coVar, l.f3752b);
                        break;
                }
                coVar.mo2798m();
            }
        }

        public void m6139b(co coVar, bc bcVar) throws bv {
            bcVar.m5257o();
            coVar.mo2784a(bc.f5020e);
            if (bcVar.f5025a != null) {
                coVar.mo2779a(bc.f5021f);
                coVar.mo2781a(new cl((byte) 11, (byte) 12, bcVar.f5025a.size()));
                for (Entry entry : bcVar.f5025a.entrySet()) {
                    coVar.mo2777a((String) entry.getKey());
                    ((bb) entry.getValue()).mo2768b(coVar);
                }
                coVar.mo2790e();
                coVar.mo2788c();
            }
            if (bcVar.f5026b != null && bcVar.m5253k()) {
                coVar.mo2779a(bc.f5022g);
                coVar.mo2780a(new ck((byte) 12, bcVar.f5026b.size()));
                for (ba b : bcVar.f5026b) {
                    b.mo2768b(coVar);
                }
                coVar.mo2791f();
                coVar.mo2788c();
            }
            if (bcVar.f5027c != null && bcVar.m5256n()) {
                coVar.mo2779a(bc.f5023h);
                coVar.mo2777a(bcVar.f5027c);
                coVar.mo2788c();
            }
            coVar.mo2789d();
            coVar.mo2787b();
        }
    }

    /* compiled from: IdTracking */
    private static class C2025c extends cz<bc> {
        private C2025c() {
        }

        public void m6141a(co coVar, bc bcVar) throws bv {
            coVar = (cu) coVar;
            coVar.mo2775a(bcVar.f5025a.size());
            for (Entry entry : bcVar.f5025a.entrySet()) {
                coVar.mo2777a((String) entry.getKey());
                ((bb) entry.getValue()).mo2768b(coVar);
            }
            BitSet bitSet = new BitSet();
            if (bcVar.m5253k()) {
                bitSet.set(0);
            }
            if (bcVar.m5256n()) {
                bitSet.set(1);
            }
            coVar.m6188a(bitSet, 2);
            if (bcVar.m5253k()) {
                coVar.mo2775a(bcVar.f5026b.size());
                for (ba b : bcVar.f5026b) {
                    b.mo2768b(coVar);
                }
            }
            if (bcVar.m5256n()) {
                coVar.mo2777a(bcVar.f5027c);
            }
        }

        public void m6143b(co coVar, bc bcVar) throws bv {
            int i = 0;
            coVar = (cu) coVar;
            cl clVar = new cl((byte) 11, (byte) 12, coVar.mo2808w());
            bcVar.f5025a = new HashMap(clVar.f3758c * 2);
            for (int i2 = 0; i2 < clVar.f3758c; i2++) {
                String z = coVar.mo2811z();
                bb bbVar = new bb();
                bbVar.mo2765a(coVar);
                bcVar.f5025a.put(z, bbVar);
            }
            bcVar.m5239a(true);
            BitSet b = coVar.mo3684b(2);
            if (b.get(0)) {
                ck ckVar = new ck((byte) 12, coVar.mo2808w());
                bcVar.f5026b = new ArrayList(ckVar.f3755b);
                while (i < ckVar.f3755b) {
                    ba baVar = new ba();
                    baVar.mo2765a(coVar);
                    bcVar.f5026b.add(baVar);
                    i++;
                }
                bcVar.m5243b(true);
            }
            if (b.get(1)) {
                bcVar.f5027c = coVar.mo2811z();
                bcVar.m5245c(true);
            }
        }
    }

    public /* synthetic */ bw mo2766b(int i) {
        return m5231a(i);
    }

    public /* synthetic */ bp mo2769p() {
        return m5232a();
    }

    static {
        f5024i.put(cy.class, new C1930b());
        f5024i.put(cz.class, new C1931d());
        Map enumMap = new EnumMap(C1932e.class);
        enumMap.put(C1932e.SNAPSHOTS, new cb("snapshots", (byte) 1, new ce(cv.f3781k, new cc((byte) 11), new cg((byte) 12, bb.class))));
        enumMap.put(C1932e.JOURNALS, new cb("journals", (byte) 2, new cd(cv.f3783m, new cg((byte) 12, ba.class))));
        enumMap.put(C1932e.CHECKSUM, new cb("checksum", (byte) 2, new cc((byte) 11)));
        f5019d = Collections.unmodifiableMap(enumMap);
        cb.m3680a(bc.class, f5019d);
    }

    public bc() {
        this.f5028j = new C1932e[]{C1932e.JOURNALS, C1932e.CHECKSUM};
    }

    public bc(Map<String, bb> map) {
        this();
        this.f5025a = map;
    }

    public bc(bc bcVar) {
        this.f5028j = new C1932e[]{C1932e.JOURNALS, C1932e.CHECKSUM};
        if (bcVar.m5248f()) {
            Map hashMap = new HashMap();
            for (Entry entry : bcVar.f5025a.entrySet()) {
                hashMap.put((String) entry.getKey(), new bb((bb) entry.getValue()));
            }
            this.f5025a = hashMap;
        }
        if (bcVar.m5253k()) {
            List arrayList = new ArrayList();
            for (ba baVar : bcVar.f5026b) {
                arrayList.add(new ba(baVar));
            }
            this.f5026b = arrayList;
        }
        if (bcVar.m5256n()) {
            this.f5027c = bcVar.f5027c;
        }
    }

    public bc m5232a() {
        return new bc(this);
    }

    public void mo2767b() {
        this.f5025a = null;
        this.f5026b = null;
        this.f5027c = null;
    }

    public int m5244c() {
        return this.f5025a == null ? 0 : this.f5025a.size();
    }

    public void m5236a(String str, bb bbVar) {
        if (this.f5025a == null) {
            this.f5025a = new HashMap();
        }
        this.f5025a.put(str, bbVar);
    }

    public Map<String, bb> m5246d() {
        return this.f5025a;
    }

    public bc m5235a(Map<String, bb> map) {
        this.f5025a = map;
        return this;
    }

    public void m5247e() {
        this.f5025a = null;
    }

    public boolean m5248f() {
        return this.f5025a != null;
    }

    public void m5239a(boolean z) {
        if (!z) {
            this.f5025a = null;
        }
    }

    public int m5249g() {
        return this.f5026b == null ? 0 : this.f5026b.size();
    }

    public Iterator<ba> m5250h() {
        return this.f5026b == null ? null : this.f5026b.iterator();
    }

    public void m5237a(ba baVar) {
        if (this.f5026b == null) {
            this.f5026b = new ArrayList();
        }
        this.f5026b.add(baVar);
    }

    public List<ba> m5251i() {
        return this.f5026b;
    }

    public bc m5234a(List<ba> list) {
        this.f5026b = list;
        return this;
    }

    public void m5252j() {
        this.f5026b = null;
    }

    public boolean m5253k() {
        return this.f5026b != null;
    }

    public void m5243b(boolean z) {
        if (!z) {
            this.f5026b = null;
        }
    }

    public String m5254l() {
        return this.f5027c;
    }

    public bc m5233a(String str) {
        this.f5027c = str;
        return this;
    }

    public void m5255m() {
        this.f5027c = null;
    }

    public boolean m5256n() {
        return this.f5027c != null;
    }

    public void m5245c(boolean z) {
        if (!z) {
            this.f5027c = null;
        }
    }

    public C1932e m5231a(int i) {
        return C1932e.m5220a(i);
    }

    public void mo2765a(co coVar) throws bv {
        ((cx) f5024i.get(coVar.mo3683D())).mo2762b().mo3682b(coVar, this);
    }

    public void mo2768b(co coVar) throws bv {
        ((cx) f5024i.get(coVar.mo3683D())).mo2762b().mo3681a(coVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("IdTracking(");
        stringBuilder.append("snapshots:");
        if (this.f5025a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f5025a);
        }
        if (m5253k()) {
            stringBuilder.append(", ");
            stringBuilder.append("journals:");
            if (this.f5026b == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f5026b);
            }
        }
        if (m5256n()) {
            stringBuilder.append(", ");
            stringBuilder.append("checksum:");
            if (this.f5027c == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f5027c);
            }
        }
        stringBuilder.append(SocializeConstants.OP_CLOSE_PAREN);
        return stringBuilder.toString();
    }

    public void m5257o() throws bv {
        if (this.f5025a == null) {
            throw new cp("Required field 'snapshots' was not present! Struct: " + toString());
        }
    }

    private void m5226a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            mo2768b(new ci(new da((OutputStream) objectOutputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }

    private void m5225a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            mo2765a(new ci(new da((InputStream) objectInputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }
}
