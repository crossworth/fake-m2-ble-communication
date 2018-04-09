package com.tencent.stat.common;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class C1379a {
    static C1381c f4395a;
    private static StatLogger f4396d = C1389k.m4125b();
    private static JSONObject f4397e = null;
    Integer f4398b = null;
    String f4399c = null;

    public C1379a(Context context) {
        try {
            C1379a.m4089a(context);
            this.f4398b = C1389k.m4151q(context.getApplicationContext());
            this.f4399c = C1389k.m4150p(context);
        } catch (Object th) {
            f4396d.m4085e(th);
        }
    }

    static synchronized C1381c m4089a(Context context) {
        C1381c c1381c;
        synchronized (C1379a.class) {
            if (f4395a == null) {
                f4395a = new C1381c(context.getApplicationContext());
            }
            c1381c = f4395a;
        }
        return c1381c;
    }

    public static void m4090a(Context context, Map<String, String> map) {
        if (map != null) {
            Map hashMap = new HashMap(map);
            if (f4397e == null) {
                f4397e = new JSONObject();
            }
            for (Entry entry : hashMap.entrySet()) {
                f4397e.put((String) entry.getKey(), entry.getValue());
            }
        }
    }

    public void m4091a(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (f4395a != null) {
                f4395a.m4092a(jSONObject2);
            }
            C1389k.m4121a(jSONObject2, "cn", this.f4399c);
            if (this.f4398b != null) {
                jSONObject2.put("tn", this.f4398b);
            }
            jSONObject.put("ev", jSONObject2);
            if (f4397e != null && f4397e.length() > 0) {
                jSONObject.put("eva", f4397e);
            }
        } catch (Object th) {
            f4396d.m4085e(th);
        }
    }
}
