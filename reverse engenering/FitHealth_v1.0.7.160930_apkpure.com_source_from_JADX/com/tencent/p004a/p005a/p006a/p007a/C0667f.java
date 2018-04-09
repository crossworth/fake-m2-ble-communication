package com.tencent.p004a.p005a.p006a.p007a;

import android.content.Context;

public abstract class C0667f {
    protected Context f2316e = null;

    protected C0667f(Context context) {
        this.f2316e = context;
    }

    public final void m2224a(C0666c c0666c) {
        if (c0666c != null) {
            String c0666c2 = c0666c.toString();
            if (mo2077a()) {
                mo2079b(C0669h.m2241g(c0666c2));
            }
        }
    }

    protected abstract boolean mo2077a();

    protected abstract String mo2078b();

    protected abstract void mo2079b(String str);

    public final C0666c m2228e() {
        String f = mo2077a() ? C0669h.m2240f(mo2078b()) : null;
        return f != null ? C0666c.m2221c(f) : null;
    }
}
