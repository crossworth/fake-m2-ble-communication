package com.tencent.p021a.p022a.p023a.p024a;

import android.content.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class C1146g {
    private static C1146g f3507V = null;
    private Map<Integer, C1141f> f3508U = null;
    private int f3509b = 0;
    private Context f3510c = null;

    private C1146g(Context context) {
        this.f3510c = context.getApplicationContext();
        this.f3508U = new HashMap(3);
        this.f3508U.put(Integer.valueOf(1), new C1145e(context));
        this.f3508U.put(Integer.valueOf(2), new C1142b(context));
        this.f3508U.put(Integer.valueOf(4), new C1144d(context));
    }

    public static synchronized C1146g m3331E(Context context) {
        C1146g c1146g;
        synchronized (C1146g.class) {
            if (f3507V == null) {
                f3507V = new C1146g(context);
            }
            c1146g = f3507V;
        }
        return c1146g;
    }

    private C1143c m3332b(List<Integer> list) {
        if (list.size() >= 0) {
            for (Integer num : list) {
                C1141f c1141f = (C1141f) this.f3508U.get(num);
                if (c1141f != null) {
                    C1143c o = c1141f.m3318o();
                    if (o != null && C1147h.m3341c(o.f3506c)) {
                        return o;
                    }
                }
            }
        }
        return new C1143c();
    }

    public final void m3333a(String str) {
        C1143c p = m3334p();
        p.f3506c = str;
        if (!C1147h.m3340b(p.f3504a)) {
            p.f3504a = C1147h.m3335a(this.f3510c);
        }
        if (!C1147h.m3340b(p.f3505b)) {
            p.f3505b = C1147h.m3339b(this.f3510c);
        }
        p.f3503T = System.currentTimeMillis();
        for (Entry value : this.f3508U.entrySet()) {
            ((C1141f) value.getValue()).m3314a(p);
        }
    }

    public final C1143c m3334p() {
        return m3332b(new ArrayList(Arrays.asList(new Integer[]{Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(4)})));
    }
}
