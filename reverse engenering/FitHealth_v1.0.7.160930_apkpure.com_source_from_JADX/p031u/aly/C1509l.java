package p031u.aly;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/* compiled from: UMCCVerbatimObject */
public class C1509l implements Serializable {
    private static final long f3842a = 1;
    private List<String> f3843b = new ArrayList();
    private String f3844c;
    private long f3845d;
    private long f3846e;
    private String f3847f;

    public C1509l(List<String> list, long j, String str, long j2) {
        this.f3843b = list;
        this.f3845d = j;
        this.f3844c = str;
        this.f3846e = j2;
        m3800f();
    }

    private void m3800f() {
        this.f3847f = C1521q.m3878a(this.f3846e);
    }

    public List<String> m3801a() {
        return this.f3843b;
    }

    public String m3802b() {
        return this.f3844c;
    }

    public long m3803c() {
        return this.f3845d;
    }

    public long m3804d() {
        return this.f3846e;
    }

    public String m3805e() {
        return this.f3847f;
    }
}
