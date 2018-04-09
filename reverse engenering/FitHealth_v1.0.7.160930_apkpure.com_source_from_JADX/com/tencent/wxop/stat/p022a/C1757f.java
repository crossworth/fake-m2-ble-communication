package com.tencent.wxop.stat.p022a;

import android.content.Context;
import com.tencent.wxop.stat.C0894c;
import com.tencent.wxop.stat.C0897f;
import com.tencent.wxop.stat.p023b.C0891r;
import org.json.JSONObject;

public final class C1757f extends C0873d {
    public static final C0897f bw;

    static {
        C0897f c0897f = new C0897f();
        bw = c0897f;
        c0897f.m3007s("A9VH9B8L4GX4");
    }

    public C1757f(Context context) {
        super(context, 0, bw);
    }

    public final C0874e ac() {
        return C0874e.NETWORK_DETECTOR;
    }

    public final boolean mo2147b(JSONObject jSONObject) {
        C0891r.m2918a(jSONObject, "actky", C0894c.m2943d(this.bv));
        return true;
    }
}
