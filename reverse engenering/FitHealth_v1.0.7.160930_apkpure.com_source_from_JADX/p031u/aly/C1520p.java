package p031u.aly;

import android.text.TextUtils;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/* compiled from: UMCCSystemBufferManager */
public class C1520p implements Serializable {
    private static final long f3868a = 1;
    private Map<String, C1508k> f3869b = new HashMap();

    public void m3873a(C1950f c1950f, String str) {
        if (this.f3869b.containsKey(str)) {
            m3870c(str);
        } else {
            m3868b(str);
        }
        c1950f.mo2823a(this, false);
    }

    public Map<String, C1508k> m3871a() {
        return this.f3869b;
    }

    public void m3876b() {
        this.f3869b.clear();
    }

    public void m3872a(Map<String, C1508k> map) {
        this.f3869b = map;
    }

    public boolean m3875a(String str) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        for (Entry key : this.f3869b.entrySet()) {
            if (((String) key.getKey()).equals(str)) {
                return true;
            }
        }
        return false;
    }

    private void m3868b(String str) {
        this.f3869b.put(str, new C1508k(str, System.currentTimeMillis(), 1));
    }

    private void m3870c(String str) {
        this.f3869b.put(str, ((C1508k) this.f3869b.get(str)).m3792a());
    }

    public void m3874a(C1508k c1508k) {
        if (m3875a(c1508k.m3797c())) {
            m3869b(c1508k);
        } else {
            this.f3869b.put(c1508k.m3797c(), c1508k);
        }
    }

    private void m3869b(C1508k c1508k) {
        this.f3869b.put(c1508k.m3797c(), ((C1508k) this.f3869b.get(c1508k.m3797c())).m3793a(c1508k));
    }
}
