package com.tencent.stat.p039a;

import android.content.Context;
import com.tencent.stat.common.C1379a;
import org.json.JSONObject;

public class C1375k extends C1365e {
    private C1379a f4379a;
    private JSONObject f4380l = null;

    public C1375k(Context context, int i, JSONObject jSONObject) {
        super(context, i);
        this.f4379a = new C1379a(context);
        this.f4380l = jSONObject;
    }

    public C1370f mo2219a() {
        return C1370f.SESSION_ENV;
    }

    public boolean mo2220a(JSONObject jSONObject) {
        if (this.f4343e != null) {
            jSONObject.put("ut", this.f4343e.getUserType());
        }
        if (this.f4380l != null) {
            jSONObject.put("cfg", this.f4380l);
        }
        this.f4379a.m4091a(jSONObject);
        return true;
    }
}
