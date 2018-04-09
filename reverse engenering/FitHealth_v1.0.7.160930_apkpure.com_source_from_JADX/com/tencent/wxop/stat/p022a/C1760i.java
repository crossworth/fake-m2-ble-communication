package com.tencent.wxop.stat.p022a;

import android.content.Context;
import com.tencent.wxop.stat.C0897f;
import com.tencent.wxop.stat.p023b.C0879d;
import com.tencent.wxop.stat.p023b.C0885l;
import org.json.JSONObject;

public final class C1760i extends C0873d {
    private C0879d bJ;
    private JSONObject bK = null;

    public C1760i(Context context, int i, JSONObject jSONObject, C0897f c0897f) {
        super(context, i, c0897f);
        this.bJ = new C0879d(context);
        this.bK = jSONObject;
    }

    public final C0874e ac() {
        return C0874e.SESSION_ENV;
    }

    public final boolean mo2147b(JSONObject jSONObject) {
        if (this.bp != null) {
            jSONObject.put("ut", this.bp.as());
        }
        if (this.bK != null) {
            jSONObject.put("cfg", this.bK);
        }
        if (C0885l.m2883P(this.bv)) {
            jSONObject.put("ncts", 1);
        }
        this.bJ.m2859a(jSONObject, null);
        return true;
    }
}
