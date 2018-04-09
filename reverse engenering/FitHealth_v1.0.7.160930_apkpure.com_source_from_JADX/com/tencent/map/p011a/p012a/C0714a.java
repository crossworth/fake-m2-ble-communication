package com.tencent.map.p011a.p012a;

import android.content.Context;
import com.tencent.map.p013b.C1722n;

public class C0714a {
    private static C1722n f2454a = C1722n.m4682a();
    private static C0714a f2455b;

    public static synchronized C0714a m2387a() {
        C0714a c0714a;
        synchronized (C0714a.class) {
            if (f2455b == null) {
                f2455b = new C0714a();
            }
            c0714a = f2455b;
        }
        return c0714a;
    }

    public boolean m2388a(Context context, C0715b c0715b) {
        return f2454a.m4728a(context, c0715b);
    }

    public boolean m2389a(String str, String str2) {
        return f2454a.m4729a(str, str2);
    }

    public void m2390b() {
        f2454a.m4730b();
    }
}
