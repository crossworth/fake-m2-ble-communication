package com.tencent.stat.p021a;

import android.content.Context;
import com.tencent.stat.StatAppMonitor;
import com.tencent.stat.common.C0837k;
import org.json.JSONObject;

public class C1740h extends C0824e {
    private static String f4694l = null;
    private static String f4695m = null;
    private StatAppMonitor f4696a = null;

    public C1740h(Context context, int i, StatAppMonitor statAppMonitor) {
        super(context, i);
        this.f4696a = statAppMonitor.clone();
    }

    public C0825f mo2142a() {
        return C0825f.MONITOR_STAT;
    }

    public boolean mo2143a(JSONObject jSONObject) {
        if (this.f4696a == null) {
            return false;
        }
        jSONObject.put("na", this.f4696a.getInterfaceName());
        jSONObject.put("rq", this.f4696a.getReqSize());
        jSONObject.put("rp", this.f4696a.getRespSize());
        jSONObject.put("rt", this.f4696a.getResultType());
        jSONObject.put("tm", this.f4696a.getMillisecondsConsume());
        jSONObject.put("rc", this.f4696a.getReturnCode());
        jSONObject.put("sp", this.f4696a.getSampling());
        if (f4695m == null) {
            f4695m = C0837k.m2745r(this.k);
        }
        C0837k.m2714a(jSONObject, "av", f4695m);
        if (f4694l == null) {
            f4694l = C0837k.m2740m(this.k);
        }
        C0837k.m2714a(jSONObject, "op", f4694l);
        jSONObject.put("cn", C0837k.m2743p(this.k));
        return true;
    }
}
