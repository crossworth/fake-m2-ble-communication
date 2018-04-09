package p031u.aly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: UMCCAggregatedObject */
public class C1506i implements Serializable {
    private static final long f3821a = 1;
    private List<String> f3822b = new ArrayList();
    private List<String> f3823c = new ArrayList();
    private long f3824d = 0;
    private long f3825e = 0;
    private long f3826f = 0;
    private String f3827g = null;

    public C1506i(List<String> list, long j, long j2, long j3, List<String> list2, String str) {
        this.f3822b = list;
        this.f3823c = list2;
        this.f3824d = j;
        this.f3825e = j2;
        this.f3826f = j3;
        this.f3827g = str;
    }

    public void m3777a(String str) {
        try {
            if (this.f3823c.size() < C1516n.m3847a().m3851b()) {
                this.f3823c.add(str);
            } else {
                this.f3823c.remove(this.f3823c.get(0));
                this.f3823c.add(str);
            }
            if (this.f3823c.size() > C1516n.m3847a().m3851b()) {
                for (int i = 0; i < this.f3823c.size() - C1516n.m3847a().m3851b(); i++) {
                    this.f3823c.remove(this.f3823c.get(0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void m3779a(C1950f c1950f, C1509l c1509l) {
        m3777a(c1509l.m3802b());
        this.f3826f++;
        this.f3825e += c1509l.m3803c();
        this.f3824d += c1509l.m3804d();
        c1950f.mo2823a(this, false);
    }

    public void m3780a(C1509l c1509l) {
        this.f3826f = 1;
        this.f3822b = c1509l.m3801a();
        m3777a(c1509l.m3802b());
        this.f3825e = c1509l.m3803c();
        this.f3824d = System.currentTimeMillis();
        this.f3827g = C1521q.m3878a(System.currentTimeMillis());
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("[key: ").append(this.f3822b).append("] [label: ").append(this.f3823c).append("][ totalTimeStamp").append(this.f3827g).append("][ value").append(this.f3825e).append("][ count").append(this.f3826f).append("][ timeWindowNum").append(this.f3827g).append("]");
        return stringBuffer.toString();
    }

    public String m3775a() {
        return C1502d.m3741a(this.f3822b);
    }

    public List<String> m3781b() {
        return this.f3822b;
    }

    public String m3785c() {
        return C1502d.m3741a(this.f3823c);
    }

    public List<String> m3787d() {
        return this.f3823c;
    }

    public long m3788e() {
        return this.f3824d;
    }

    public long m3789f() {
        return this.f3825e;
    }

    public long m3790g() {
        return this.f3826f;
    }

    public String m3791h() {
        return this.f3827g;
    }

    public void m3778a(List<String> list) {
        this.f3822b = list;
    }

    public void m3784b(List<String> list) {
        this.f3823c = list;
    }

    public void m3776a(long j) {
        this.f3824d = j;
    }

    public void m3782b(long j) {
        this.f3825e = j;
    }

    public void m3786c(long j) {
        this.f3826f = j;
    }

    public void m3783b(String str) {
        this.f3827g = str;
    }
}
