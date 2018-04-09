package p031u.aly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: UMCCAggregatedListObject */
public class C1505h implements Serializable {
    private static final long f3818a = 1;
    private Map<List<String>, C1506i> f3819b = new HashMap();
    private long f3820c = 0;

    public Map<List<String>, C1506i> m3763a() {
        return this.f3819b;
    }

    public void m3765a(Map<List<String>, C1506i> map) {
        if (this.f3819b.size() <= 0) {
            this.f3819b = map;
        } else {
            m3762b(map);
        }
    }

    private void m3762b(Map<List<String>, C1506i> map) {
        ArrayList arrayList = new ArrayList();
        arrayList = new ArrayList();
        Iterator it = this.f3819b.entrySet().iterator();
        while (it.hasNext()) {
            Entry entry = (Entry) it.next();
            List list = (List) entry.getKey();
            Iterator it2 = this.f3819b.entrySet().iterator();
            while (it.hasNext()) {
                Entry entry2 = (Entry) it2.next();
                List list2 = (List) entry.getKey();
                if (list.equals(list2)) {
                    C1506i c1506i = (C1506i) entry2.getValue();
                    m3761a((C1506i) entry.getValue(), c1506i);
                    this.f3819b.remove(list);
                    this.f3819b.put(list, c1506i);
                } else {
                    this.f3819b.put(list2, entry2.getValue());
                }
            }
        }
    }

    private void m3761a(C1506i c1506i, C1506i c1506i2) {
        c1506i2.m3786c(c1506i2.m3790g() + c1506i.m3790g());
        c1506i2.m3782b(c1506i2.m3789f() + c1506i.m3789f());
        c1506i2.m3776a(c1506i2.m3788e() + c1506i.m3788e());
        for (int i = 0; i < c1506i.m3787d().size(); i++) {
            c1506i2.m3777a((String) c1506i.m3787d().get(i));
        }
    }

    public long m3772b() {
        return this.f3820c;
    }

    public void m3764a(long j) {
        this.f3820c = j;
    }

    public void m3768a(final C1950f c1950f, C1509l c1509l) {
        try {
            if (m3770a(c1509l.m3801a())) {
                C1506i c1506i = (C1506i) this.f3819b.get(c1509l.m3801a());
                if (c1506i != null) {
                    c1506i.m3779a(new C1950f(this) {
                        final /* synthetic */ C1505h f5547b;

                        public void mo2823a(Object obj, boolean z) {
                            C1506i c1506i = (C1506i) obj;
                            this.f5547b.f3819b.remove(c1506i.m3775a());
                            this.f5547b.f3819b.put(c1506i.m3781b(), c1506i);
                            c1950f.mo2823a(this, false);
                        }
                    }, c1509l);
                    return;
                } else {
                    m3767a(c1950f, c1509l.m3801a(), c1509l);
                    return;
                }
            }
            m3767a(c1950f, c1509l.m3801a(), c1509l);
        } catch (Exception e) {
            bl.m3594e("aggregated faild!");
        }
    }

    public void m3767a(C1950f c1950f, List<String> list, C1509l c1509l) {
        C1506i c1506i = new C1506i();
        c1506i.m3780a(c1509l);
        this.f3819b.put(list, c1506i);
        c1950f.mo2823a(this, false);
    }

    public boolean m3770a(List<?> list) {
        if (this.f3819b == null || !this.f3819b.containsKey(list)) {
            return false;
        }
        return true;
    }

    public void m3766a(C1950f c1950f) {
        for (List list : this.f3819b.keySet()) {
            if (!c1950f.m5620a()) {
                c1950f.mo2823a(this.f3819b.get(list), false);
            } else {
                return;
            }
        }
    }

    public int m3773c() {
        if (this.f3819b != null) {
            return this.f3819b.size();
        }
        return 0;
    }

    public void m3774d() {
        this.f3819b.clear();
    }

    public boolean m3771a(List<String> list, List<String> list2) {
        if (list == null || list.size() == 0) {
            return false;
        }
        List arrayList = new ArrayList();
        for (int i = 0; i < list.size() - 1; i++) {
            arrayList.add(C1502d.m3742a((String) list.get(i)));
        }
        if (list == null || list.size() == 0) {
            return false;
        }
        return arrayList.contains(list2);
    }

    public void m3769a(C1950f c1950f, C1509l c1509l, List<String> list, List<String> list2) {
        while (list.size() >= 1) {
            try {
                if (list.size() == 1) {
                    if (m3771a((List) list2, (List) list)) {
                        m3760a(c1950f, c1509l, (List) list);
                        return;
                    } else {
                        c1950f.mo2823a(Boolean.valueOf(false), false);
                        return;
                    }
                } else if (m3771a((List) list2, (List) list)) {
                    m3760a(c1950f, c1509l, (List) list);
                    return;
                } else {
                    list.remove(list.size() - 1);
                }
            } catch (Exception e) {
                bl.m3594e("overFlowAggregated faild");
                return;
            }
        }
    }

    private void m3760a(C1950f c1950f, C1509l c1509l, List<String> list) {
        if (m3770a((List) list)) {
            m3768a(c1950f, c1509l);
        } else {
            m3767a(c1950f, (List) list, c1509l);
        }
    }
}
