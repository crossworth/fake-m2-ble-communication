package com.tencent.stat.p039a;

import android.content.Context;
import com.tencent.stat.StatAppMonitor;
import com.tencent.stat.common.C1389k;
import org.json.JSONObject;

public class C1372h extends C1365e {
    private static String f4370l = null;
    private static String f4371m = null;
    private StatAppMonitor f4372a = null;

    public C1372h(Context context, int i, StatAppMonitor statAppMonitor) {
        super(context, i);
        this.f4372a = statAppMonitor.clone();
    }

    public C1370f mo2219a() {
        return C1370f.MONITOR_STAT;
    }

    public boolean mo2220a(JSONObject jSONObject) {
        if (this.f4372a == null) {
            return false;
        }
        jSONObject.put("na", this.f4372a.getInterfaceName());
        jSONObject.put("rq", this.f4372a.getReqSize());
        jSONObject.put("rp", this.f4372a.getRespSize());
        jSONObject.put("rt", this.f4372a.getResultType());
        jSONObject.put("tm", this.f4372a.getMillisecondsConsume());
        jSONObject.put("rc", this.f4372a.getReturnCode());
        jSONObject.put("sp", this.f4372a.getSampling());
        if (f4371m == null) {
            f4371m = C1389k.m4152r(this.k);
        }
        C1389k.m4121a(jSONObject, "av", f4371m);
        if (f4370l == null) {
            f4370l = C1389k.m4147m(this.k);
        }
        C1389k.m4121a(jSONObject, "op", f4370l);
        jSONObject.put("cn", C1389k.m4150p(this.k));
        return true;
    }
}
