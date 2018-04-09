package com.tencent.stat.p021a;

import android.content.Context;
import com.tencent.stat.common.C0837k;
import org.json.JSONObject;
import p031u.aly.au;

public class C1742j extends C0824e {
    Long f4700a = null;
    String f4701l;
    String f4702m;

    public C1742j(Context context, String str, String str2, int i, Long l) {
        super(context, i);
        this.f4702m = str;
        this.f4701l = str2;
        this.f4700a = l;
    }

    public C0825f mo2142a() {
        return C0825f.PAGE_VIEW;
    }

    public boolean mo2143a(JSONObject jSONObject) {
        C0837k.m2714a(jSONObject, "pi", this.f4701l);
        C0837k.m2714a(jSONObject, "rf", this.f4702m);
        if (this.f4700a != null) {
            jSONObject.put(au.aI, this.f4700a);
        }
        return true;
    }
}
