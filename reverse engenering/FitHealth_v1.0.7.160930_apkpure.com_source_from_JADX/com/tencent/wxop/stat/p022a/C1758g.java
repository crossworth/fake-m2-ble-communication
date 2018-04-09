package com.tencent.wxop.stat.p022a;

import android.content.Context;
import com.tencent.wxop.stat.C0897f;
import com.tencent.wxop.stat.C0898g;
import com.tencent.wxop.stat.p023b.C0885l;
import com.tencent.wxop.stat.p023b.C0891r;
import org.json.JSONObject;

public final class C1758g extends C0873d {
    private static String f4740a = null;
    private String aR = null;
    private String aS = null;

    public C1758g(Context context, int i, C0897f c0897f) {
        super(context, i, c0897f);
        this.aR = C0898g.m3012r(context).m3019b();
        if (f4740a == null) {
            f4740a = C0885l.m2869C(context);
        }
    }

    public final C0874e ac() {
        return C0874e.NETWORK_MONITOR;
    }

    public final void m4893b(String str) {
        this.aS = str;
    }

    public final boolean mo2147b(JSONObject jSONObject) {
        C0891r.m2918a(jSONObject, "op", f4740a);
        C0891r.m2918a(jSONObject, "cn", this.aR);
        jSONObject.put("sp", this.aS);
        return true;
    }
}
