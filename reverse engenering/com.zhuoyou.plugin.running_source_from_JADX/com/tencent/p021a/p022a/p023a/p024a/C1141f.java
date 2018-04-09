package com.tencent.p021a.p022a.p023a.p024a;

import android.content.Context;

public abstract class C1141f {
    protected Context f3502a = null;

    protected C1141f(Context context) {
        this.f3502a = context;
    }

    public final void m3314a(C1143c c1143c) {
        if (c1143c != null) {
            String c1143c2 = c1143c.toString();
            if (mo2139a()) {
                mo2138a(C1147h.m3343g(c1143c2));
            }
        }
    }

    protected abstract void mo2138a(String str);

    protected abstract boolean mo2139a();

    protected abstract String mo2140b();

    public final C1143c m3318o() {
        String f = mo2139a() ? C1147h.m3342f(mo2140b()) : null;
        return f != null ? C1143c.m3322e(f) : null;
    }
}
