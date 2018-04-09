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
import org.apache.http.cookie.ClientCookie;

/* compiled from: IdJournal */
public class ba implements Serializable, Cloneable, bp<ba, C1926e> {
    public static final Map<C1926e, cb> f4979e;
    private static final ct f4980f = new ct("IdJournal");
    private static final cj f4981g = new cj(ClientCookie.DOMAIN_ATTR, (byte) 11, (short) 1);
    private static final cj f4982h = new cj("old_id", (byte) 11, (short) 2);
    private static final cj f4983i = new cj("new_id", (byte) 11, (short) 3);
    private static final cj f4984j = new cj("ts", (byte) 10, (short) 4);
    private static final Map<Class<? extends cw>, cx> f4985k = new HashMap();
    private static final int f4986l = 0;
    public String f4987a;
    public String f4988b;
    public String f4989c;
    public long f4990d;
    private byte f4991m;
    private C1926e[] f4992n;

    /* compiled from: IdJournal */
    private static class C1924b implements cx {
        private C1924b() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5134a();
        }

        public C2020a m5134a() {
            return new C2020a();
        }
    }

    /* compiled from: IdJournal */
    private static class C1925d implements cx {
        private C1925d() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5136a();
        }

        public C2021c m5136a() {
            return new C2021c();
        }
    }

    /* compiled from: IdJournal */
    public enum C1926e implements bw {
        DOMAIN((short) 1, ClientCookie.DOMAIN_ATTR),
        OLD_ID((short) 2, "old_id"),
        NEW_ID((short) 3, "new_id"),
        TS((short) 4, "ts");
        
        private static final Map<String, C1926e> f4975e = null;
        private final short f4977f;
        private final String f4978g;

        static {
            f4975e = new HashMap();
            Iterator it = EnumSet.allOf(C1926e.class).iterator();
            while (it.hasNext()) {
                C1926e c1926e = (C1926e) it.next();
                f4975e.put(c1926e.mo2764b(), c1926e);
            }
        }

        public static C1926e m5138a(int i) {
            switch (i) {
                case 1:
                    return DOMAIN;
                case 2:
                    return OLD_ID;
                case 3:
                    return NEW_ID;
                case 4:
                    return TS;
                default:
                    return null;
            }
        }

        public static C1926e m5140b(int i) {
            C1926e a = C1926e.m5138a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static C1926e m5139a(String str) {
            return (C1926e) f4975e.get(str);
        }

        private C1926e(short s, String str) {
            this.f4977f = s;
            this.f4978g = str;
        }

        public short mo2763a() {
            return this.f4977f;
        }

        public String mo2764b() {
            return this.f4978g;
        }
    }

    /* compiled from: IdJournal */
    private static class C2020a extends cy<ba> {
        private C2020a() {
        }

        public /* synthetic */ void mo3681a(co coVar, bp bpVar) throws bv {
            m6123b(coVar, (ba) bpVar);
        }

        public /* synthetic */ void mo3682b(co coVar, bp bpVar) throws bv {
            m6121a(coVar, (ba) bpVar);
        }

        public void m6121a(co coVar, ba baVar) throws bv {
            coVar.mo2795j();
            while (true) {
                cj l = coVar.mo2797l();
                if (l.f3752b == (byte) 0) {
                    coVar.mo2796k();
                    if (baVar.m5175n()) {
                        baVar.m5176o();
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
                        baVar.f4987a = coVar.mo2811z();
                        baVar.m5155a(true);
                        break;
                    case (short) 2:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        baVar.f4988b = coVar.mo2811z();
                        baVar.m5160b(true);
                        break;
                    case (short) 3:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        baVar.f4989c = coVar.mo2811z();
                        baVar.m5163c(true);
                        break;
                    case (short) 4:
                        if (l.f3752b != (byte) 10) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        baVar.f4990d = coVar.mo2809x();
                        baVar.m5165d(true);
                        break;
                    default:
                        cr.m3735a(coVar, l.f3752b);
                        break;
                }
                coVar.mo2798m();
            }
        }

        public void m6123b(co coVar, ba baVar) throws bv {
            baVar.m5176o();
            coVar.mo2784a(ba.f4980f);
            if (baVar.f4987a != null) {
                coVar.mo2779a(ba.f4981g);
                coVar.mo2777a(baVar.f4987a);
                coVar.mo2788c();
            }
            if (baVar.f4988b != null && baVar.m5169h()) {
                coVar.mo2779a(ba.f4982h);
                coVar.mo2777a(baVar.f4988b);
                coVar.mo2788c();
            }
            if (baVar.f4989c != null) {
                coVar.mo2779a(ba.f4983i);
                coVar.mo2777a(baVar.f4989c);
                coVar.mo2788c();
            }
            coVar.mo2779a(ba.f4984j);
            coVar.mo2776a(baVar.f4990d);
            coVar.mo2788c();
            coVar.mo2789d();
            coVar.mo2787b();
        }
    }

    /* compiled from: IdJournal */
    private static class C2021c extends cz<ba> {
        private C2021c() {
        }

        public void m6125a(co coVar, ba baVar) throws bv {
            cu cuVar = (cu) coVar;
            cuVar.mo2777a(baVar.f4987a);
            cuVar.mo2777a(baVar.f4989c);
            cuVar.mo2776a(baVar.f4990d);
            BitSet bitSet = new BitSet();
            if (baVar.m5169h()) {
                bitSet.set(0);
            }
            cuVar.m6188a(bitSet, 1);
            if (baVar.m5169h()) {
                cuVar.mo2777a(baVar.f4988b);
            }
        }

        public void m6127b(co coVar, ba baVar) throws bv {
            cu cuVar = (cu) coVar;
            baVar.f4987a = cuVar.mo2811z();
            baVar.m5155a(true);
            baVar.f4989c = cuVar.mo2811z();
            baVar.m5163c(true);
            baVar.f4990d = cuVar.mo2809x();
            baVar.m5165d(true);
            if (cuVar.mo3684b(1).get(0)) {
                baVar.f4988b = cuVar.mo2811z();
                baVar.m5160b(true);
            }
        }
    }

    public /* synthetic */ bw mo2766b(int i) {
        return m5150a(i);
    }

    public /* synthetic */ bp mo2769p() {
        return m5151a();
    }

    static {
        f4985k.put(cy.class, new C1924b());
        f4985k.put(cz.class, new C1925d());
        Map enumMap = new EnumMap(C1926e.class);
        enumMap.put(C1926e.DOMAIN, new cb(ClientCookie.DOMAIN_ATTR, (byte) 1, new cc((byte) 11)));
        enumMap.put(C1926e.OLD_ID, new cb("old_id", (byte) 2, new cc((byte) 11)));
        enumMap.put(C1926e.NEW_ID, new cb("new_id", (byte) 1, new cc((byte) 11)));
        enumMap.put(C1926e.TS, new cb("ts", (byte) 1, new cc((byte) 10)));
        f4979e = Collections.unmodifiableMap(enumMap);
        cb.m3680a(ba.class, f4979e);
    }

    public ba() {
        this.f4991m = (byte) 0;
        this.f4992n = new C1926e[]{C1926e.OLD_ID};
    }

    public ba(String str, String str2, long j) {
        this();
        this.f4987a = str;
        this.f4989c = str2;
        this.f4990d = j;
        m5165d(true);
    }

    public ba(ba baVar) {
        this.f4991m = (byte) 0;
        this.f4992n = new C1926e[]{C1926e.OLD_ID};
        this.f4991m = baVar.f4991m;
        if (baVar.m5166e()) {
            this.f4987a = baVar.f4987a;
        }
        if (baVar.m5169h()) {
            this.f4988b = baVar.f4988b;
        }
        if (baVar.m5172k()) {
            this.f4989c = baVar.f4989c;
        }
        this.f4990d = baVar.f4990d;
    }

    public ba m5151a() {
        return new ba(this);
    }

    public void mo2767b() {
        this.f4987a = null;
        this.f4988b = null;
        this.f4989c = null;
        m5165d(false);
        this.f4990d = 0;
    }

    public String m5161c() {
        return this.f4987a;
    }

    public ba m5153a(String str) {
        this.f4987a = str;
        return this;
    }

    public void m5164d() {
        this.f4987a = null;
    }

    public boolean m5166e() {
        return this.f4987a != null;
    }

    public void m5155a(boolean z) {
        if (!z) {
            this.f4987a = null;
        }
    }

    public String m5167f() {
        return this.f4988b;
    }

    public ba m5156b(String str) {
        this.f4988b = str;
        return this;
    }

    public void m5168g() {
        this.f4988b = null;
    }

    public boolean m5169h() {
        return this.f4988b != null;
    }

    public void m5160b(boolean z) {
        if (!z) {
            this.f4988b = null;
        }
    }

    public String m5170i() {
        return this.f4989c;
    }

    public ba m5162c(String str) {
        this.f4989c = str;
        return this;
    }

    public void m5171j() {
        this.f4989c = null;
    }

    public boolean m5172k() {
        return this.f4989c != null;
    }

    public void m5163c(boolean z) {
        if (!z) {
            this.f4989c = null;
        }
    }

    public long m5173l() {
        return this.f4990d;
    }

    public ba m5152a(long j) {
        this.f4990d = j;
        m5165d(true);
        return this;
    }

    public void m5174m() {
        this.f4991m = bm.m3612b(this.f4991m, 0);
    }

    public boolean m5175n() {
        return bm.m3608a(this.f4991m, 0);
    }

    public void m5165d(boolean z) {
        this.f4991m = bm.m3600a(this.f4991m, 0, z);
    }

    public C1926e m5150a(int i) {
        return C1926e.m5138a(i);
    }

    public void mo2765a(co coVar) throws bv {
        ((cx) f4985k.get(coVar.mo3683D())).mo2762b().mo3682b(coVar, this);
    }

    public void mo2768b(co coVar) throws bv {
        ((cx) f4985k.get(coVar.mo3683D())).mo2762b().mo3681a(coVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("IdJournal(");
        stringBuilder.append("domain:");
        if (this.f4987a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f4987a);
        }
        if (m5169h()) {
            stringBuilder.append(", ");
            stringBuilder.append("old_id:");
            if (this.f4988b == null) {
                stringBuilder.append("null");
            } else {
                stringBuilder.append(this.f4988b);
            }
        }
        stringBuilder.append(", ");
        stringBuilder.append("new_id:");
        if (this.f4989c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f4989c);
        }
        stringBuilder.append(", ");
        stringBuilder.append("ts:");
        stringBuilder.append(this.f4990d);
        stringBuilder.append(SocializeConstants.OP_CLOSE_PAREN);
        return stringBuilder.toString();
    }

    public void m5176o() throws bv {
        if (this.f4987a == null) {
            throw new cp("Required field 'domain' was not present! Struct: " + toString());
        } else if (this.f4989c == null) {
            throw new cp("Required field 'new_id' was not present! Struct: " + toString());
        }
    }

    private void m5144a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            mo2768b(new ci(new da((OutputStream) objectOutputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }

    private void m5143a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f4991m = (byte) 0;
            mo2765a(new ci(new da((InputStream) objectInputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }
}
