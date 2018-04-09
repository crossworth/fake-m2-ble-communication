package com.tencent.wxop.stat.common;

import android.content.Context;
import com.tencent.wxop.stat.C1428a;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONObject;

public class C1433b {
    static C1435d f4743a;
    private static StatLogger f4744d = C1442k.m4416b();
    private static JSONObject f4745e = new JSONObject();
    Integer f4746b = null;
    String f4747c = null;

    public C1433b(Context context) {
        try {
            C1433b.m4384a(context);
            this.f4746b = C1442k.m4435m(context.getApplicationContext());
            this.f4747c = C1428a.m4298a(context).m4307b();
        } catch (Throwable th) {
            f4744d.m4375e(th);
        }
    }

    static synchronized C1435d m4384a(Context context) {
        C1435d c1435d;
        synchronized (C1433b.class) {
            if (f4743a == null) {
                f4743a = new C1435d(context.getApplicationContext());
            }
            c1435d = f4743a;
        }
        return c1435d;
    }

    public static void m4385a(Context context, Map<String, String> map) {
        if (map != null) {
            for (Entry entry : new HashMap(map).entrySet()) {
                f4745e.put((String) entry.getKey(), entry.getValue());
            }
        }
    }

    public void m4386a(JSONObject jSONObject, Thread thread) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (f4743a != null) {
                f4743a.m4387a(jSONObject2, thread);
            }
            C1448q.m4464a(jSONObject2, "cn", this.f4747c);
            if (this.f4746b != null) {
                jSONObject2.put("tn", this.f4746b);
            }
            if (thread == null) {
                jSONObject.put("ev", jSONObject2);
            } else {
                jSONObject.put("errkv", jSONObject2.toString());
            }
            if (f4745e != null && f4745e.length() > 0) {
                jSONObject.put("eva", f4745e);
            }
        } catch (Throwable th) {
            f4744d.m4375e(th);
        }
    }
}
