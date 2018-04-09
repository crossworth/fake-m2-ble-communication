package p031u.aly;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import com.facebook.GraphResponse;
import java.util.List;
import java.util.Map;
import p031u.aly.av.C1465e;
import p031u.aly.av.C1466f;

/* compiled from: UMCCStorageManager */
public class C1519o {
    private static Context f3867a;

    /* compiled from: UMCCStorageManager */
    private static final class C1518a {
        private static final C1519o f3866a = new C1519o();

        private C1518a() {
        }
    }

    private C1519o() {
        if (f3867a == null) {
        }
    }

    public static C1519o m3857a(Context context) {
        f3867a = context;
        return C1518a.f3866a;
    }

    public void m3859a(C1950f c1950f) {
        try {
            SQLiteDatabase a = C1474b.m3483a(f3867a).m3485a();
            String a2 = C1459a.m3391a(a);
            String a3 = C1521q.m3878a(System.currentTimeMillis());
            if (a2.equals("0")) {
                c1950f.mo2823a("faild", false);
                return;
            }
            if (a2.equals(a3)) {
                C1459a.m3403b(a, c1950f);
            } else {
                C1459a.m3399a(a, c1950f);
            }
            C1474b.m3483a(f3867a).m3487c();
        } catch (Exception e) {
            c1950f.mo2823a(Boolean.valueOf(false), false);
            bl.m3594e("load agg data error");
        } finally {
            C1474b.m3483a(f3867a).m3487c();
        }
    }

    public void m3862a(C1950f c1950f, Map<List<String>, C1506i> map) {
        try {
            C1459a.m3398a(C1474b.m3483a(f3867a).m3486b(), map.values());
            c1950f.mo2823a(GraphResponse.SUCCESS_KEY, false);
        } catch (Exception e) {
            bl.m3594e("save agg data error");
        } finally {
            C1474b.m3483a(f3867a).m3487c();
        }
    }

    public Map<String, List<C1465e>> m3858a() {
        Map<String, List<C1465e>> b;
        try {
            b = C1459a.m3401b(C1474b.m3483a(f3867a).m3485a());
        } catch (Exception e) {
            bl.m3594e("upload agg date error");
            return null;
        } finally {
            C1474b.m3483a(f3867a).m3487c();
        }
        return b;
    }

    public Map<String, List<C1466f>> m3865b(C1950f c1950f) {
        Map<String, List<C1466f>> a;
        try {
            a = C1459a.m3392a(c1950f, C1474b.m3483a(f3867a).m3485a());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            C1474b.m3483a(f3867a).m3487c();
        }
        return a;
    }

    public void m3863a(C1950f c1950f, boolean z) {
        try {
            C1459a.m3395a(C1474b.m3483a(f3867a).m3486b(), z, c1950f);
        } catch (Exception e) {
            bl.m3594e("notifyUploadSuccess error");
        } finally {
            C1474b.m3483a(f3867a).m3487c();
        }
    }

    public void m3860a(C1950f c1950f, String str, long j, long j2) {
        try {
            C1459a.m3393a(C1474b.m3483a(f3867a).m3486b(), str, j, j2);
            c1950f.mo2823a(GraphResponse.SUCCESS_KEY, false);
        } catch (Exception e) {
            bl.m3594e("package size to big or envelopeOverflowPackageCount exception");
        } finally {
            C1474b.m3483a(f3867a).m3487c();
        }
    }

    public void m3861a(C1950f c1950f, List<String> list) {
        try {
            C1459a.m3396a(c1950f, C1474b.m3483a(f3867a).m3486b(), (List) list);
        } catch (Exception e) {
            bl.m3594e("saveToLimitCKTable exception");
        } finally {
            C1474b.m3483a(f3867a).m3487c();
        }
    }

    public void m3866b(C1950f c1950f, Map<String, C1508k> map) {
        try {
            C1459a.m3394a(C1474b.m3483a(f3867a).m3486b(), (Map) map, c1950f);
        } catch (Exception e) {
            bl.m3594e("arrgetated system buffer exception");
        } finally {
            C1474b.m3483a(f3867a).m3487c();
        }
    }

    public List<String> m3864b() {
        List<String> c;
        try {
            c = C1459a.m3405c(C1474b.m3483a(f3867a).m3485a());
        } catch (Exception e) {
            bl.m3594e("loadCKToMemory exception");
            return null;
        } finally {
            C1474b.m3483a(f3867a).m3487c();
        }
        return c;
    }

    public void m3867c(C1950f c1950f, Map<List<String>, C1506i> map) {
        try {
            C1459a.m3400a(c1950f, C1474b.m3483a(f3867a).m3486b(), map.values());
        } catch (Exception e) {
            bl.m3594e("cacheToData error");
        } finally {
            C1474b.m3483a(f3867a).m3487c();
        }
    }
}
