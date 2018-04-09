package com.droi.sdk.analytics;

import android.content.Context;
import com.droi.sdk.core.AnalyticsCoreHelper;
import com.tencent.stat.DeviceInfo;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONObject;

class C0773i {
    private Context f2333a;
    private String f2334b;
    private int f2335c;
    private int f2336d;
    private Map<String, String> f2337e;

    C0773i(Context context, String str, int i, Map<String, String> map, int i2) {
        this.f2333a = context;
        this.f2334b = C0755c.m2324a(str, 128);
        this.f2335c = i2;
        this.f2336d = i;
        this.f2337e = map;
    }

    private JSONArray m2377a() {
        JSONArray jSONArray = new JSONArray();
        if (this.f2337e == null || this.f2337e.size() == 0) {
            return null;
        }
        for (Entry entry : this.f2337e.entrySet()) {
            JSONObject jSONObject = new JSONObject();
            try {
                jSONObject.put(C0755c.m2324a((String) entry.getKey(), 128), C0755c.m2324a((String) entry.getValue(), 256));
                if (jSONObject != null) {
                    jSONArray.put(jSONObject);
                } else {
                    continue;
                }
            } catch (Exception e) {
                try {
                    C0753a.m2311a("EventManager", e);
                } catch (Exception e2) {
                    C0753a.m2311a("EventManager", e2);
                }
            }
        }
        return jSONArray;
    }

    private JSONObject m2378b(long j) {
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("did", AnalyticsCoreHelper.getDeviceId());
        jSONObject.put("ty", this.f2336d);
        jSONObject.put("t", j);
        jSONObject.put("eid", this.f2334b);
        jSONObject.put("num", this.f2335c);
        jSONObject.put("ac", C0755c.m2322a(this.f2333a));
        if (m2377a() != null) {
            jSONObject.put("kv", m2377a());
        } else {
            jSONObject.put("kv", "");
        }
        jSONObject.put("a03", C0754b.m2318b());
        jSONObject.put("a04", C0754b.m2319c());
        jSONObject.put("a05", C0754b.m2320d());
        return jSONObject;
    }

    protected void m2379a(long j) {
        C0753a.m2312a("EventManager", "postEventInfo()");
        JSONObject jSONObject = new JSONObject();
        try {
            JSONObject b = m2378b(j);
            if (b != null) {
                jSONObject.put("mt", "m05");
                jSONObject.put(DeviceInfo.TAG_MAC, b);
                C0770f.m2354a(new C0775k(1, 0, 3, "m05").toString(), jSONObject.toString());
            }
        } catch (Exception e) {
            C0753a.m2311a("EventManager", e);
        }
    }
}
