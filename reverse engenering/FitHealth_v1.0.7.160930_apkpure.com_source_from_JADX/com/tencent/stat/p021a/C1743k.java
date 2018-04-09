package com.tencent.stat.p021a;

import android.content.Context;
import com.tencent.stat.common.C0829a;
import org.json.JSONObject;

public class C1743k extends C0824e {
    private C0829a f4703a;
    private JSONObject f4704l = null;

    public C1743k(Context context, int i, JSONObject jSONObject) {
        super(context, i);
        this.f4703a = new C0829a(context);
        this.f4704l = jSONObject;
    }

    public C0825f mo2142a() {
        return C0825f.SESSION_ENV;
    }

    public boolean mo2143a(JSONObject jSONObject) {
        if (this.f2844e != null) {
            jSONObject.put("ut", this.f2844e.getUserType());
        }
        if (this.f4704l != null) {
            jSONObject.put("cfg", this.f4704l);
        }
        this.f4703a.m2686a(jSONObject);
        return true;
    }
}
