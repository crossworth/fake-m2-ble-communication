package com.tencent.stat.p039a;

import android.content.Context;
import com.tencent.stat.common.C1389k;
import org.json.JSONObject;

public class C1374j extends C1365e {
    Long f4376a = null;
    String f4377l;
    String f4378m;

    public C1374j(Context context, String str, String str2, int i, Long l) {
        super(context, i);
        this.f4378m = str;
        this.f4377l = str2;
        this.f4376a = l;
    }

    public C1370f mo2219a() {
        return C1370f.PAGE_VIEW;
    }

    public boolean mo2220a(JSONObject jSONObject) {
        C1389k.m4121a(jSONObject, "pi", this.f4377l);
        C1389k.m4121a(jSONObject, "rf", this.f4378m);
        if (this.f4376a != null) {
            jSONObject.put("du", this.f4376a);
        }
        return true;
    }
}
