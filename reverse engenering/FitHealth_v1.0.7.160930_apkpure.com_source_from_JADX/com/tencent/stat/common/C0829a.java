package com.tencent.stat.common;

import android.content.Context;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class C0829a {
    static C0831c f2875a;
    private static StatLogger f2876d = C0837k.m2718b();
    private static JSONObject f2877e = null;
    Integer f2878b = null;
    String f2879c = null;

    public C0829a(Context context) {
        try {
            C0829a.m2684a(context);
            this.f2878b = C0837k.m2744q(context.getApplicationContext());
            this.f2879c = C0837k.m2743p(context);
        } catch (Object th) {
            f2876d.m2680e(th);
        }
    }

    static synchronized C0831c m2684a(Context context) {
        C0831c c0831c;
        synchronized (C0829a.class) {
            if (f2875a == null) {
                f2875a = new C0831c(context.getApplicationContext());
            }
            c0831c = f2875a;
        }
        return c0831c;
    }

    public static void m2685a(Context context, Map<String, String> map) {
        if (map != null) {
            Map hashMap = new HashMap(map);
            if (f2877e == null) {
                f2877e = new JSONObject();
            }
            for (Entry entry : hashMap.entrySet()) {
                f2877e.put((String) entry.getKey(), entry.getValue());
            }
        }
    }

    public void m2686a(JSONObject jSONObject) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (f2875a != null) {
                f2875a.m2687a(jSONObject2);
            }
            C0837k.m2714a(jSONObject2, "cn", this.f2879c);
            if (this.f2878b != null) {
                jSONObject2.put("tn", this.f2878b);
            }
            jSONObject.put("ev", jSONObject2);
            if (f2877e != null && f2877e.length() > 0) {
                jSONObject.put("eva", f2877e);
            }
        } catch (Object th) {
            f2876d.m2680e(th);
        }
    }
}
