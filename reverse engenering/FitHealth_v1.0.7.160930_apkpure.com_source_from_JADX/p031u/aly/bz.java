package p031u.aly;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/* compiled from: TUnion */
public abstract class bz<T extends bz<?, ?>, F extends bw> implements bp<T, F> {
    private static final Map<Class<? extends cw>, cx> f5141c = new HashMap();
    protected Object f5142a;
    protected F f5143b;

    /* compiled from: TUnion */
    private static class C1945b implements cx {
        private C1945b() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5460a();
        }

        public C2034a m5460a() {
            return new C2034a();
        }
    }

    /* compiled from: TUnion */
    private static class C1946d implements cx {
        private C1946d() {
        }

        public /* synthetic */ cw mo2762b() {
            return m5462a();
        }

        public C2035c m5462a() {
            return new C2035c();
        }
    }

    /* compiled from: TUnion */
    private static class C2034a extends cy<bz> {
        private C2034a() {
        }

        public /* synthetic */ void mo3681a(co coVar, bp bpVar) throws bv {
            m6180b(coVar, (bz) bpVar);
        }

        public /* synthetic */ void mo3682b(co coVar, bp bpVar) throws bv {
            m6178a(coVar, (bz) bpVar);
        }

        public void m6178a(co coVar, bz bzVar) throws bv {
            bzVar.f5143b = null;
            bzVar.f5142a = null;
            coVar.mo2795j();
            cj l = coVar.mo2797l();
            bzVar.f5142a = bzVar.m5470a(coVar, l);
            if (bzVar.f5142a != null) {
                bzVar.f5143b = bzVar.m5473a(l.f3753c);
            }
            coVar.mo2798m();
            coVar.mo2797l();
            coVar.mo2796k();
        }

        public void m6180b(co coVar, bz bzVar) throws bv {
            if (bzVar.m5472a() == null || bzVar.m5481c() == null) {
                throw new cp("Cannot write a TUnion with no set value!");
            }
            coVar.mo2784a(bzVar.m5487e());
            coVar.mo2779a(bzVar.m5482c(bzVar.f5143b));
            bzVar.m5483c(coVar);
            coVar.mo2788c();
            coVar.mo2789d();
            coVar.mo2787b();
        }
    }

    /* compiled from: TUnion */
    private static class C2035c extends cz<bz> {
        private C2035c() {
        }

        public /* synthetic */ void mo3681a(co coVar, bp bpVar) throws bv {
            m6184b(coVar, (bz) bpVar);
        }

        public /* synthetic */ void mo3682b(co coVar, bp bpVar) throws bv {
            m6182a(coVar, (bz) bpVar);
        }

        public void m6182a(co coVar, bz bzVar) throws bv {
            bzVar.f5143b = null;
            bzVar.f5142a = null;
            short v = coVar.mo2807v();
            bzVar.f5142a = bzVar.m5471a(coVar, v);
            if (bzVar.f5142a != null) {
                bzVar.f5143b = bzVar.m5473a(v);
            }
        }

        public void m6184b(co coVar, bz bzVar) throws bv {
            if (bzVar.m5472a() == null || bzVar.m5481c() == null) {
                throw new cp("Cannot write a TUnion with no set value!");
            }
            coVar.mo2785a(bzVar.f5143b.mo2763a());
            bzVar.m5485d(coVar);
        }
    }

    protected abstract Object m5470a(co coVar, cj cjVar) throws bv;

    protected abstract Object m5471a(co coVar, short s) throws bv;

    protected abstract F m5473a(short s);

    protected abstract void m5478b(F f, Object obj) throws ClassCastException;

    protected abstract cj m5482c(F f);

    protected abstract void m5483c(co coVar) throws bv;

    protected abstract void m5485d(co coVar) throws bv;

    protected abstract ct m5487e();

    protected bz() {
        this.f5143b = null;
        this.f5142a = null;
    }

    static {
        f5141c.put(cy.class, new C1945b());
        f5141c.put(cz.class, new C1946d());
    }

    protected bz(F f, Object obj) {
        m5475a((bw) f, obj);
    }

    protected bz(bz<T, F> bzVar) {
        if (bzVar.getClass().equals(getClass())) {
            this.f5143b = bzVar.f5143b;
            this.f5142a = bz.m5464a(bzVar.f5142a);
            return;
        }
        throw new ClassCastException();
    }

    private static Object m5464a(Object obj) {
        if (obj instanceof bp) {
            return ((bp) obj).mo2769p();
        }
        if (obj instanceof ByteBuffer) {
            return bq.m3646d((ByteBuffer) obj);
        }
        if (obj instanceof List) {
            return bz.m5465a((List) obj);
        }
        if (obj instanceof Set) {
            return bz.m5467a((Set) obj);
        }
        if (obj instanceof Map) {
            return bz.m5466a((Map) obj);
        }
        return obj;
    }

    private static Map m5466a(Map<Object, Object> map) {
        Map hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            hashMap.put(bz.m5464a(entry.getKey()), bz.m5464a(entry.getValue()));
        }
        return hashMap;
    }

    private static Set m5467a(Set set) {
        Set hashSet = new HashSet();
        for (Object a : set) {
            hashSet.add(bz.m5464a(a));
        }
        return hashSet;
    }

    private static List m5465a(List list) {
        List arrayList = new ArrayList(list.size());
        for (Object a : list) {
            arrayList.add(bz.m5464a(a));
        }
        return arrayList;
    }

    public F m5472a() {
        return this.f5143b;
    }

    public Object m5481c() {
        return this.f5142a;
    }

    public Object m5469a(F f) {
        if (f == this.f5143b) {
            return m5481c();
        }
        throw new IllegalArgumentException("Cannot get the value of field " + f + " because union's set field is " + this.f5143b);
    }

    public Object m5468a(int i) {
        return m5469a(m5473a((short) i));
    }

    public boolean m5486d() {
        return this.f5143b != null;
    }

    public boolean m5480b(F f) {
        return this.f5143b == f;
    }

    public boolean m5484c(int i) {
        return m5480b(m5473a((short) i));
    }

    public void mo2765a(co coVar) throws bv {
        ((cx) f5141c.get(coVar.mo3683D())).mo2762b().mo3682b(coVar, this);
    }

    public void m5475a(F f, Object obj) {
        m5478b(f, obj);
        this.f5143b = f;
        this.f5142a = obj;
    }

    public void m5474a(int i, Object obj) {
        m5475a(m5473a((short) i), obj);
    }

    public void mo2768b(co coVar) throws bv {
        ((cx) f5141c.get(coVar.mo3683D())).mo2762b().mo3681a(coVar, this);
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("<");
        stringBuilder.append(getClass().getSimpleName());
        stringBuilder.append(" ");
        if (m5472a() != null) {
            Object c = m5481c();
            stringBuilder.append(m5482c(m5472a()).f3751a);
            stringBuilder.append(":");
            if (c instanceof ByteBuffer) {
                bq.m3641a((ByteBuffer) c, stringBuilder);
            } else {
                stringBuilder.append(c.toString());
            }
        }
        stringBuilder.append(">");
        return stringBuilder.toString();
    }

    public final void mo2767b() {
        this.f5143b = null;
        this.f5142a = null;
    }
}
