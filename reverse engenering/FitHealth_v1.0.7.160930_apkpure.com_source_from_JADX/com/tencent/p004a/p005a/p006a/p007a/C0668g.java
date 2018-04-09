package com.tencent.p004a.p005a.p006a.p007a;

import android.content.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class C0668g {
    private static C0668g f2317i = null;
    private Map<Integer, C0667f> f2318f = null;
    private int f2319g = 0;
    private Context f2320h = null;

    private C0668g(Context context) {
        this.f2320h = context.getApplicationContext();
        this.f2318f = new HashMap(3);
        this.f2318f.put(Integer.valueOf(1), new C1709e(context));
        this.f2318f.put(Integer.valueOf(2), new C1707b(context));
        this.f2318f.put(Integer.valueOf(4), new C1708d(context));
    }

    private C0666c m2229a(List<Integer> list) {
        if (list != null && list.size() >= 0) {
            for (Integer num : list) {
                C0667f c0667f = (C0667f) this.f2318f.get(num);
                if (c0667f != null) {
                    C0666c e = c0667f.m2228e();
                    if (e != null && C0669h.m2239e(e.f2314c)) {
                        return e;
                    }
                }
            }
        }
        return new C0666c();
    }

    public static synchronized C0668g m2230a(Context context) {
        C0668g c0668g;
        synchronized (C0668g.class) {
            if (f2317i == null) {
                f2317i = new C0668g(context);
            }
            c0668g = f2317i;
        }
        return c0668g;
    }

    public final void m2231b(String str) {
        C0666c f = m2232f();
        f.f2314c = str;
        if (!C0669h.m2238d(f.f2312a)) {
            f.f2312a = C0669h.m2236b(this.f2320h);
        }
        if (!C0669h.m2238d(f.f2313b)) {
            f.f2313b = C0669h.m2237c(this.f2320h);
        }
        f.f2315d = System.currentTimeMillis();
        for (Entry value : this.f2318f.entrySet()) {
            ((C0667f) value.getValue()).m2224a(f);
        }
    }

    public final C0666c m2232f() {
        return m2229a(new ArrayList(Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(4)})));
    }
}
