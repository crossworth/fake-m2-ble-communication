package com.tencent.wxop.stat.p040a;

import android.content.Context;
import com.tencent.wxop.stat.C1428a;
import com.tencent.wxop.stat.StatAppMonitor;
import com.tencent.wxop.stat.StatSpecifyReportedInfo;
import com.tencent.wxop.stat.common.C1442k;
import com.tencent.wxop.stat.common.C1448q;
import org.json.JSONObject;

public class C1423h extends C1416e {
    private static String f4624m = null;
    private static String f4625n = null;
    private StatAppMonitor f4626a = null;

    public C1423h(Context context, int i, StatAppMonitor statAppMonitor, StatSpecifyReportedInfo statSpecifyReportedInfo) {
        super(context, i, statSpecifyReportedInfo);
        this.f4626a = statAppMonitor.clone();
    }

    public C1421f mo2223a() {
        return C1421f.MONITOR_STAT;
    }

    public boolean mo2224a(JSONObject jSONObject) {
        if (this.f4626a == null) {
            return false;
        }
        jSONObject.put("na", this.f4626a.getInterfaceName());
        jSONObject.put("rq", this.f4626a.getReqSize());
        jSONObject.put("rp", this.f4626a.getRespSize());
        jSONObject.put("rt", this.f4626a.getResultType());
        jSONObject.put("tm", this.f4626a.getMillisecondsConsume());
        jSONObject.put("rc", this.f4626a.getReturnCode());
        jSONObject.put("sp", this.f4626a.getSampling());
        if (f4625n == null) {
            f4625n = C1442k.m4436n(this.l);
        }
        C1448q.m4464a(jSONObject, "av", f4625n);
        if (f4624m == null) {
            f4624m = C1442k.m4431i(this.l);
        }
        C1448q.m4464a(jSONObject, "op", f4624m);
        jSONObject.put("cn", C1428a.m4298a(this.l).m4307b());
        return true;
    }
}
