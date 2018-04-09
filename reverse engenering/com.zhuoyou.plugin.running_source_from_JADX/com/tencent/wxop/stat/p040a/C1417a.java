package com.tencent.wxop.stat.p040a;

import android.content.Context;
import com.tencent.wxop.stat.StatAccount;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.C1448q;
import org.json.JSONObject;

public class C1417a extends C1416e {
    private StatAccount f4602a = null;

    public C1417a(Context context, int i, StatAccount statAccount, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.f4602a = statAccount;
    }

    public C1421f mo2223a() {
        return C1421f.ADDITION;
    }

    public boolean mo2224a(JSONObject jSONObject) {
        C1448q.m4464a(jSONObject, "qq", this.f4602a.getAccount());
        jSONObject.put("acc", this.f4602a.toJsonString());
        return true;
    }
}
