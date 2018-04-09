package com.tencent.wxop.stat.p040a;

import android.content.Context;
import com.tencent.wxop.stat.C1428a;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.C1442k;
import com.tencent.wxop.stat.common.C1448q;
import org.json.JSONObject;

public class C1425j extends C1416e {
    private static String f4628a = null;
    private String f4629m = null;
    private String f4630n = null;

    public C1425j(Context context, int i, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.f4629m = C1428a.m4298a(context).m4307b();
        if (f4628a == null) {
            f4628a = C1442k.m4431i(context);
        }
    }

    public C1421f mo2223a() {
        return C1421f.NETWORK_MONITOR;
    }

    public void m4292a(String str) {
        this.f4630n = str;
    }

    public boolean mo2224a(JSONObject jSONObject) {
        C1448q.m4464a(jSONObject, "op", f4628a);
        C1448q.m4464a(jSONObject, "cn", this.f4629m);
        jSONObject.put("sp", this.f4630n);
        return true;
    }
}
