package com.tencent.wxop.stat.p040a;

import android.content.Context;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.C1433b;
import com.tencent.wxop.stat.common.C1442k;
import org.json.JSONObject;

public class C1427l extends C1416e {
    private C1433b f4634a;
    private JSONObject f4635m = null;

    public C1427l(Context context, int i, JSONObject jSONObject, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.f4634a = new C1433b(context);
        this.f4635m = jSONObject;
    }

    public C1421f mo2223a() {
        return C1421f.SESSION_ENV;
    }

    public boolean mo2224a(JSONObject jSONObject) {
        if (this.f4595e != null) {
            jSONObject.put("ut", this.f4595e.m4383d());
        }
        if (this.f4635m != null) {
            jSONObject.put("cfg", this.f4635m);
        }
        if (C1442k.m4447y(this.l)) {
            jSONObject.put("ncts", 1);
        }
        this.f4634a.m4386a(jSONObject, null);
        return true;
    }
}
