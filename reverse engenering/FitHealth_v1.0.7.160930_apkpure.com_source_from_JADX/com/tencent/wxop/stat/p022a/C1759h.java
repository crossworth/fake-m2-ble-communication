package com.tencent.wxop.stat.p022a;

import android.content.Context;
import com.tencent.wxop.stat.C0897f;
import com.tencent.wxop.stat.p023b.C0891r;
import org.json.JSONObject;
import p031u.aly.au;

public final class C1759h extends C0873d {
    String aR;
    String aS;
    Long bI = null;

    public C1759h(Context context, String str, String str2, int i, Long l, C0897f c0897f) {
        super(context, i, c0897f);
        this.aS = str;
        this.aR = str2;
        this.bI = l;
    }

    public final C0874e ac() {
        return C0874e.PAGE_VIEW;
    }

    public final boolean mo2147b(JSONObject jSONObject) {
        C0891r.m2918a(jSONObject, "pi", this.aR);
        C0891r.m2918a(jSONObject, "rf", this.aS);
        if (this.bI != null) {
            jSONObject.put(au.aI, this.bI);
        }
        return true;
    }
}
