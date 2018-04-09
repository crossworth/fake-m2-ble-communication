package com.tencent.wxop.stat.p023b;

import android.content.Context;
import com.tencent.wxop.stat.C0898g;
import org.json.JSONObject;

public final class C0879d {
    static C0880e cw;
    private static C0877b cx = C0885l.av();
    private static JSONObject cz = new JSONObject();
    String f3026c = null;
    Integer cy = null;

    public C0879d(Context context) {
        try {
            C0879d.m2858u(context);
            this.cy = C0885l.m2872F(context.getApplicationContext());
            this.f3026c = C0898g.m3012r(context).m3019b();
        } catch (Throwable th) {
            cx.m2852b(th);
        }
    }

    private static synchronized C0880e m2858u(Context context) {
        C0880e c0880e;
        synchronized (C0879d.class) {
            if (cw == null) {
                cw = new C0880e(context.getApplicationContext());
            }
            c0880e = cw;
        }
        return c0880e;
    }

    public final void m2859a(JSONObject jSONObject, Thread thread) {
        JSONObject jSONObject2 = new JSONObject();
        try {
            if (cw != null) {
                cw.m2860a(jSONObject2, thread);
            }
            C0891r.m2918a(jSONObject2, "cn", this.f3026c);
            if (this.cy != null) {
                jSONObject2.put("tn", this.cy);
            }
            if (thread == null) {
                jSONObject.put("ev", jSONObject2);
            } else {
                jSONObject.put("errkv", jSONObject2.toString());
            }
            if (cz != null && cz.length() > 0) {
                jSONObject.put("eva", cz);
            }
        } catch (Throwable th) {
            cx.m2852b(th);
        }
    }
}
