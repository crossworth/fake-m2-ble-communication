package com.droi.sdk.analytics;

import android.content.Context;
import com.droi.sdk.core.AnalyticsCoreHelper;
import com.tencent.stat.DeviceInfo;
import com.umeng.socialize.net.utils.SocializeProtocolConstants;
import org.json.JSONObject;

class C0772h {
    private Context f2332a;

    C0772h(Context context) {
        this.f2332a = context;
    }

    private JSONObject m2375b(String str, long j) {
        String a = C0755c.m2322a(this.f2332a);
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("did", AnalyticsCoreHelper.getDeviceId());
        jSONObject.put("t", j);
        jSONObject.put(SocializeProtocolConstants.PROTOCOL_KEY_ST, C0755c.m2332b(str));
        jSONObject.put("ac", a);
        jSONObject.put("a03", C0754b.m2318b());
        jSONObject.put("a04", C0754b.m2319c());
        jSONObject.put("a05", C0754b.m2320d());
        return jSONObject;
    }

    protected void m2376a(String str, long j) {
        C0753a.m2312a("ErrorManager", "Call postErrorInfo()");
        if (str != null) {
            JSONObject jSONObject = new JSONObject();
            try {
                JSONObject b = m2375b(str, j);
                if (b != null) {
                    jSONObject.put("mt", "m07");
                    jSONObject.put(DeviceInfo.TAG_MAC, b);
                    C0770f.m2354a(new C0775k(1, 0, 3, "m07").toString(), jSONObject.toString());
                }
            } catch (Exception e) {
                C0753a.m2311a("ErrorManager", e);
            }
        }
    }
}
