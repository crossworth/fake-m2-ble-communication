package com.tencent.wxop.stat.p040a;

import android.content.Context;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.C1448q;
import org.json.JSONObject;

public class C1426k extends C1416e {
    Long f4631a = null;
    String f4632m;
    String f4633n;

    public C1426k(Context context, String str, String str2, int i, Long l, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.f4633n = str;
        this.f4632m = str2;
        this.f4631a = l;
    }

    public C1421f mo2223a() {
        return C1421f.PAGE_VIEW;
    }

    public boolean mo2224a(JSONObject jSONObject) {
        C1448q.m4464a(jSONObject, "pi", this.f4632m);
        C1448q.m4464a(jSONObject, "rf", this.f4633n);
        if (this.f4631a != null) {
            jSONObject.put("du", this.f4631a);
        }
        return true;
    }
}
