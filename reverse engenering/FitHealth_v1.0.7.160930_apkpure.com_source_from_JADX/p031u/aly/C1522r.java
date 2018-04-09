package p031u.aly;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/* compiled from: AbstractIdTracker */
public abstract class C1522r {
    private final int f3874a = 10;
    private final int f3875b = 20;
    private final String f3876c;
    private List<ba> f3877d;
    private bb f3878e;

    public abstract String mo2746f();

    public C1522r(String str) {
        this.f3876c = str;
    }

    public boolean m3889a() {
        return m3884g();
    }

    public String m3890b() {
        return this.f3876c;
    }

    public boolean m3891c() {
        if (this.f3878e == null || this.f3878e.m5211i() <= 20) {
            return true;
        }
        return false;
    }

    private boolean m3884g() {
        bb bbVar = this.f3878e;
        String c = bbVar == null ? null : bbVar.m5203c();
        int i = bbVar == null ? 0 : bbVar.m5211i();
        String a = m3885a(mo2746f());
        if (a == null || a.equals(c)) {
            return false;
        }
        if (bbVar == null) {
            bbVar = new bb();
        }
        bbVar.m5196a(a);
        bbVar.m5195a(System.currentTimeMillis());
        bbVar.m5194a(i + 1);
        ba baVar = new ba();
        baVar.m5153a(this.f3876c);
        baVar.m5162c(a);
        baVar.m5156b(c);
        baVar.m5152a(bbVar.m5208f());
        if (this.f3877d == null) {
            this.f3877d = new ArrayList(2);
        }
        this.f3877d.add(baVar);
        if (this.f3877d.size() > 10) {
            this.f3877d.remove(0);
        }
        this.f3878e = bbVar;
        return true;
    }

    public bb m3892d() {
        return this.f3878e;
    }

    public void m3887a(bb bbVar) {
        this.f3878e = bbVar;
    }

    public List<ba> m3893e() {
        return this.f3877d;
    }

    public void m3886a(List<ba> list) {
        this.f3877d = list;
    }

    public String m3885a(String str) {
        if (str == null) {
            return null;
        }
        String trim = str.trim();
        if (trim.length() == 0 || "0".equals(trim) || "unknown".equals(trim.toLowerCase(Locale.US))) {
            return null;
        }
        return trim;
    }

    public void m3888a(bc bcVar) {
        this.f3878e = (bb) bcVar.m5246d().get(this.f3876c);
        List<ba> i = bcVar.m5251i();
        if (i != null && i.size() > 0) {
            if (this.f3877d == null) {
                this.f3877d = new ArrayList();
            }
            for (ba baVar : i) {
                if (this.f3876c.equals(baVar.f4987a)) {
                    this.f3877d.add(baVar);
                }
            }
        }
    }
}
