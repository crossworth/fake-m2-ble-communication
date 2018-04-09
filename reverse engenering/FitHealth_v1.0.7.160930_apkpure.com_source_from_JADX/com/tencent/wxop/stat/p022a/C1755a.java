package com.tencent.wxop.stat.p022a;

import android.content.Context;
import com.tencent.wxop.stat.C0896e;
import com.tencent.wxop.stat.C0897f;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;
import p031u.aly.au;

public final class C1755a extends C0873d {
    protected C0872b bj = new C0872b();
    private long bk = -1;

    public C1755a(Context context, int i, String str, C0897f c0897f) {
        super(context, i, c0897f);
        this.bj.f3005a = str;
    }

    public final C0872b ab() {
        return this.bj;
    }

    public final C0874e ac() {
        return C0874e.CUSTOM;
    }

    public final boolean mo2147b(JSONObject jSONObject) {
        jSONObject.put("ei", this.bj.f3005a);
        if (this.bk > 0) {
            jSONObject.put(au.aI, this.bk);
        }
        if (this.bj.bl == null) {
            if (this.bj.f3005a != null) {
                Map p = C0896e.m2998p(this.bj.f3005a);
                if (p != null && p.size() > 0) {
                    if (this.bj.bm == null || this.bj.bm.length() == 0) {
                        this.bj.bm = new JSONObject(p);
                    } else {
                        for (Entry entry : p.entrySet()) {
                            try {
                                this.bj.bm.put(entry.getKey().toString(), entry.getValue());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
            jSONObject.put("kv", this.bj.bm);
        } else {
            jSONObject.put("ar", this.bj.bl);
        }
        return true;
    }
}
