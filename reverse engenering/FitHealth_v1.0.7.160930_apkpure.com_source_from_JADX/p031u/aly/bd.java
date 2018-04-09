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
import java.util.Map.Entry;

/* compiled from: Imprint */
public class bd implements Serializable, Cloneable, bp<bd, C1935e> {
    public static final Map<C1935e, cb> f5036d;
    private static final ct f5037e = new ct("Imprint");
    private static final cj f5038f = new cj("property", cv.f3781k, (short) 1);
    private static final cj f5039g = new cj("version", (byte) 8, (short) 2);
    private static final cj f5040h = new cj("checksum", (byte) 11, (short) 3);
    private static final Map<Class<? extends cw>, cx> f5041i = new HashMap();
    private static final int f5042j = 0;
    public Map<String, be> f5043a;
    public int f5044b;
    public String f5045c;
    private byte f5046k;

    /* compiled from: Imprint */
    private static class C1933b implements cx {
        private C1933b() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5259a();
        }

        public C2026a m5259a() {
            return new C2026a();
        }
    }

    /* compiled from: Imprint */
    private static class C1934d implements cx {
        private C1934d() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5261a();
        }

        public C2027c m5261a() {
            return new C2027c();
        }
    }

    /* compiled from: Imprint */
    public enum C1935e implements bw {
        PROPERTY((short) 1, "property"),
        VERSION((short) 2, "version"),
        CHECKSUM((short) 3, "checksum");
        
        private static final Map<String, C1935e> f5032d = null;
        private final short f5034e;
        private final String f5035f;

        static {
            f5032d = new HashMap();
            Iterator it = EnumSet.allOf(C1935e.class).iterator();
            while (it.hasNext()) {
                C1935e c1935e = (C1935e) it.next();
                f5032d.put(c1935e.mo2764b(), c1935e);
            }
        }

        public static C1935e m5263a(int i) {
            switch (i) {
                case 1:
                    return PROPERTY;
                case 2:
                    return VERSION;
                case 3:
                    return CHECKSUM;
                default:
                    return null;
            }
        }

        public static C1935e m5265b(int i) {
            C1935e a = C1935e.m5263a(i);
            if (a != null) {
                return a;
            }
            throw new IllegalArgumentException("Field " + i + " doesn't exist!");
        }

        public static C1935e m5264a(String str) {
            return (C1935e) f5032d.get(str);
        }

        private C1935e(short s, String str) {
            this.f5034e = s;
            this.f5035f = str;
        }

        public short mo2763a() {
            return this.f5034e;
        }

        public String mo2764b() {
            return this.f5035f;
        }
    }

    /* compiled from: Imprint */
    private static class C2026a extends cy<bd> {
        private C2026a() {
        }

        public /* synthetic */ void mo3681a(co coVar, bp bpVar) throws bv {
            m6147b(coVar, (bd) bpVar);
        }

        public /* synthetic */ void mo3682b(co coVar, bp bpVar) throws bv {
            m6145a(coVar, (bd) bpVar);
        }

        public void m6145a(co coVar, bd bdVar) throws bv {
            coVar.mo2795j();
            while (true) {
                cj l = coVar.mo2797l();
                if (l.f3752b == (byte) 0) {
                    coVar.mo2796k();
                    if (bdVar.m5293i()) {
                        bdVar.m5297m();
                        return;
                    }
                    throw new cp("Required field 'version' was not found in serialized data! Struct: " + toString());
                }
                switch (l.f3753c) {
                    case (short) 1:
                        if (l.f3752b != cv.f3781k) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        cl n = coVar.mo2799n();
                        bdVar.f5043a = new HashMap(n.f3758c * 2);
                        for (int i = 0; i < n.f3758c; i++) {
                            String z = coVar.mo2811z();
                            be beVar = new be();
                            beVar.mo2765a(coVar);
                            bdVar.f5043a.put(z, beVar);
                        }
                        coVar.mo2800o();
                        bdVar.m5280a(true);
                        break;
                    case (short) 2:
                        if (l.f3752b != (byte) 8) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bdVar.f5044b = coVar.mo2808w();
                        bdVar.m5284b(true);
                        break;
                    case (short) 3:
                        if (l.f3752b != (byte) 11) {
                            cr.m3735a(coVar, l.f3752b);
                            break;
                        }
                        bdVar.f5045c = coVar.mo2811z();
                        bdVar.m5287c(true);
                        break;
                    default:
                        cr.m3735a(coVar, l.f3752b);
                        break;
                }
                coVar.mo2798m();
            }
        }

        public void m6147b(co coVar, bd bdVar) throws bv {
            bdVar.m5297m();
            coVar.mo2784a(bd.f5037e);
            if (bdVar.f5043a != null) {
                coVar.mo2779a(bd.f5038f);
                coVar.mo2781a(new cl((byte) 11, (byte) 12, bdVar.f5043a.size()));
                for (Entry entry : bdVar.f5043a.entrySet()) {
                    coVar.mo2777a((String) entry.getKey());
                    ((be) entry.getValue()).mo2768b(coVar);
                }
                coVar.mo2790e();
                coVar.mo2788c();
            }
            coVar.mo2779a(bd.f5039g);
            coVar.mo2775a(bdVar.f5044b);
            coVar.mo2788c();
            if (bdVar.f5045c != null) {
                coVar.mo2779a(bd.f5040h);
                coVar.mo2777a(bdVar.f5045c);
                coVar.mo2788c();
            }
            coVar.mo2789d();
            coVar.mo2787b();
        }
    }

    /* compiled from: Imprint */
    private static class C2027c extends cz<bd> {
        private C2027c() {
        }

        public void m6149a(co coVar, bd bdVar) throws bv {
            coVar = (cu) coVar;
            coVar.mo2775a(bdVar.f5043a.size());
            for (Entry entry : bdVar.f5043a.entrySet()) {
                coVar.mo2777a((String) entry.getKey());
                ((be) entry.getValue()).mo2768b(coVar);
            }
            coVar.mo2775a(bdVar.f5044b);
            coVar.mo2777a(bdVar.f5045c);
        }

        public void m6151b(co coVar, bd bdVar) throws bv {
            coVar = (cu) coVar;
            cl clVar = new cl((byte) 11, (byte) 12, coVar.mo2808w());
            bdVar.f5043a = new HashMap(clVar.f3758c * 2);
            for (int i = 0; i < clVar.f3758c; i++) {
                String z = coVar.mo2811z();
                be beVar = new be();
                beVar.mo2765a(coVar);
                bdVar.f5043a.put(z, beVar);
            }
            bdVar.m5280a(true);
            bdVar.f5044b = coVar.mo2808w();
            bdVar.m5284b(true);
            bdVar.f5045c = coVar.mo2811z();
            bdVar.m5287c(true);
        }
    }

    public /* synthetic */ bw mo2766b(int i) {
        return m5286c(i);
    }

    public /* synthetic */ bp mo2769p() {
        return m5274a();
    }

    static {
        f5041i.put(cy.class, new C1933b());
        f5041i.put(cz.class, new C1934d());
        Map enumMap = new EnumMap(C1935e.class);
        enumMap.put(C1935e.PROPERTY, new cb("property", (byte) 1, new ce(cv.f3781k, new cc((byte) 11), new cg((byte) 12, be.class))));
        enumMap.put(C1935e.VERSION, new cb("version", (byte) 1, new cc((byte) 8)));
        enumMap.put(C1935e.CHECKSUM, new cb("checksum", (byte) 1, new cc((byte) 11)));
        f5036d = Collections.unmodifiableMap(enumMap);
        cb.m3680a(bd.class, f5036d);
    }

    public bd() {
        this.f5046k = (byte) 0;
    }

    public bd(Map<String, be> map, int i, String str) {
        this();
        this.f5043a = map;
        this.f5044b = i;
        m5284b(true);
        this.f5045c = str;
    }

    public bd(bd bdVar) {
        this.f5046k = (byte) 0;
        this.f5046k = bdVar.f5046k;
        if (bdVar.m5290f()) {
            Map hashMap = new HashMap();
            for (Entry entry : bdVar.f5043a.entrySet()) {
                hashMap.put((String) entry.getKey(), new be((be) entry.getValue()));
            }
            this.f5043a = hashMap;
        }
        this.f5044b = bdVar.f5044b;
        if (bdVar.m5296l()) {
            this.f5045c = bdVar.f5045c;
        }
    }

    public bd m5274a() {
        return new bd(this);
    }

    public void mo2767b() {
        this.f5043a = null;
        m5284b(false);
        this.f5044b = 0;
        this.f5045c = null;
    }

    public int m5285c() {
        return this.f5043a == null ? 0 : this.f5043a.size();
    }

    public void m5278a(String str, be beVar) {
        if (this.f5043a == null) {
            this.f5043a = new HashMap();
        }
        this.f5043a.put(str, beVar);
    }

    public Map<String, be> m5288d() {
        return this.f5043a;
    }

    public bd m5277a(Map<String, be> map) {
        this.f5043a = map;
        return this;
    }

    public void m5289e() {
        this.f5043a = null;
    }

    public boolean m5290f() {
        return this.f5043a != null;
    }

    public void m5280a(boolean z) {
        if (!z) {
            this.f5043a = null;
        }
    }

    public int m5291g() {
        return this.f5044b;
    }

    public bd m5275a(int i) {
        this.f5044b = i;
        m5284b(true);
        return this;
    }

    public void m5292h() {
        this.f5046k = bm.m3612b(this.f5046k, 0);
    }

    public boolean m5293i() {
        return bm.m3608a(this.f5046k, 0);
    }

    public void m5284b(boolean z) {
        this.f5046k = bm.m3600a(this.f5046k, 0, z);
    }

    public String m5294j() {
        return this.f5045c;
    }

    public bd m5276a(String str) {
        this.f5045c = str;
        return this;
    }

    public void m5295k() {
        this.f5045c = null;
    }

    public boolean m5296l() {
        return this.f5045c != null;
    }

    public void m5287c(boolean z) {
        if (!z) {
            this.f5045c = null;
        }
    }

    public C1935e m5286c(int i) {
        return C1935e.m5263a(i);
    }

    public void mo2765a(co coVar) throws bv {
        ((cx) f5041i.get(coVar.mo3683D())).mo2762b().mo3682b(coVar, this);
    }

    public void mo2768b(co coVar) throws bv {
        ((cx) f5041i.get(coVar.mo3683D())).mo2762b().mo3681a(coVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("Imprint(");
        stringBuilder.append("property:");
        if (this.f5043a == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f5043a);
        }
        stringBuilder.append(", ");
        stringBuilder.append("version:");
        stringBuilder.append(this.f5044b);
        stringBuilder.append(", ");
        stringBuilder.append("checksum:");
        if (this.f5045c == null) {
            stringBuilder.append("null");
        } else {
            stringBuilder.append(this.f5045c);
        }
        stringBuilder.append(SocializeConstants.OP_CLOSE_PAREN);
        return stringBuilder.toString();
    }

    public void m5297m() throws bv {
        if (this.f5043a == null) {
            throw new cp("Required field 'property' was not present! Struct: " + toString());
        } else if (this.f5045c == null) {
            throw new cp("Required field 'checksum' was not present! Struct: " + toString());
        }
    }

    private void m5269a(ObjectOutputStream objectOutputStream) throws IOException {
        try {
            mo2768b(new ci(new da((OutputStream) objectOutputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }

    private void m5268a(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        try {
            this.f5046k = (byte) 0;
            mo2765a(new ci(new da((InputStream) objectInputStream)));
        } catch (bv e) {
            throw new IOException(e.getMessage());
        }
    }
}
